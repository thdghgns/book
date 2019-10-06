package xyz.hohoon.book.service;

import xyz.hohoon.book.entity.SearchHistory;
import xyz.hohoon.book.entity.User;

import java.util.List;

public interface UserService {
    boolean signUp(String username, String password);
    List<SearchHistory> getUserSearchHistory(String username);
}
