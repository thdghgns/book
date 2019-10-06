package xyz.hohoon.book.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.event.BookSearchEvent;
import xyz.hohoon.book.service.BookSearchService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {
    @Mock
    ApplicationEventPublisher applicationEventPublisher;
    @Mock
    BookSearchService bookSearchService;
    @InjectMocks
    BookController bookController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void searchBook() throws Exception {
        String keyword = "java";
        int page = 3;
        int size = 10;

        Book.Response expectedResponse = new Book.Response();

        when(bookSearchService.searchBook(keyword, page, size)).thenReturn(expectedResponse);

        mockMvc.perform(get("/books").param("keyword", keyword))
        .andExpect(status().isOk());

        verify(applicationEventPublisher).publishEvent(any(BookSearchEvent.class));
    }
}