package com.dhmusic.DHMusic.repositories.account.repositories;

import com.dhmusic.DHMusic.entities.account.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
