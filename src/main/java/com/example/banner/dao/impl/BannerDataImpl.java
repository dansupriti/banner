package com.example.banner.dao.impl;

import com.example.banner.configuration.ElasticsearchConfig;
import com.example.banner.dao.BannerDataDao;
import com.example.banner.response.BannerResponse;
import com.sun.deploy.util.SessionState;
import entity.Banner;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.example.banner.common.ApiConstants.BANNER_INDEX;

@Repository
public class BannerDataImpl implements BannerDataDao{

    private ElasticsearchTemplate elasticsearchTemplate;

    private BannerQueryBuilder bannerQueryBuilder;

    private Client client;

    @Autowired
    public BannerDataImpl(ElasticsearchTemplate elasticsearchTemplate, BannerQueryBuilder bannerQueryBuilder,
                          Client client){
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.bannerQueryBuilder = bannerQueryBuilder;
        this.client = client;
    }

    public void insert(Banner banner){
        IndexQuery insertQuery = bannerQueryBuilder.buildInsertQuery(banner);
        elasticsearchTemplate.index(insertQuery);
    }

    public BannerResponse display(){
        List<Map> resultList;
        try {
            SearchRequestBuilder searchRequestBuilder = SearchAction.INSTANCE.newRequestBuilder(client);
            searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
            searchRequestBuilder.setQuery(bannerQueryBuilder.buildDisplayQuery());
            searchRequestBuilder.setIndices(BANNER_INDEX);
            SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        } catch (IndexNotFoundException indexNotFoundException) {
          //  throw new InternalServerError(String.format("Index: '%s' not found in Elasticsearch!", navigationSearchRequest.getResources()));
        } catch (Exception exception) {
         //   throw new InternalServerError("Exception occurred while fetching data from database!");
        }
        return null;
    }
}
