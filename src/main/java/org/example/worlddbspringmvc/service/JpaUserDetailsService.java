package org.example.worlddbspringmvc.service;

import org.example.worlddbspringmvc.model.respositories.UserRepository;
import org.example.worlddbspringmvc.model.entities.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username)
                             .map(SecurityUser::new)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
