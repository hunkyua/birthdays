package ua.com.hunky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hunky.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}