package com.example.banner.configuration;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class IpConfiguration {

	@Value("#{${allowed.ip}}")
	private Map<String,String> ips;

	@Bean
	public  Map<String,String> getIps() {
		return ips;
	}

}

