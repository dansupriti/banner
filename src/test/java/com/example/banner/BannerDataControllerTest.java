package com.example.banner;


import static com.example.banner.common.ApiConstants.BANNER_INDEX;
import static com.example.banner.common.ApiConstants.DB_DATE_TIME_FORMAT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import com.example.banner.entity.Banner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BannerapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BannerDataControllerTest {
	@Value("${local.server.port}")
	private int port;

	@Autowired
	private TestRestTemplate template;

	private String baseUrl;
	private String indexName = BANNER_INDEX;
	private ObjectMapper objectMapper;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Before
	public void setUp() {
		baseUrl = String.format("http://localhost:%s/bannerapi/v1/banners", port);
		objectMapper = new ObjectMapper();
		cleanUpAllIndexes();
	}

	private void cleanUpAllIndexes() {
		checkAndDeleteIndex(indexName);
	}

	@After
	public void tearDown() {
		cleanUpAllIndexes();
	}

	@Test
	public void testShouldReturn200OKAndDisplayAllBannerEvenDisplayPeriodPassedWhenSpecialIPGiven() {
		//given
		DateTime now = DateTime.now();
		Date hundredDaysAfter = now.plusDays(100).toDate();
		Date tenDaysBefore = now.minusDays(10).toDate();
		Date hundredDaysBefore = now.minusDays(100).toDate();
		String id1 = "bannerId1";
		Banner validBanner1 = Banner.builder().bannerId(id1).bannerDescription("bannerDescription1").bannerTitle("bannerTitle1")
				.expiryDate(hundredDaysAfter).startDate(hundredDaysBefore).build();
		createTestData(id1, validBanner1, indexName, indexName);
		String id2 = "bannerId2";
		Banner expiredBanner1 = Banner.builder().bannerId(id2).bannerDescription("bannerDescription2").
				bannerTitle("bannerTitle2")
				.expiryDate(tenDaysBefore).startDate(hundredDaysBefore).build();
		createTestData(id2, expiredBanner1, indexName, indexName);
		String resourcePath = String.format(baseUrl + "?ipAddress=%s","10.0.0.1");
		URI uri = URI.create(resourcePath);

		//when
		ResponseEntity<Object> actualResponse = template.getForEntity(uri, Object.class);

		//then
		assertThat(actualResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertThat(((List)actualResponse.getBody()).size(), is(2));
	}

	@Test
	public void testShouldReturn200OKAndDisplayOnlyValidBanner() {
		//given
		DateTime now = DateTime.now();
		Date hundredDaysAfter = now.plusDays(100).toDate();
		Date tenDaysBefore = now.minusDays(10).toDate();
		Date hundredDaysBefore = now.minusDays(100).toDate();
		String id1 = "bannerId1";
		Banner validBanner1 = Banner.builder().bannerId(id1).bannerDescription("bannerDescription1").bannerTitle("bannerTitle1")
				.expiryDate(hundredDaysAfter).startDate(hundredDaysBefore).build();
		createTestData(id1, validBanner1, indexName, indexName);
		String id2 = "bannerId2";
		Banner expiredBanner1 = Banner.builder().bannerId(id2).bannerDescription("bannerDescription2").
				bannerTitle("bannerTitle2")
				.expiryDate(tenDaysBefore).startDate(hundredDaysBefore).build();
		createTestData(id2, expiredBanner1, indexName, indexName);
		String resourcePath = baseUrl;
		URI uri = URI.create(resourcePath);

		//when
		ResponseEntity<Object> actualResponse = template.getForEntity(uri, Object.class);

		//then
		assertThat(actualResponse.getStatusCode(), is(equalTo(HttpStatus.OK)));
		assertThat(((List)actualResponse.getBody()).size(), is(1));
	}

	private void createTestData(String id, Banner banner, String indexName, String indexType) {
		DateFormat dateFormat = new SimpleDateFormat(DB_DATE_TIME_FORMAT);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			this.objectMapper.setDateFormat(dateFormat);
			if (!elasticsearchTemplate.indexExists(indexName)) {
				elasticsearchTemplate.createIndex(Banner.class);
				elasticsearchTemplate.putMapping(Banner.class);
			}
			elasticsearchTemplate.index(new IndexQueryBuilder().withObject(banner)
					.withIndexName(indexName)
					.withType(indexName)
					.withId(id)
					.build());
		}catch (Exception exception){
			log.error("Exception occur while creating test data and index in Elasticsearch.", exception);
		}
		refreshIndex(indexType);
	}

	private void checkAndDeleteIndex(String indexName){
		try{
			if (elasticsearchTemplate.indexExists(indexName)) {
				elasticsearchTemplate.deleteIndex(indexName);
			}
		}catch (Exception exception){
			log.error("Exception occur while deleting index from Elasticsearch", exception);
		}
	}

	private void refreshIndex(String indexName) {
		if (elasticsearchTemplate.indexExists(indexName)) {
			elasticsearchTemplate.refresh(indexName);
		}
	}

}
