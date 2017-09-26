package com.example.banner.service;

import com.example.banner.dao.BannerDataDao;
import com.example.banner.entity.Banner;
import com.example.banner.factory.BannerFactory;
import com.example.banner.request.StoreRequest;
import java.util.List;
import java.util.Map;
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
                storeRequest.getBannerDescription(), storeRequest.getStartDate(), storeRequest.getExpiryDate());
        bannerDataDao.insert(banner);
    }

    public List<Map> display(Map<String, String> ips, String requestedIp){
    	if(requestedIp != null && ips.containsValue(requestedIp)){
			return bannerDataDao.displayAll();
		}else {
			return bannerDataDao.display();
		}
    }
}
