package xyz.hohoon.book.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import xyz.hohoon.book.code.Vendor;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.dto.NaverBook;
import xyz.hohoon.book.exception.ApiException;
import xyz.hohoon.book.util.RequestUtil;

@Service("naverBookSearchService")
public class NaverExternalBookSearchServiceImpl implements ExternalBookSearchService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RequestUtil requestUtil;

    @Override
    public Book.Response searchBooks(Book.Request bookRequest) {
        String url = requestUtil.makeSearchUrlString(Vendor.NAVER, bookRequest);
        HttpEntity<String> requestEntity = requestUtil.getRequestEntity(Vendor.NAVER);

        ResponseEntity<NaverBook.Response> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, NaverBook.Response.class);
        } catch (HttpClientErrorException clientException) {
            throw new ApiException.ClientException("bad request : " + bookRequest.toString());
        } catch (HttpServerErrorException serverException) {
            throw new ApiException.ServerException("naver server failed to process request");
        }

        return modelMapper.map(responseEntity.getBody(), Book.Response.class);
    }
}