package com.example.banner.dao.impl;

import static com.example.banner.common.ApiConstants.BANNER_INDEX;
import static com.example.banner.common.ApiConstants.DB_DATE_TIME_FORMAT;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import com.example.banner.entity.Banner;
import com.example.banner.pojo.DateRange;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

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

		RangeQueryBuilder startDateRangeQuery = QueryBuilders.rangeQuery("startDate");
		Optional<Date> startDate = Optional.empty();
		Optional<Date> endDate = Optional.of(getCurrentUTCDate());
		DateRange startDateRange = new DateRange(startDate, endDate);
		if (startDateRange.getLowerBound().isPresent()) {
			startDateRangeQuery.gte(startDateRange.getLowerBound().get());
		}
		if (startDateRange.getUpperBound().isPresent()) {
			startDateRangeQuery.lte(startDateRange.getUpperBound().get());
		}
		boolQueryBuilder.filter(startDateRangeQuery);

		RangeQueryBuilder endDateRangeQuery = QueryBuilders.rangeQuery("expiryDate");
		Optional<Date> startDate2 = Optional.of(getCurrentUTCDate());
		Optional<Date> endDate2 = Optional.empty();
		DateRange endDateRange = new DateRange(startDate2, endDate2);
		if (endDateRange.getLowerBound().isPresent()) {
			endDateRangeQuery.gte(endDateRange.getLowerBound().get());
		}
		if (endDateRange.getUpperBound().isPresent()) {
			endDateRangeQuery.lte(endDateRange.getUpperBound().get());
		}
		boolQueryBuilder.filter(endDateRangeQuery);
		return boolQueryBuilder;
    }

	private  Date getCurrentUTCDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(DB_DATE_TIME_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		return DateTime.parse(formatter.format(DateTime.now(DateTimeZone.UTC).toDate())).toDate();
	}

	public BoolQueryBuilder buildDisplayAllQuery(){
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(matchAllQuery());
		return boolQueryBuilder;
	}
}
