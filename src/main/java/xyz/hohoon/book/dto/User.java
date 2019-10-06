package xyz.hohoon.book.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class User {
    @Getter
    @Setter
    @ToString
    public static class Request {
        @NotBlank
        private String username;
        @NotBlank
        @Size(min = 4)
        private String password;
    }
}
