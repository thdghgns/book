package xyz.hohoon.book.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.dto.SearchCount;
import xyz.hohoon.book.event.BookSearchEvent;
import xyz.hohoon.book.service.BookSearchService;
import xyz.hohoon.book.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BookSearchService bookSearchService;
    @Autowired
    UserService userService;

    @GetMapping("/books")
    public Book.Response searchBook (Principal principal, @RequestParam String keyword, @PageableDefault(page = 1, size = 10) Pageable pageable) {
        publishBookSearchEvent(principal, keyword);

        return bookSearchService.searchBook(keyword, pageable.getPageNumber(), pageable.getPageSize());
    }

    @GetMapping("/books/top10Keyword")
    public List<SearchCount.Response> getPopularKeyword () {
        return bookSearchService.findTop10Keyword().stream()
                .map(searchCount -> modelMapper.map(searchCount, SearchCount.Response.class))
                .collect(Collectors.toList());
    }

    private void publishBookSearchEvent(Principal principal, String keyword) {
        applicationEventPublisher.publishEvent(new BookSearchEvent(this, ObjectUtils.isEmpty(principal) ? null : principal.getName(), keyword));
    }
}
