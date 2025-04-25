package com.example.restful_api.controller;

import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.global.RestResponse;
import com.example.restful_api.dtos.request.UsersRequest;
import com.example.restful_api.entity.Users;
import com.example.restful_api.service.IUserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    public ResponseEntity<?> getUsers(@RequestParam("current") Optional<Integer> current, @RequestParam("pageSize") Optional<Integer> pageSize) {
//        PaginationResponse response = userService.getUserPagination(current, pageSize);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping
    public ResponseEntity<?> getUsers(@Filter Specification<Users> usersSpecification, Pageable pageable) {
        /*
        usersSpecification:{
        ?filter=..
        }

        pageable:{
            tham so paramerter: page, size
        }
         */
        PaginationResponse response = userService.getUserPagination(usersSpecification, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UsersRequest users) {
        Users result = userService.saveUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Users users, @PathVariable("id") Long id) {
        Users result = userService.updateUser(id, users);
        return ResponseEntity.status(HttpStatus.CREATED).body(new RestResponse<>(HttpStatus.CREATED.value(), "Update User", result, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(new RestResponse<>(HttpStatus.ACCEPTED.value(), "Delete User", null, null));
    }
}
