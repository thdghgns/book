package xyz.hohoon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hohoon.book.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}
