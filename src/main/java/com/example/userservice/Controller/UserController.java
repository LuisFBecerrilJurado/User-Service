package com.example.userservice.Controller;

import com.example.userservice.Entity.User;
import com.example.userservice.Exceptions.UserExceptions;
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
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    private User buildUpdatedUser(User existingUser, User newUserData) {
        return User.builder()
                .idUser(existingUser.getIdUser())
                .firstName(newUserData.getFirstName())
                .middleName(newUserData.getMiddleName())
                .lastName(newUserData.getLastName())
                .birthDate(newUserData.getBirthDate())
                .position(newUserData.getPosition())
                .build();
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<String> updateUser(@PathVariable String idUser, @RequestBody User user) {
        Optional<User> optionalUser = userService.getUserById(idUser);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            User updatedUser = buildUpdatedUser(existingUser, user);
            userService.updateUser(updatedUser);
            return ResponseEntity.status(HttpStatus.OK).body(" User Updated Successfully");
        } else {
            throw new UserExceptions("User not found in system");
        }
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable String idUser){
        Optional<User> userOptional = userService.getUserById(idUser);
        if(userOptional.isPresent()) return ResponseEntity.ok(userOptional);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<String> deleteUser(@PathVariable String idUser){
        Optional<User> userOptional = userService.getUserById(idUser);
        if(userOptional.isPresent()){
            userService.deleteUserById(idUser);
            return ResponseEntity.ok("User deleted successfully");
        }
        throw new UserExceptions("User not found in system");
    }
}
