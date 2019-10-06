package xyz.hohoon.book.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import xyz.hohoon.book.entity.SearchCount;
import xyz.hohoon.book.entity.SearchHistory;
import xyz.hohoon.book.repository.SearchCountRepository;
import xyz.hohoon.book.repository.SearchHistoryRepository;

@Component
public class EventListeners {
    @Autowired
    SearchCountRepository searchCountRepository;
    @Autowired
    SearchHistoryRepository searchHistoryRepository;

    @Transactional
    @EventListener(value = BookSearchEvent.class)
    public void processBookSearchEvent(BookSearchEvent event) {
        int count = searchCountRepository.increaseSearchCount(event.getKeyword());
        if (count == 0) {
            searchCountRepository.save(SearchCount.builder()
                    .keyword(event.getKeyword())
                    .count(1).build());
        }

        if (!StringUtils.isEmpty(event.getUsername())) {
            SearchHistory searchHistory = SearchHistory.builder()
                    .keyword(event.getKeyword())
                    .username(event.getUsername())
                    .build();

            searchHistoryRepository.save(searchHistory);
        }
    }
}
