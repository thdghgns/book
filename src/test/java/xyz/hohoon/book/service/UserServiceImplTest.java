package xyz.hohoon.book.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.hohoon.book.entity.SearchHistory;
import xyz.hohoon.book.entity.User;
import xyz.hohoon.book.exception.UserException;
import xyz.hohoon.book.repository.SearchHistoryRepository;
import xyz.hohoon.book.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SearchHistoryRepository searchHistoryRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signUp_정상_케이스() {
        String username = "test_user";
        String password = "change_me";
        String encryptedPassword = "encrypted_password";

        User expectedUser = new User(username, encryptedPassword, null);
        ArgumentCaptor<User> argumentCaptor= ArgumentCaptor.forClass(User.class);

        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        when(passwordEncoder.encode(anyString())).thenReturn(encryptedPassword);

        boolean success = userService.signUp(username, password);

        verify(userRepository).save(argumentCaptor.capture());
        assertEquals(encryptedPassword, argumentCaptor.getValue().getPassword());
        assertTrue(success);
    }

    @Test(expected = UserException.Duplicated.class)
    public void signUp_사용자중복_케이스() {
        String username = "test_user";
        String password = "change_me";
        String encryptedPassword = "encrypted_password";

        User expectedUser = new User(username, encryptedPassword, null);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(expectedUser));

        userService.signUp(username, password);

        verify(userRepository).findById(username);
    }

    @Test(expected = UserException.Unknown.class)
    public void signUp_저장결과_null인_케이스() {
        String username = "test_user";
        String password = "change_me";

        ArgumentCaptor<User> argumentCaptor= ArgumentCaptor.forClass(User.class);

        when(userRepository.save(any(User.class))).thenReturn(null);

        userService.signUp(username, password);

        verify(userRepository).save(argumentCaptor.capture());
        assertEquals(username, argumentCaptor.getValue().getName());
    }

    @Test
    public void getUserSearchHistory_정상케이스() {
        String username = "test_user";
        String password = "change_me";
        String encryptedPassword = "encrypted_password";

        User expectedUser = new User(username, encryptedPassword, null);
        List<SearchHistory> expectedSearchHistories = new ArrayList<>();
        expectedSearchHistories.add(new SearchHistory(1L, username, "java"));
        expectedSearchHistories.add(new SearchHistory(2L, username, "python"));
        expectedSearchHistories.add(new SearchHistory(3L, username, "javascript"));
        expectedSearchHistories.add(new SearchHistory(4L, username, "go"));

        when(userRepository.findById(anyString())).thenReturn(Optional.of(expectedUser));
        when(searchHistoryRepository.findTop10ByUsernameOrderByCreatedDateDesc(username)).thenReturn(expectedSearchHistories);

        List<SearchHistory> actualSearchHistories = userService.getUserSearchHistory(username);

        verify(userRepository).findById(username);
        verify(searchHistoryRepository).findTop10ByUsernameOrderByCreatedDateDesc(username);

        assertEquals(expectedSearchHistories, actualSearchHistories);
    }

    @Test(expected = UserException.NotExist.class)
    public void getUserSearchHistory_사용자가_없는_케이스() {
        String username = "test_user";

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        userService.getUserSearchHistory(username);

        verify(userRepository).findById(username);
    }

    @Test
    public void getUserSearchHistory_히스토리가_없는_케이스() {
        String username = "test_user";
        String password = "change_me";
        String encryptedPassword = "encrypted_password";

        User expectedUser = new User(username, encryptedPassword, null);
        List<SearchHistory> expectedSearchHistories = new ArrayList<>();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(expectedUser));
        when(searchHistoryRepository.findTop10ByUsernameOrderByCreatedDateDesc(username)).thenReturn(expectedSearchHistories);

        List<SearchHistory> actualSearchHistories = userService.getUserSearchHistory(username);

        verify(userRepository).findById(username);
        verify(searchHistoryRepository).findTop10ByUsernameOrderByCreatedDateDesc(username);

        assertEquals(expectedSearchHistories, actualSearchHistories);
        assertEquals(0, actualSearchHistories.size());
    }
}