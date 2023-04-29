package com.dhmusic.DHMusic.repositories.account_repositories;

import com.dhmusic.DHMusic.entities.account.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAll();

    boolean existsById(Long id);

    User getUserById(Long id);

    void deleteById(Long id);

    boolean existsUserByEmail(String email);
    User findUserById(long id);
    boolean existsUserByUsernameIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);




}
