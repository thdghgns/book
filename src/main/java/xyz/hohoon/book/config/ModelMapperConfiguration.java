package xyz.hohoon.book.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.dto.KakaoBook;
import xyz.hohoon.book.dto.NaverBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);
        setKakaoObjectMapping(modelMapper);
        setNaverObjectMappings(modelMapper);

        return modelMapper;
    }

    private void setKakaoObjectMapping(ModelMapper modelMapper) {
        Converter<LocalDateTime, String> localDateTimeStringConverter = mappingContext -> {
            if (ObjectUtils.isEmpty(mappingContext.getSource())) {
                return "";
            } else {
                return mappingContext.getSource().format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        };
        Converter<List<String>, String> listStringConverter = mappingContext -> String.join(",", mappingContext.getSource());

        modelMapper.addMappings(new PropertyMap<KakaoBook.Detail, Book.Detail>() {
            @Override
            protected void configure() {
                map().setTitle(source.getTitle());
                map().setThumbnailLink(source.getThumbnailLink());
                map().setDescription(source.getDescription());
                map().setIsbn(source.getIsbn());
                using(listStringConverter).map(source.getAuthors()).setAuthor(null);
                map().setPublisher(source.getPublisher());
                using(localDateTimeStringConverter).map(source.getPublishDate()).setPublishDate(null);
                map().setPrice(String.valueOf(source.getPrice()));
                map().setSalePrice(String.valueOf(source.getSalePrice()));
            }
        });

        Converter<List<KakaoBook.Detail>, List<Book.Detail>> kakaoDetailToDetailConverter = mappingContext -> mappingContext.getSource().stream()
                .map(kakaoBookDetail -> modelMapper.map(kakaoBookDetail, Book.Detail.class))
                .collect(Collectors.toList());

        modelMapper.addMappings(new PropertyMap<KakaoBook.Response, Book.Response>() {
            @Override
            protected void configure() {
                map().setTotalCount(source.getMetadata().getTotalCount());
                using(kakaoDetailToDetailConverter).map(source.getDetails()).setDetails(null);
            }
        });
    }

    private void setNaverObjectMappings(ModelMapper modelMapper) {
        Converter<LocalDate, String> localDateStringConverter = mappingContext -> {
            if (ObjectUtils.isEmpty(mappingContext.getSource())) {
                return "";
            } else {
                return mappingContext.getSource().format(DateTimeFormatter.ISO_LOCAL_DATE);
            }
        };
        Converter<String, String> verticalSlashReplaceConverter = mappingContext -> mappingContext.getSource().replace("|", ",");

        modelMapper.addMappings(new PropertyMap<NaverBook.Detail, Book.Detail>() {
            @Override
            protected void configure() {
                map().setTitle(source.getTitle());
                map().setThumbnailLink(source.getThumbnailLink());
                map().setDescription(source.getDescription());
                map().setIsbn(source.getIsbn());
                using(verticalSlashReplaceConverter).map().setAuthor(source.getAuthor());
                map().setPublisher(source.getPublisher());
                using(localDateStringConverter).map(source.getPublishDate()).setPublishDate(null);
                map().setPrice(source.getPrice());
                map().setSalePrice(source.getSalePrice());
            }
        });

        Converter<List<NaverBook.Detail>, List<Book.Detail>> naverDetailToDetailConverter = mappingContext -> mappingContext.getSource().stream()
                    .map(naverBookDetail -> modelMapper.map(naverBookDetail, Book.Detail.class))
                    .collect(Collectors.toList());

        modelMapper.addMappings(new PropertyMap<NaverBook.Response, Book.Response>() {
            @Override
            protected void configure() {
                map().setTotalCount(source.getTotalCount());
                using(naverDetailToDetailConverter).map(source.getDetails()).setDetails(null);
            }
        });
    }
}
