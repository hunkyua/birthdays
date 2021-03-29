package ua.com.hunky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hunky.model.User;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User findByActivationCode(String code);

    User findByChatID(Long chatID);

    List<User> findAllByChatID(Long chatID);

}