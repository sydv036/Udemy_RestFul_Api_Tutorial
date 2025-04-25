package com.example.restful_api.exception;

import com.example.restful_api.dtos.global.RestResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException(Exception ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setMessage("Exception System");
        res.setErrorCode(ex.getMessage());
        return ResponseEntity.internalServerError().body(res);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            EntityNotFoundException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<?> handleIllegalArgumentException(Exception ex) {
        var res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage("Bad Request");
        res.setErrorCode(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        RestResponse<Object> res = new RestResponse<>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setErrorCode("Invalid");
        List<String> error = ex.getBindingResult().getFieldErrors().stream().map(err ->
                err.getField() + ": " + err.getDefaultMessage()
        ).collect(Collectors.toList());
        res.setMessage(error.size() > 1 ? error : error.get(0));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(value = {
            BadCredentialsException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<?> handleBadCredentialsException(Exception ex) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setErrorCode(ex.getMessage());
        response.setMessage("Username or Password inValid!");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> hanledResourceNotFound(Exception ex) {
        RestResponse<Object> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setErrorCode(ex.getMessage());
        response.setMessage("404 Not Found. URL Not Found");
        return ResponseEntity.badRequest().body(response);
    }

}
