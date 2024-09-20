package com.example.userservice.Exceptions;
import com.example.userservice.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptions {
    @ExceptionHandler(UserExceptions.class)
    public ResponseEntity<ApiResponse> handlerNotFound(UserExceptions ue){
        String message = ue.getMessage();
        ApiResponse response = new ApiResponse().builder()
                .message(message)
                .success(true)
                .status(HttpStatus.CONFLICT)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}