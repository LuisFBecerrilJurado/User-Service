package com.example.userservice.Service;

import com.example.userservice.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long idUser);
    void saveUser(User user);
    Optional<User> updateUser(User user);
    void deleteUserById(Long idUser);
}
