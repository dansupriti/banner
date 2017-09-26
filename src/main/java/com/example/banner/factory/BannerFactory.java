package com.example.banner.factory;

import com.example.banner.entity.Banner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BannerFactory {

    public Banner getBannerObject(String id, String bannerTitle, String bannerDescription, Date startDate, Date expiryDate){
        return new Banner(id, bannerTitle, bannerDescription, startDate, expiryDate);
    }
}
