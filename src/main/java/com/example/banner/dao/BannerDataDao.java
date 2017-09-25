package com.example.banner.dao;

import com.example.banner.response.BannerResponse;
import entity.Banner;

public interface BannerDataDao {
    void insert(Banner banner);
    BannerResponse display();
}
