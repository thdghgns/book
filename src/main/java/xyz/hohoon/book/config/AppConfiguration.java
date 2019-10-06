package xyz.hohoon.book.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@EnableCaching
@EnableJpaAuditing
@EnableRetry
@Configuration
public class AppConfiguration {
}
