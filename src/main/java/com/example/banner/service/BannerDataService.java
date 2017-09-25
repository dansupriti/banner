package com.example.banner.service;

import com.example.banner.dao.BannerDataDao;
import com.example.banner.factory.BannerFactory;
import com.example.banner.request.StoreRequest;
import com.example.banner.response.BannerResponse;
import entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by supritidan on 2017/09/23.
 */
@Service
public class BannerDataService {

    private BannerFactory bannerFactory;

    private BannerDataDao bannerDataDao;


    @Autowired
    public BannerDataService(BannerFactory bannerFactory, BannerDataDao bannerDataDao) {
        this.bannerFactory = bannerFactory;
        this.bannerDataDao = bannerDataDao;
    }

    public void insert(StoreRequest storeRequest) {
        Banner banner = bannerFactory.getBannerObject(storeRequest.getBannerId(), storeRequest.getBannerTitle(),
                storeRequest.getBannerDescription(), storeRequest.getBannerExpiryDate());
        bannerDataDao.insert(banner);
    }

    public BannerResponse display(){
        return bannerDataDao.display();
    }
}
