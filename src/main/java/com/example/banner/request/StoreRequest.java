package com.example.banner.request;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {

    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    private Date startDate;
    private Date expiryDate;
}
