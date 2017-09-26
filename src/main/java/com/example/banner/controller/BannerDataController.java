package com.example.banner.controller;

import com.example.banner.request.StoreRequest;
import com.example.banner.service.BannerDataService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BannerDataController {

	private BannerDataService bannerDataService;

	private Map<String, String> ips;

	@Autowired
	public BannerDataController(BannerDataService bannerDataService, Map<String, String> ips) {
		this.bannerDataService = bannerDataService;
		this.ips = ips;
	}

	@PostMapping("/v1/banners")
	@ResponseStatus(HttpStatus.OK)
	public void insert(@RequestBody StoreRequest storeRequest) {
		bannerDataService.insert(storeRequest);
	}

	@GetMapping("/v1/banners")
	@ResponseStatus(HttpStatus.OK)
	public List<Map> display(@RequestParam(required = false) String ipAddress) {
		return bannerDataService.display(ips, ipAddress);
	}
}
