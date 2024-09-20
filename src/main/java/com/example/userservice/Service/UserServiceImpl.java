package com.example.userservice.Service;

import com.example.userservice.Entity.User;
import com.example.userservice.Exceptions.UserExceptions;
import com.example.userservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String idUser) {
        return Optional.ofNullable(userRepository.findById(idUser).orElseThrow(() -> new UserExceptions("User Not Found")));
    }

    @Override
    public void deleteUserById(String idUser) {
        userRepository.deleteById(idUser);
    }

    public void ageValidation(LocalDate birthDate){
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        if(period.getYears() < 18) throw new UserExceptions("Age not valid");
    }

    public void userValidation(User user){
        boolean userExists = userRepository
                .existsByFirstNameAndMiddleNameAndLastNameAndBirthDate(
                        user.getFirstName(),
                        user.getMiddleName(),
                        user.getLastName(),
                        user.getBirthDate());
        if(userExists) throw new UserExceptions("User already exists in system");
    }

    private void validateUserFields(User user) {
        if (user.getFirstName() == null) throw new UserExceptions("Verify the First Name field");
        if (user.getLastName() == null) throw new UserExceptions("Verify the Last Name field");
        if (user.getBirthDate() == null) throw new UserExceptions("Verify the Birth Date field");
        if (user.getPosition() == null) throw new UserExceptions("Verify the Position field");
    }

    private void generateAndSetUserId(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setIdUser(randomUserId);
    }

    @Override
    public void saveUser(User user) {
        validateUserFields(user);
        ageValidation(user.getBirthDate());
        userValidation(user);
        generateAndSetUserId(user);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        validateUserFields(user);
        ageValidation(user.getBirthDate());
        userValidation(user);
        userRepository.save(user);
    }
}
