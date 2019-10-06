package xyz.hohoon.book.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.hohoon.book.repository.SearchCountRepository;
import xyz.hohoon.book.repository.SearchHistoryRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventListenersTest {
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private SearchCountRepository searchCountRepository;
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Test
    public void processBookSearchEvent() {
        String username = "testUser";
        String keyword = "java";

        assertFalse(searchCountRepository.findById(keyword).isPresent());
        assertEquals(0, searchHistoryRepository.findTop10ByUsernameOrderByCreatedDateDesc(username).size());

        for (int i = 1; i< 10; i++) {
            publisher.publishEvent(new BookSearchEvent(this, username, keyword));
            assertEquals(i, searchCountRepository.findById(keyword).get().getCount());
            assertEquals(i, searchHistoryRepository.findTop10ByUsernameOrderByCreatedDateDesc(username).size());
        }
    }
}