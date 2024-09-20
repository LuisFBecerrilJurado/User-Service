package com.example.userservice.Service;

import com.example.userservice.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User>  getUserById(String idUser);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(String idUser);
}
