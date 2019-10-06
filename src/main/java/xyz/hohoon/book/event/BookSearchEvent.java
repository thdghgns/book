package xyz.hohoon.book.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import xyz.hohoon.book.entity.User;

@Getter
public class BookSearchEvent extends ApplicationEvent {
    private final String username;
    private final String keyword;

    public BookSearchEvent(Object source, String username, String keyword) {
        super(source);
        this.username = username;
        this.keyword = keyword;
    }
}
