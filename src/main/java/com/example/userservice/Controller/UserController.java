package com.example.userservice.Controller;

import com.example.userservice.Entity.User;
import com.example.userservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/newUser")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User userToSave = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userToSave);
    }

    @PutMapping("/newUser")
    public ResponseEntity<Optional<User>> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<User> getUserById(@PathVariable Long idUser){
        User user = userService.getUserById(idUser);
        return ResponseEntity.ok(user);
    }
}
