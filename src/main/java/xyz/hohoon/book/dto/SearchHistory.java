package xyz.hohoon.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SearchHistory {
    @Getter
    @Setter
    public static class Response implements Serializable {
        private String keyword;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime createdDate;
    }
}
