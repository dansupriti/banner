package com.example.banner.controller;

import com.example.banner.request.StoreRequest;
import com.example.banner.response.BannerResponse;
import com.example.banner.service.BannerDataService;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by supritidan on 2017/09/23.
 */
@RestController
public class BannerDataController {

    private BannerDataService bannerDataService;

    public BannerDataController(BannerDataService bannerDataService){
        this.bannerDataService = bannerDataService;
    }

    @PostMapping("/v1/banners")
    public BannerResponse insert(@RequestBody StoreRequest storeRequest){

        return bannerDataService.insert(storeRequest);
    }
}
