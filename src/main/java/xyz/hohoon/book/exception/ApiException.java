package xyz.hohoon.book.exception;

public class ApiException {
    public static class ServerException extends RuntimeException{
        public ServerException(String message) {
            super(message);
        }
    }
    public static class ClientException extends RuntimeException{
        public ClientException(String message) {
            super(message);
        }
    }
}
