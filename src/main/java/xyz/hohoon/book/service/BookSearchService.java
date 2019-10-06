package xyz.hohoon.book.service;

import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.entity.SearchCount;

import java.io.IOException;
import java.util.List;

public interface BookSearchService {
    Book.Response searchBook(String keyword, int page, int size);
    List<SearchCount> findTop10Keyword();
}
