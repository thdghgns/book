package xyz.hohoon.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.entity.SearchCount;
import xyz.hohoon.book.repository.SearchCountRepository;

import java.util.List;

@Service
public class BookSearchServiceImpl implements BookSearchService {
    @Qualifier("kakaoBookSearchService")
    @Autowired
    ExternalBookSearchService kakaoExternalBookSearchService;
    @Autowired
    SearchCountRepository searchCountRepository;

    private static final int MIN_SIZE = 10;
    private static final int MAX_SIZE = 50;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_PAGE = 100;
    private static final int MAX_TOTAL_SIZE = 1000;

    @Override
    @Cacheable(value = "books")
    public Book.Response searchBook(String keyword, int page, int size) {

        Book.Request bookRequest = Book.Request.builder()
                .query(keyword)
                .page(String.valueOf(page > MAX_PAGE ? MAX_PAGE : page))
                .size(String.valueOf(refineSize(size)))
                .build();

        Book.Response kakaoResponse = kakaoExternalBookSearchService.searchBooks(bookRequest);

        return refineResponse(kakaoResponse, page, size);
    }

    private int refineSize(int size) {
        if (size >= MIN_SIZE && size <= MAX_SIZE) {
            return size;
        } else if (size < MIN_SIZE) {
            return MIN_SIZE;
        } else if (size > MAX_SIZE) {
            return MAX_SIZE;
        } else {
            return DEFAULT_SIZE;
        }
    }

    private Book.Response refineResponse(Book.Response response, int page, int size) {
        response.setPage(page > MAX_PAGE ? MAX_PAGE : page);
        response.setSize(size);
        response.setTotalCount(response.getTotalCount() > MAX_TOTAL_SIZE ? MAX_TOTAL_SIZE : response.getTotalCount());

        return response;
    }

    @Override
    public List<SearchCount> findTop10Keyword() {
        return searchCountRepository.findTop10ByOrderByCountDesc();
    }
}
