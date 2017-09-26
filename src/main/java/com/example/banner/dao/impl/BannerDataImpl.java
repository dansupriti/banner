package com.example.banner.dao.impl;

import static com.example.banner.common.ApiConstants.BANNER_INDEX;

import com.example.banner.dao.BannerDataDao;
import com.example.banner.entity.Banner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
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

    public List<Map> display(){
		List<Map> resultList = new ArrayList<>();
		Map<String, Object> stringObjectMap;
        try {
            SearchRequestBuilder searchRequestBuilder = SearchAction.INSTANCE.newRequestBuilder(client);
            searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
            searchRequestBuilder.setQuery(bannerQueryBuilder.buildDisplayQuery());
            searchRequestBuilder.setIndices(BANNER_INDEX);
            log.info("query is ::" + searchRequestBuilder);
            SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
			for (SearchHit searchHits : searchResponse.getHits()) {
				stringObjectMap = searchHits.sourceAsMap();
				resultList.add(stringObjectMap);
			}
        } catch (IndexNotFoundException indexNotFoundException) {
         	log.error("Elasticsearch Index Not Found", indexNotFoundException);
        } catch (Exception exception) {
			log.error("Exception occoured due to :: ", exception);
        }
		return resultList;
    }

	public List<Map> displayAll(){
		List<Map> resultList = new ArrayList<>();
		Map<String, Object> stringObjectMap;
		try {
			SearchRequestBuilder searchRequestBuilder = SearchAction.INSTANCE.newRequestBuilder(client);
			searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
			searchRequestBuilder.setQuery(bannerQueryBuilder.buildDisplayAllQuery());
			searchRequestBuilder.setIndices(BANNER_INDEX);
			log.info("query is ::" + searchRequestBuilder);
			SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
			for (SearchHit searchHits : searchResponse.getHits()) {
				stringObjectMap = searchHits.sourceAsMap();
				resultList.add(stringObjectMap);
			}
		} catch (IndexNotFoundException indexNotFoundException) {
			log.error("Elasticsearch Index Not Found", indexNotFoundException);
		} catch (Exception exception) {
			log.error("Exception occoured due to :: ", exception);
		}
		return resultList;
	}
}
