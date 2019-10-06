package xyz.hohoon.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import xyz.hohoon.book.entity.Role;
import xyz.hohoon.book.entity.SearchHistory;
import xyz.hohoon.book.entity.User;
import xyz.hohoon.book.exception.UserException;
import xyz.hohoon.book.repository.SearchHistoryRepository;
import xyz.hohoon.book.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    private static final String defaultRole = "ROLE_USER";

    @Override
    public boolean signUp(String username, String password) {
        if (isUserExist(username)) {
            throw new UserException.Duplicated("username duplicated");
        }

        User user = userRepository.save(new User(username, passwordEncoder.encode(password), getRoles()));

        if (ObjectUtils.isEmpty(user)) {
            throw new UserException.Unknown("failed to sign up");
        } else {
            return true;
        }
    }

    @Override
    public List<SearchHistory> getUserSearchHistory(String username) {
        if (!isUserExist(username)) {
            throw new UserException.NotExist("user not exist");
        }

        List<SearchHistory> histories = searchHistoryRepository.findTop10ByUsernameOrderByCreatedDateDesc(username);

        return ObjectUtils.isEmpty(histories) ? new ArrayList<>() : histories;
    }

    private boolean isUserExist(String username) {
        return userRepository.findById(username).isPresent();
    }

    private List<Role> getRoles() {
        return getRoles(defaultRole);
    }

    private List<Role> getRoles(String role) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(role));

        return roles;
    }
}
