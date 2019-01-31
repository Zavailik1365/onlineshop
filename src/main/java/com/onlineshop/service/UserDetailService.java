package com.onlineshop.service;

import com.onlineshop.dao.entitys.Role;
import com.onlineshop.dao.entitys.User;
import com.onlineshop.dao.jpa.UserDao;
import com.onlineshop.dto.UserRequest;
import com.onlineshop.dto.UserRequestAdmin;
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

    /**
     * Обработка запросов клиентов
     */

    public List<UserResponse> findAll(){

        List<UserResponse> userResponseList = new ArrayList<>();
        List userList = userDao.findAll();

        for (Object item:userList) {
            User user = (User) item;
            userResponseList.add(
                    newUserUserResponse(user));
        }

        return userResponseList;
    }

    public UserResponse findById(long id, User user)
            throws UserNotFound {

        if (user == null) {
            throw new UserNotFound(id);
        }

        return newUserUserResponse(user);
    }


    public void addUser(UserRequest userRequest) throws UserAlreadyExist {

        User userFromDb = userDao.findByUsername(userRequest.getUsername());

        if (userFromDb != null) {
            throw new UserAlreadyExist(userFromDb.getUsername());
        }

        User user = new User();

        BeanUtils.copyProperties(
                userRequest,
                user,
                ignoreProperties());

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userDao.save(user);

    }

    public UserResponse authenticationUserUpdate(
                User userFromDB,
                UserRequest userRequest)
            throws UserNotFound {

        if (userFromDB == null) {
            throw new UserNotFound(0);
        }

        BeanUtils.copyProperties(
                userRequest,
                userFromDB,
                ignoreProperties());

        if (userRequest.getPassword() != null
                && userRequest.getPassword().trim().length() != 0) {
            userFromDB.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        userDao.save(userFromDB);

        return newUserUserResponse(userFromDB);
    }

    public UserResponse updateAdmin(
            long id,
            User userFromDB,
            UserRequestAdmin userRequestAdmin)
            throws UserNotFound {

        if (userFromDB == null) {
            throw new UserNotFound(id);
        }

        BeanUtils.copyProperties(
                userRequestAdmin,
                userFromDB,
                ignoreProperties());

        userFromDB.setRoles(userRequestAdmin.getRoles());
        if (userRequestAdmin.getPassword() != null
                && userRequestAdmin.getPassword().trim().length() != 0) {
            userFromDB.setPassword(passwordEncoder.encode(userRequestAdmin.getPassword()));
        }

        userDao.save(userFromDB);

        return newUserUserResponse(userFromDB);
    }

    public UserResponse authenticationUser(User user){

        return newUserUserResponse(user);
    }

    public List<Role> getAllRoles() {

        List<Role> roles = new ArrayList<>();

        for (Role role: Role.values()) {
            roles.add(role);
        }

        return roles;
    }
    /**
     * User details.
     */

    public User getAuthenticationUser() {

        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    /**
     * Служебные методы класса.
     */

    private UserResponse newUserUserResponse(User user) {

        return new UserResponse(
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getId(),
                user.isActive(),
                user.getRoles());

    }

    private String[] ignoreProperties(){
        return new String[]{"id", "password"};
    }
}
