package com.example.banner.request;

import lombok.*;

import java.util.Date;

/**
 * Created by supritidan on 2017/09/23.
 */
@Data
@AllArgsConstructor
@Setter
public class StoreRequest {

    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    private Date bannerExpiryDate;
}
