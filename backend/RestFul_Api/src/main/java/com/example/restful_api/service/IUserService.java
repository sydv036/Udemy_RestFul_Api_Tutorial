package com.example.restful_api.service;

import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.request.UsersRequest;
import com.example.restful_api.dtos.response.UserResponse;
import com.example.restful_api.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<Users> getAllUsers();

    Optional<Users> findById(Long id);

    UserResponse saveUser(UsersRequest usersRequest);

    UserResponse updateUser(Long id, Users users);

    void deleteUserById(Long id);

    Users findByEmail(String email);

    UserResponse fetchUserById(Long id);

    PaginationResponse getUserPagination(Specification<Users> usersSpecification, Pageable pageable);

}
