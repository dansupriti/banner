package com.example.banner.request;

import lombok.*;

import java.util.Date;

/**
 * Created by supritidan on 2017/09/23.
 */
public class StoreRequest {

    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    private Date bannerExpiryDate;

    public StoreRequest(String bannerId, String bannerTitle, String bannerDescription, Date bannerExpiryDate) {
        this.bannerId = bannerId;
        this.bannerTitle = bannerTitle;
        this.bannerDescription = bannerDescription;
        this.bannerExpiryDate = bannerExpiryDate;
    }

    public StoreRequest(){

    }

    public String getBannerId() {
        return bannerId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public String getBannerDescription() {
        return bannerDescription;
    }

    public Date getBannerExpiryDate() {
        return bannerExpiryDate;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public void setBannerDescription(String bannerDescription) {
        this.bannerDescription = bannerDescription;
    }

    public void setBannerExpiryDate(Date bannerExpiryDate) {
        this.bannerExpiryDate = bannerExpiryDate;
    }

}
