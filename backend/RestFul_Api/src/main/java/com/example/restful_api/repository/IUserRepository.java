package com.example.restful_api.repository;

import com.example.restful_api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(path = "users") //mặc định sẽ tạo 4 method get,post,put,delete cho curd đơn giản mà không cần service hay controller
public interface IUserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    boolean existsByEmail(String email);

    Users findByEmail(String email);

    Users findByEmailAndRefreshToken(String email, String refreshToken);
}
