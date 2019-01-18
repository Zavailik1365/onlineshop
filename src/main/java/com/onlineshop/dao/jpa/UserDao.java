package com.onlineshop.dao.jpa;

import com.onlineshop.dao.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}