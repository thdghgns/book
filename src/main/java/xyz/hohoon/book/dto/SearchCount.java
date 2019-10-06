package xyz.hohoon.book.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class SearchCount {
    @Getter
    @Setter
    public static class Response implements Serializable {
        private String keyword;
        private int count;
    }
}
