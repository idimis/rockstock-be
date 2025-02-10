package com.rockstock.backend.service.user.auth.impl;

import com.rockstock.backend.common.exceptions.DataNotFoundException;
import com.rockstock.backend.entity.user.User;
import com.rockstock.backend.infrastructure.user.auth.dto.UserAuth;
import com.rockstock.backend.infrastructure.user.repository.UserRepository;
import com.rockstock.backend.service.user.auth.GetUserAuthDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetUserAuthDetailsServiceImpl implements GetUserAuthDetailsService {
    private final UserRepository usersRepository;

    public GetUserAuthDetailsServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User existingUser = usersRepository.findByEmailContainsIgnoreCase(username).orElseThrow(() -> new DataNotFoundException("User not found with email: " + username));

        UserAuth userAuth = new UserAuth();
        userAuth.setUser(existingUser);
        return userAuth;
    }
}
