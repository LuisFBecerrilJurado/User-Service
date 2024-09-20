package com.example.userservice.Repository;

import com.example.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByFirstNameAndMiddleNameAndLastNameAndBirthDate(String firstName, String middleName, String lastName, LocalDate birthDate);
}
