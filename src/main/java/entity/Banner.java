package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

import static com.example.banner.common.ApiConstants.BANNER_INDEX;
import static com.example.banner.common.ApiConstants.DB_DATE_TIME_FORMAT;

@Document(indexName = BANNER_INDEX, createIndex = false)

public class Banner{

    @Id
    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DB_DATE_TIME_FORMAT)
    private Date bannerExpiryDate;

    public Banner(){ }

    public Banner(String bannerId, String bannerTitle, String bannerDescription, Date bannerExpiryDate) {
        this.bannerId = bannerId;
        this.bannerTitle = bannerTitle;
        this.bannerDescription = bannerDescription;
        this.bannerExpiryDate = bannerExpiryDate;
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
}
