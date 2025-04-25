package com.example.restful_api.controller;

import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.global.RestResponse;
import com.example.restful_api.dtos.request.UsersRequest;
import com.example.restful_api.dtos.response.UserResponse;
import com.example.restful_api.entity.Users;
import com.example.restful_api.service.IUserService;
import com.example.restful_api.utils.annotation.ApiMessage;
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
@RequestMapping("/api/v1/users")
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
    @ApiMessage("Fetch All User")
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

    @GetMapping("/{id}")
    @ApiMessage("Fetch User by ID")
    public ResponseEntity<UserResponse> fetchUserById(@PathVariable("id") Long id) {
        UserResponse userResponse = userService.fetchUserById(id);
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping
    @ApiMessage("Create an new user")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UsersRequest users) {
        UserResponse result = userService.saveUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @ApiMessage("Updated an user")
    public ResponseEntity<UserResponse> updateUser(@RequestBody Users users, @PathVariable("id") Long id) {
        UserResponse result = userService.updateUser(id, users);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete User By Id")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok(new RestResponse<>(HttpStatus.ACCEPTED.value(), "Delete User", null, null));
    }
}
