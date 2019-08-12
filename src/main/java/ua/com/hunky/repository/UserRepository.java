package ua.com.hunky.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.com.hunky.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}