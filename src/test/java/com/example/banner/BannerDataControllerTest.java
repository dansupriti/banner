package com.example.banner;


import com.example.banner.controller.BannerDataController;
import com.example.banner.request.StoreRequest;
import com.example.banner.service.BannerDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;


/**
 * Created by supritidan on 2017/09/23.
 */
@RunWith(MockitoJUnitRunner.class)
public class BannerDataControllerTest {

    private BannerDataController bannerDataController;
    @Mock
    private BannerDataService bannerDataService;

    @Before
    public void setUp(){
        bannerDataController = new BannerDataController(bannerDataService);
    }

    @Test
    public void testShouldInsertBannerAndReturn200OK(){
        //given
        StoreRequest storeRequest = new StoreRequest();


        //when
        bannerDataController.insert(storeRequest);

        //then
    }
}
