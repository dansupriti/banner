package com.example.banner.dao;

import com.example.banner.entity.Banner;
import java.util.List;
import java.util.Map;

public interface BannerDataDao {
    void insert(Banner banner);
	List<Map> display();
	List<Map> displayAll();
}
