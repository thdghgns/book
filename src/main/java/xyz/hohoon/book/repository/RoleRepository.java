package xyz.hohoon.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.hohoon.book.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
