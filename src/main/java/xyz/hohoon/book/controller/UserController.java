package xyz.hohoon.book.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import xyz.hohoon.book.dto.SearchHistory;
import xyz.hohoon.book.dto.User;
import xyz.hohoon.book.exception.UserException;
import xyz.hohoon.book.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/user")
    @ResponseBody
    public String signUp(@RequestBody @Valid User.Request request) throws Exception {
        userService.signUp(request.getUsername(), request.getPassword());
        return "ok";
    }

    @GetMapping("/user/history")
    public List<SearchHistory.Response> history(Principal principal) {
        if (ObjectUtils.isEmpty(principal)) {
            throw new UserException.NotAuthorized("only logged in user can get search history");
        }

        return userService.getUserSearchHistory(principal.getName()).stream()
                .map(history -> modelMapper.map(history, SearchHistory.Response.class))
                .collect(Collectors.toList());
    }
}
