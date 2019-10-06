package xyz.hohoon.book.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.entity.SearchCount;
import xyz.hohoon.book.exception.ApiException;
import xyz.hohoon.book.repository.SearchCountRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class BookSearchServiceImplTest {
    @Mock
    ExternalBookSearchService kakaoExternalBookSearchService;
    @Mock
    SearchCountRepository searchCountRepository;

    @InjectMocks
    BookSearchServiceImpl bookSearchService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void searchBook_정상_케이스() {
        String keyword = "java";
        int page = 1;
        int size = 10;
        ArgumentCaptor<Book.Request> argumentCaptor= ArgumentCaptor.forClass(Book.Request.class);

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(1000);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenReturn(expectedResponse);

        Book.Response actualResponse = bookSearchService.searchBook(keyword, page, size);

        verify(kakaoExternalBookSearchService).searchBooks(argumentCaptor.capture());

        assertEquals(keyword, argumentCaptor.getValue().getQuery());
        assertEquals(String.valueOf(page), argumentCaptor.getValue().getPage());
        assertEquals(String.valueOf(size), argumentCaptor.getValue().getSize());
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void searchBook_최대_페이지사이즈를_넘는_케이스() {
        String keyword = "java";
        int page = 1;
        int size = 1024;
        ArgumentCaptor<Book.Request> argumentCaptor= ArgumentCaptor.forClass(Book.Request.class);

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(1000);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenReturn(expectedResponse);

        Book.Response actualResponse = bookSearchService.searchBook(keyword, page, size);

        verify(kakaoExternalBookSearchService).searchBooks(argumentCaptor.capture());

        assertEquals(String.valueOf(page), argumentCaptor.getValue().getPage());
        assertEquals(keyword, argumentCaptor.getValue().getQuery());
        assertEquals(String.valueOf(50), argumentCaptor.getValue().getSize());
    }

    @Test
    public void searchBook_최소_페이지사이즈를_넘는_케이스() {
        String keyword = "java";
        int page = 1;
        int size = 5;
        ArgumentCaptor<Book.Request> argumentCaptor= ArgumentCaptor.forClass(Book.Request.class);

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(1000);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenReturn(expectedResponse);

        Book.Response actualResponse = bookSearchService.searchBook(keyword, page, size);

        verify(kakaoExternalBookSearchService).searchBooks(argumentCaptor.capture());

        assertEquals(String.valueOf(page), argumentCaptor.getValue().getPage());
        assertEquals(keyword, argumentCaptor.getValue().getQuery());
        assertEquals(String.valueOf(10), argumentCaptor.getValue().getSize());
    }

    @Test
    public void searchBook_최대_페이지를_넘는_케이스() {
        String keyword = "java";
        int page = 400;
        int size = 10;
        ArgumentCaptor<Book.Request> argumentCaptor= ArgumentCaptor.forClass(Book.Request.class);

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(1000);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenReturn(expectedResponse);

        Book.Response actualResponse = bookSearchService.searchBook(keyword, page, size);

        verify(kakaoExternalBookSearchService).searchBooks(argumentCaptor.capture());

        assertEquals(String.valueOf(100), argumentCaptor.getValue().getPage());
        assertEquals(keyword, argumentCaptor.getValue().getQuery());
        assertEquals(String.valueOf(size), argumentCaptor.getValue().getSize());
    }

    @Test
    public void searchBook_최대_토탈사이즈를_넘는_케이스() {
        String keyword = "java";
        int page = 1;
        int size = 10;
        int totalSize = 1000000;

        ArgumentCaptor<Book.Request> argumentCaptor= ArgumentCaptor.forClass(Book.Request.class);

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(totalSize);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenReturn(expectedResponse);

        Book.Response actualResponse = bookSearchService.searchBook(keyword, page, size);

        verify(kakaoExternalBookSearchService).searchBooks(argumentCaptor.capture());

        assertEquals(1000, actualResponse.getTotalCount());
        assertEquals(keyword, argumentCaptor.getValue().getQuery());
        assertEquals(String.valueOf(page), argumentCaptor.getValue().getPage());
        assertEquals(String.valueOf(size), argumentCaptor.getValue().getSize());
    }

    @Test(expected = ApiException.ClientException.class)
    public void searchBook_클라이언트_에러_발생_케이스() {
        String keyword = "java";
        int page = 1;
        int size = 10;

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(1000);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenThrow(ApiException.ClientException.class);

        bookSearchService.searchBook(keyword, page, size);
    }

    @Test(expected = ApiException.ServerException.class)
    public void searchBook_서버_에러_발생_케이스() {
        String keyword = "java";
        int page = 1;
        int size = 10;

        Book.Response expectedResponse = new Book.Response();
        expectedResponse.setTotalCount(1000);

        when(kakaoExternalBookSearchService.searchBooks(any())).thenThrow(ApiException.ServerException.class);

        bookSearchService.searchBook(keyword, page, size);
    }

    @Test
    public void findTop10Keyword() {
        List<SearchCount> expectedSearchCounts = new ArrayList<>();
        when(searchCountRepository.findTop10ByOrderByCountDesc()).thenReturn(expectedSearchCounts);

        List<SearchCount> actualSearchCounts = bookSearchService.findTop10Keyword();

        verify(searchCountRepository).findTop10ByOrderByCountDesc();

        assertEquals(expectedSearchCounts, actualSearchCounts);
    }
}