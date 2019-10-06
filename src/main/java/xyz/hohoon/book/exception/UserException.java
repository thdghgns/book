package xyz.hohoon.book.exception;

public class UserException {
    public static class NotAuthorized extends RuntimeException {
        public NotAuthorized(String message) {
            super(message);
        }
    }
    public static class NotExist extends RuntimeException {
        public NotExist(String mesage) {
            super(mesage);
        }
    }
    public static class Duplicated extends RuntimeException {
        public Duplicated(String message) {
            super(message);
        }
    }
    public static class Unknown extends RuntimeException {
        public Unknown(String message) {
            super(message);
        }
    }
}
