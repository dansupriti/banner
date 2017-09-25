package com.example.banner.factory;

import entity.Banner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BannerFactory {

    public Banner getBannerObject(String id, String bannerTitle, String bannerDescription, Date bannerExpiryDate){
        return new Banner(id, bannerTitle, bannerDescription, bannerExpiryDate);
    }
}
