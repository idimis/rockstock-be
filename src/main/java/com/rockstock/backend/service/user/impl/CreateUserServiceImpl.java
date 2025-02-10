package com.rockstock.backend.service.user.impl;

import com.rockstock.backend.entity.user.Role;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.dto.CreateUserRequestDTO;
import com.rockstock.backend.infrastructure.user.repository.RoleRepository;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.user.CreateUserService;
import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CreateUserServiceImpl implements CreateUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createUser(CreateUserRequestDTO req) {
        Optional<User> checkUser = userRepository.findByEmailContainsIgnoreCase(req.getEmail());
        if (checkUser.isPresent()) {
            throw new DuplicateRequestException("Email already exists");
        }

        // Create user entity
        User newUser = req.toEntity();
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // Assign "Customer" role
        Optional<Role> customerRole = roleRepository.findByName("Customer");
        if (customerRole.isPresent()) {
            newUser.getRoles().add(customerRole.get());
        } else {
            throw new RuntimeException("Role 'Customer' not found");
        }

        // Save user entity first to persist the user
        newUser = userRepository.save(newUser);

        return newUser;
    }
}
