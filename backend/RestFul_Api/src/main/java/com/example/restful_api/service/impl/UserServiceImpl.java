package com.example.restful_api.service.impl;

import com.example.restful_api.dtos.global.MetaPagination;
import com.example.restful_api.dtos.global.PaginationResponse;
import com.example.restful_api.dtos.request.UsersRequest;
import com.example.restful_api.entity.Users;
import com.example.restful_api.repository.IUserRepository;
import com.example.restful_api.service.IUserService;
import com.example.restful_api.utils.ValueConstant;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private ValueConstant valueConstant;

    private final IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Id not found");
        }
        return userRepository.findById(id);
    }

    @Override
    public Users saveUser(UsersRequest usersRequest) {
        if (userRepository.existsByEmail(usersRequest.getEmail())) {
            throw new IllegalArgumentException("Email exist in DB");
        }
        String hashPassword = passwordEncoder.encode(usersRequest.getPassword());
        usersRequest.setPassword(hashPassword);
        Users users = mapper.map(usersRequest, Users.class);
        return userRepository.save(users);
    }

    @Override
    public Users updateUser(Long id, Users users) {
        return findById(id).map(item -> {
            item.setEmail(users.getEmail());
            item.setName(users.getName());
            return userRepository.saveAndFlush(item);
        }).orElseThrow(() -> new IllegalArgumentException(("User not found")));
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new IllegalArgumentException("Id not found");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Users findByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new UsernameNotFoundException("Email not exist in DB");
        }
        Users users = userRepository.findByEmail(email);
        return users;
    }

//    @Override
//    public PaginationResponse getUserPagination(Optional<Integer> current, Optional<Integer> pageSize) {
//        Pageable pageable = PageRequest.of(current.orElse(valueConstant.DEFAULT_CURRENT_PAGE) - 1, pageSize.orElse(valueConstant.DEFAULT_PAGE_SIZE));
//        Page page = userRepository.findAll(pageable);
//        MetaPagination metaPagination = new MetaPagination(page.getTotalElements(), page.getNumber() + 1, page.getSize(), page.getTotalPages());
//        PaginationResponse response = new PaginationResponse(metaPagination, page.getContent());
//        return response;
//    }

    @Override
    public PaginationResponse getUserPagination(Specification<Users> usersSpecification, Pageable pageable) {
        Page<Users> page = userRepository.findAll(usersSpecification, pageable);
        MetaPagination metaPagination = new MetaPagination(page.getTotalElements(), pageable.getPageNumber() + 1, pageable.getPageSize(), page.getTotalPages());
        PaginationResponse response = new PaginationResponse(metaPagination, page.getContent());
        return response;
    }

}
