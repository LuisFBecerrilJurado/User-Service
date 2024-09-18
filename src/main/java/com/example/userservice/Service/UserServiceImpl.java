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
    public User getUserById(String idUser) {
        return userRepository.findById(idUser).orElseThrow(()-> new UserExceptions("User Not Found"));
    }

    @Override
    public User deleteUserById(String idUser) {
        userRepository.deleteById(idUser);
        throw new UserExceptions("User Deleted Successfully");
    }

    public boolean ageValidation(LocalDate birthDate){
        LocalDate today = LocalDate.now();
        Period period = Period.between(birthDate, today);
        return period.getYears() >= 18;
    }

    public boolean userValidation(User user){
        if(user.getMiddleName() == null){
            return (userRepository.existsByFirstNameAndLastNameAndBirthDate(user.getFirstName(), user.getLastName(), user.getBirthDate()));
        }
        return(userRepository.existsByFirstNameAndMiddleNameAndLastNameAndBirthDate(user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getBirthDate()));
    }

    @Override
    public User saveUser(User user) {
        boolean ageValid = ageValidation(user.getBirthDate());
        boolean userValid = userValidation(user);
        if(userValid) throw new UserExceptions("User already exists in system");
        if (!ageValid) throw new UserExceptions("Age not valid");
        String rndID = UUID.randomUUID().toString();
        user.setIdUser(rndID);
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> updateUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getIdUser());
        if(userOptional.isPresent()){
            boolean ageValid = ageValidation(user.getBirthDate());
            if (!ageValid) throw new UserExceptions("Age not valid");
            User userToUpdate = User.builder()
                    .idUser(userOptional.get().getIdUser())
                    .firstName(user.getFirstName())
                    .middleName(user.getMiddleName())
                    .lastName(user.getLastName())
                    .birthDate(user.getBirthDate())
                    .build();
            userRepository.save(userToUpdate);
        }
        throw new UserExceptions("User not found in system");
    }
}
