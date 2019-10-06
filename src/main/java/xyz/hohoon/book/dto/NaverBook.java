package xyz.hohoon.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class NaverBook {

    @Getter
    @Setter
    public static class Response {
//        lastBuildDate ("Tue, 01 Oct 2019 20:03:32 +0900")
        @JsonProperty("lastBuildDate")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss ZZ", timezone = "Asia/Seoul")
        private LocalDateTime lastBuildDate;
        @JsonProperty("total")
        private int totalCount;
        @JsonProperty("start")
        private int page;
        @JsonProperty("display")
        private int size;
        @JsonProperty("items")
        private List<Detail> details;
    }

    // 필요없는 정보 : title; link;
    @Getter
    @Setter
    public static class Detail {
        @JsonProperty("title")
        private String title;
        @JsonProperty("link")
        private String link;
        @JsonProperty("image")
        private String thumbnailLink;
        @JsonProperty("author")
        private String author;
        @JsonProperty("price")
        private String price;
        @JsonProperty("discount")
        private String salePrice;
        @JsonProperty("publisher")
        private String publisher;
//        pubdate ("20181101")
        @JsonProperty("pubdate")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
        private LocalDate publishDate;
        @JsonProperty("isbn")
        private String isbn;
        @JsonProperty("description")
        private String description;
    }

}