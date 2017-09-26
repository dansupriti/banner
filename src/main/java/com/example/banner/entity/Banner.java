package com.example.banner.entity;

import static com.example.banner.common.ApiConstants.BANNER_INDEX;
import static com.example.banner.common.ApiConstants.DB_DATE_TIME_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = BANNER_INDEX, createIndex = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Banner{

    @Id
    private String bannerId;
    private String bannerTitle;
    private String bannerDescription;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DB_DATE_TIME_FORMAT)
	private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DB_DATE_TIME_FORMAT)
    private Date expiryDate;
}
