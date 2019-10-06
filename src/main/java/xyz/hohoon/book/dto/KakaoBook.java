package xyz.hohoon.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class KakaoBook {

    @Getter
    @Setter
    public static class Response {
        @JsonProperty("documents")
        private List<Detail> details;
        @JsonProperty("meta")
        private Metadata metadata;

    }

    // 필요없는 정보 : status; translators; link;
    @Getter
    @Setter
    public static class Detail {
        @JsonProperty("authors")
        private List<String> authors;
        @JsonProperty("contents")
        private String description;
//        [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
//    datetime ("2018-06-01T00:00:00.000+09:00")
        @JsonProperty("datetime")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Seoul")
        private LocalDateTime publishDate;
        @JsonProperty("isbn")
        private String isbn;
        @JsonProperty("price")
        private Integer price;
        @JsonProperty("publisher")
        private String publisher;
        @JsonProperty("sale_price")
        private int salePrice;
        @JsonProperty("status")
        private String status;
        @JsonProperty("thumbnail")
        private String thumbnailLink;
        @JsonProperty("title")
        private String title;
        @JsonProperty("translators")
        private List<String> translators;
        @JsonProperty("url")
        private String link;
    }

    @Getter
    @Setter
    public static class Metadata {
        @JsonProperty("is_end")
        private boolean isEnd;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("total_count")
        private int totalCount;
    }
}
