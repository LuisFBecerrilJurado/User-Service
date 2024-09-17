package com.example.userservice.Repository;

import com.example.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByFirstNameAndMiddleNameAndLastNameAndBirthDate(String firstName, String middleName, String lastName, LocalDate birthDate);
    Boolean existsByFirstNameAndLastNameAndBirthDate(String firstName, String lastName, LocalDate birthDate);
}
