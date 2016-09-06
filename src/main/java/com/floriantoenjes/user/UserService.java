package com.floriantoenjes.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
}
