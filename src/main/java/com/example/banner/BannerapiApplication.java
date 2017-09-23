package com.example.banner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = ElasticsearchAutoConfiguration.class)
public class BannerapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BannerapiApplication.class, args);
	}
}
