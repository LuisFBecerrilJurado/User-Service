package com.example.userservice.Repository;

import com.example.userservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByFirstNameAndAndMiddleNameAndLastNameAndBirthDate(String firstName, String middleName, String lastName, String birthDate);

}
