package xyz.hohoon.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

public class Book {

    @Getter
    @Builder
    @ToString
    public static class Request {
        private String query;
        private String page;
        private String size;
    }

    @Getter
    @Setter
    public static class Response implements Serializable {
        private int totalCount;
        private int page;
        private int size;
        private List<Detail> details;
    }

    @Getter
    @Setter
    public static class Detail implements Serializable {
        private String title;
        private String thumbnailLink;
        private String description;
        private String isbn;
        private String author;
        private String publisher;
        private String publishDate;
        private String price;
        private String salePrice;
    }
}
