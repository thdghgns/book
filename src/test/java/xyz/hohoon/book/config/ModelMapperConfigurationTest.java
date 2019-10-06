package xyz.hohoon.book.config;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import xyz.hohoon.book.dto.Book;
import xyz.hohoon.book.dto.KakaoBook;
import xyz.hohoon.book.dto.NaverBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ModelMapperConfigurationTest {

    private ModelMapper modelMapper;

    @Before
    public void setup() {
        this.modelMapper = new ModelMapperConfiguration().modelMapper();
    }

    @Test
    public void mapperTest_KakaoBookDetailToBookDetail () {
        String title = "testBook_title";
        String thumbnailLink = "testBook_thumbnailLink";
        String description = "testBook_description";
        String isbn = "testBook_isbn";
        String author1 = "test_author_1";
        String author2 = "test_author_2";
        String publisher = "test_publisher";
        LocalDateTime publishDate = LocalDateTime.now();
        int price = 10000;
        int salePrice = 5000;

        KakaoBook.Detail kakaoBookDetail = new KakaoBook.Detail();
        kakaoBookDetail.setTitle(title);
        kakaoBookDetail.setThumbnailLink(thumbnailLink);
        kakaoBookDetail.setDescription(description);
        kakaoBookDetail.setIsbn(isbn);
        List<String> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        kakaoBookDetail.setAuthors(authors);
        kakaoBookDetail.setPublisher(publisher);
        kakaoBookDetail.setPublishDate(publishDate);
        kakaoBookDetail.setPrice(price);
        kakaoBookDetail.setSalePrice(salePrice);

        Book.Detail bookDetail = modelMapper.map(kakaoBookDetail, Book.Detail.class);

        assertEquals(kakaoBookDetail.getTitle(), bookDetail.getTitle());
        assertEquals(kakaoBookDetail.getThumbnailLink(), bookDetail.getThumbnailLink());
        assertEquals(kakaoBookDetail.getDescription(), bookDetail.getDescription());
        assertEquals(kakaoBookDetail.getIsbn(), bookDetail.getIsbn());
        assertEquals(String.join(",", authors), bookDetail.getAuthor());
        assertEquals(kakaoBookDetail.getPublisher(), bookDetail.getPublisher());
        assertEquals(publishDate.format(DateTimeFormatter.ISO_LOCAL_DATE), bookDetail.getPublishDate());
        assertEquals(String.valueOf(kakaoBookDetail.getPrice()), bookDetail.getPrice());
        assertEquals(String.valueOf(kakaoBookDetail.getSalePrice()), bookDetail.getSalePrice());
    }

    @Test
    public void mapperTest_NaverBookDetailToBookDetail() {
        String title = "testBook_title";
        String thumbnailLink = "testBook_thumbnailLink";
        String description = "testBook_description";
        String isbn = "testBook_isbn";
        String author = "test_author_1|test_author_2";
        String publisher = "test_publisher";
        LocalDate publishDate = LocalDate.now();
        String price = "10000";
        String salePrice = "5000";

        NaverBook.Detail naverBookDetail = new NaverBook.Detail();
        naverBookDetail.setTitle(title);
        naverBookDetail.setThumbnailLink(thumbnailLink);
        naverBookDetail.setDescription(description);
        naverBookDetail.setIsbn(isbn);
        naverBookDetail.setAuthor(author);
        naverBookDetail.setPublisher(publisher);
        naverBookDetail.setPublishDate(publishDate);
        naverBookDetail.setPrice(price);
        naverBookDetail.setSalePrice(salePrice);

        Book.Detail bookDetail = modelMapper.map(naverBookDetail, Book.Detail.class);

        assertEquals(naverBookDetail.getTitle(), bookDetail.getTitle());
        assertEquals(naverBookDetail.getThumbnailLink(), bookDetail.getThumbnailLink());
        assertEquals(naverBookDetail.getDescription(), bookDetail.getDescription());
        assertEquals(naverBookDetail.getIsbn(), bookDetail.getIsbn());
        assertEquals(author.replace("|", ","), bookDetail.getAuthor());
        assertEquals(naverBookDetail.getPublisher(), bookDetail.getPublisher());
        assertEquals(publishDate.format(DateTimeFormatter.ISO_LOCAL_DATE), bookDetail.getPublishDate());
        assertEquals(naverBookDetail.getPrice(), bookDetail.getPrice());
        assertEquals(naverBookDetail.getSalePrice(), bookDetail.getSalePrice());
    }
}