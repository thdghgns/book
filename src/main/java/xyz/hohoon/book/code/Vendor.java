package xyz.hohoon.book.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Vendor {
    KAKAO("query", "sort", "accuracy", "page", "size"),
    NAVER("query", "sort", "sim", "start", "display");

    private String queryKey;
    private String sortKey;
    private String sortValue;
    private String pageKey;
    private String sizeKey;
}
