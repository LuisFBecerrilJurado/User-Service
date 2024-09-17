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

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long idUser) {
        return userRepository.findById(idUser).orElseThrow(()-> new UserExceptions("User Not Found with id: "+ idUser));
    }

    @Override
    public void deleteUserById(Long idUser) {
        userRepository.deleteById(idUser);
    }

    public boolean ageValidation(LocalDate birthDate){
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears() >= 18;
    }

    public boolean userValidation(User user){
        if(user.getMiddleName().isEmpty()){
            return (userRepository.existsByFirstNameAndLastNameAndBirthDate(user.getFirstName(), user.getLastName(), user.getBirthDate()));
        }
        return(userRepository.existsByFirstNameAndMiddleNameAndLastNameAndBirthDate(user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getBirthDate()));
    }

    @Override
    public void saveUser(User user) {
        boolean ageValid = ageValidation(user.getBirthDate());
        boolean userValid = userValidation(user);
        if(!userValid) throw new UserExceptions("User already exists in system");
        if (!ageValid) throw new UserExceptions("Age not valid");
        userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getIdUser());
        if(userOptional.isPresent()){
            boolean ageValid = ageValidation(user.getBirthDate());
            boolean userValid = userValidation(user);
            if(!userValid) throw new UserExceptions("User already exists in system");
            if (!ageValid) throw new UserExceptions("Age not valid");
            userRepository.save(user);
        }
        throw new UserExceptions("User not found in system");
    }
}
