package com.example.banner.controller;

import com.example.banner.request.StoreRequest;
import com.example.banner.response.BannerResponse;
import com.example.banner.service.BannerDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by supritidan on 2017/09/23.
 */
@RestController
public class BannerDataController {

    private BannerDataService bannerDataService;

    @Autowired
    public BannerDataController(BannerDataService bannerDataService){
        this.bannerDataService = bannerDataService;
    }

    @PostMapping("/v1/banners")
    @ResponseStatus(HttpStatus.OK)
    public void insert(@RequestBody StoreRequest storeRequest){
        bannerDataService.insert(storeRequest);
    }

    @GetMapping("/v1/banners")
    @ResponseStatus(HttpStatus.OK)
    public BannerResponse display(@RequestParam String ipAddress){
        return bannerDataService.display();
    }
}
