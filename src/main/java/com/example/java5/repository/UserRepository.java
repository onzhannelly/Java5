package com.example.java5.repository;


import com.example.java5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Дополнительные методы (если нужны)
    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    User findByEmail(String email);
}
