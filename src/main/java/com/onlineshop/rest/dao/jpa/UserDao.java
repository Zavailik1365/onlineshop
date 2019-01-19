package com.onlineshop.rest.dao.jpa;

import com.onlineshop.rest.dao.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(long id);
}