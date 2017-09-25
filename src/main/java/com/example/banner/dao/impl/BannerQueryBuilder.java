package com.example.banner.dao.impl;

import entity.Banner;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import static com.example.banner.common.ApiConstants.BANNER_INDEX;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Component
public class BannerQueryBuilder {

    public IndexQuery buildInsertQuery(Banner banner){
        return new IndexQueryBuilder()
                .withObject(banner)
                .withId(banner.getBannerId())
                .withIndexName(BANNER_INDEX)
                .withType(BANNER_INDEX).build();
    }

    public BoolQueryBuilder buildDisplayQuery(){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(matchAllQuery());
        return boolQueryBuilder;
    }
}
