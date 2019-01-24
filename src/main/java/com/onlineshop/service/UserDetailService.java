package com.onlineshop.service;

import com.onlineshop.dao.entitys.Role;
import com.onlineshop.dao.entitys.User;
import com.onlineshop.dao.jpa.UserDao;
import com.onlineshop.dto.UserResponse;
import com.onlineshop.exception.UserAlreadyExist;
import com.onlineshop.exception.UserNotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public UserResponse addUser(User user) throws UserAlreadyExist {
        User userFromDb = userDao.findByUsername(user.getUsername());

        if (userFromDb != null) {
            throw new UserAlreadyExist(userFromDb.getUsername());
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userDao.save(user);

        return new UserResponse(
                user.getUsername(),
                user.getId(),
                user.isActive(),
                user.getRoles());
    }

    public User getAuthenticationUser() {

        return (User) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public List<UserResponse> findAll(){

        List<UserResponse> userResponseList = new ArrayList<>();
        List userList = userDao.findAll();

        for (Object item:userList) {
            User user = (User) item;
            userResponseList.add(
                    new UserResponse(
                            user.getUsername(),
                            user.getId(),
                            user.isActive(),
                            user.getRoles()));
        }

        return userResponseList;
    }

    public UserResponse findById(long id, User user) throws UserNotFound {

        if (user == null) {
            throw new UserNotFound(id);
        }

        return new UserResponse(
                    user.getUsername(),
                    user.getId(),
                    user.isActive(),
                    user.getRoles());
    }


    public UserResponse update(long id, User userFromDB, User user) throws UserNotFound {

        if (userFromDB == null) {
            throw new UserNotFound(id);
        }

        BeanUtils.copyProperties(
                user,
                userFromDB,
                "id");

        userDao.save(userFromDB);

        return new UserResponse(
                user.getUsername(),
                user.getId(),
                user.isActive(),
                user.getRoles());
    }

    public void delete(long id, User userFromDB) throws UserNotFound{

        if (userFromDB == null) {
            throw new UserNotFound(id);
        }

        userDao.delete(userFromDB);
    }
}
