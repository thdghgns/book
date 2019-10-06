package xyz.hohoon.book.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import xyz.hohoon.book.code.Vendor;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.dto.KakaoBook;
import xyz.hohoon.book.exception.ApiException;
import xyz.hohoon.book.util.RequestUtil;

@Service("kakaoBookSearchService")
public class KakaoExternalBookSearchServiceImpl implements ExternalBookSearchService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RequestUtil requestUtil;

    @Qualifier("naverBookSearchService")
    @Autowired
    ExternalBookSearchService naverExternalBookSearchService;

    @Override
    @Retryable(value = { ApiException.ServerException.class },
            maxAttempts = 2,
            backoff = @Backoff(delay = 3000))
    public Book.Response searchBooks(Book.Request bookRequest) {
        String url = requestUtil.makeSearchUrlString(Vendor.KAKAO, bookRequest);
        HttpEntity<String> requestEntity = requestUtil.getRequestEntity(Vendor.KAKAO);

        ResponseEntity<KakaoBook.Response> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, KakaoBook.Response.class);
        } catch (HttpClientErrorException clientException) {
            throw new ApiException.ClientException("bad request : " + bookRequest.toString());
        } catch (HttpServerErrorException serverException) {
            throw new ApiException.ServerException("kakao server failed to process request");
        }

        return modelMapper.map(responseEntity.getBody(), Book.Response.class);
    }

    @Recover
    public Book.Response recoverSearchBook(ApiException.ServerException exception, Book.Request bookRequest) {
        return naverExternalBookSearchService.searchBooks(bookRequest);
    }
}
