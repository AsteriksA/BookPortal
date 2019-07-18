package com.gold.security2.service;

import com.gold.model.UserEntity;
import com.gold.repository.UserRepository;
import com.gold.security2.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jwtUserDetailsService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));

        return JwtUserFactory.create(user);
    }
}