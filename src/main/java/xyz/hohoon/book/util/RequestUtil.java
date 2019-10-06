package xyz.hohoon.book.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import xyz.hohoon.book.code.Vendor;
import xyz.hohoon.book.dto.Book;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class RequestUtil<T> {

    @Value("${kakao.api.base-url}")
    private String kakaoBaseUrl;
    @Value("${kakao.api.book-search}")
    private String kakaoBookSearchApi;
    @Qualifier("kakaoHttpHeaders")
    @Autowired
    private HttpHeaders kakaoHttpHeaders;

    @Value("${naver.api.base-url}")
    private String naverBaseUrl;
    @Value("${naver.api.book-search}")
    private String naverBookSearchApi;
    @Qualifier("naverHttpHeaders")
    @Autowired
    private HttpHeaders naverHttpHeaders;

    public String makeSearchUrlString (Vendor vendor, Book.Request bookRequest) {
        String query = bookRequest.getQuery();
        try {
            query = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }

        return new StringBuilder()
                .append(kakaoBaseUrl).append(kakaoBookSearchApi)
                .append("?")
                .append(vendor.getQueryKey()).append("=").append(query)
                .append("&").append(vendor.getSortKey()).append("=").append(vendor.getSortValue())
                .append("&").append(vendor.getPageKey()).append("=").append(bookRequest.getPage())
                .append("&").append(vendor.getSizeKey()).append("=").append(bookRequest.getSize())
                .toString();
    }

    public HttpEntity<String> getRequestEntity(Vendor vendor) {
        switch (vendor) {
            case KAKAO:
                return new HttpEntity<>(kakaoHttpHeaders);
            case NAVER:
                return new HttpEntity<>(naverHttpHeaders);
            default: return null;
        }
    }
}
