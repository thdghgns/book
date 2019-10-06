package xyz.hohoon.book.security;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.hohoon.book.entity.Role;
import xyz.hohoon.book.entity.User;
import xyz.hohoon.book.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CustomUserDetailsServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    CustomUserDetailsService userDetailsService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsername_정상케이스() {
        String username = "test_user";
        String password = "change_me";
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_TESTER"));

        User expectedUser = new User(username, password, roles);

        when(userRepository.findById(username)).thenReturn(Optional.of(expectedUser));

        UserDetails actualUserDetails = userDetailsService.loadUserByUsername(username);

        verify(userRepository).findById(username);
        assertEquals(username, actualUserDetails.getUsername());
        assertEquals(password, actualUserDetails.getPassword());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_사용자가_없는_케이스() {
        String username = "test_user";

        when(userRepository.findById(username)).thenReturn(Optional.empty());

        userDetailsService.loadUserByUsername(username);

        verify(userRepository).findById(username);
    }
}