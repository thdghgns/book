package xyz.hohoon.book.service;

import xyz.hohoon.book.dto.Book;

public interface ExternalBookSearchService {
    Book.Response searchBooks(Book.Request bookRequest);
}
