package xyz.hohoon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hohoon.book.entity.SearchHistory;
import xyz.hohoon.book.entity.User;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findTop10ByUsernameOrderByCreatedDateDesc(String username);
}
