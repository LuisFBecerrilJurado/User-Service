package com.example.userservice.Service;

import com.example.userservice.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String idUser);
    User saveUser(User user);
    Optional<User> updateUser(User user);
    User deleteUserById(String idUser);
}
