package xyz.hohoon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.hohoon.book.entity.SearchCount;

import java.util.List;

public interface SearchCountRepository extends JpaRepository<SearchCount, String> {
    @Modifying
    @Query("UPDATE SearchCount sc SET sc.count = sc.count +1 , sc.lastModifiedDate = CURRENT_TIMESTAMP WHERE sc.keyword = :keyword")
    Integer increaseSearchCount(@Param("keyword") String keyword);
    List<SearchCount> findTop10ByOrderByCountDesc();
}