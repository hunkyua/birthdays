package ua.com.hunky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.hunky.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}