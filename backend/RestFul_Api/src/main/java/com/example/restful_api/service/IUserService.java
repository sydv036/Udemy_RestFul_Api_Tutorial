package com.example.restful_api.service;

import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.request.UsersRequest;
import com.example.restful_api.entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<Users> getAllUsers();

    Optional<Users> findById(Long id);

    Users saveUser(UsersRequest usersRequest);

    Users updateUser(Long id, Users users);

    void deleteUserById(Long id);

    Users findByEmail(String email);

    PaginationResponse getUserPagination(Specification<Users> usersSpecification, Pageable pageable);

}
