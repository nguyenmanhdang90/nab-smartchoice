package com.nab.smartchoice.api.security.services;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;
import com.nab.smartchoice.db.entities.User;
import com.nab.smartchoice.db.repo.UserRepository;

@Service
public class UserDetailsSecurityServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = userRepository.findByUsername(username)
        .orElseThrow(error(username));
    return UserDetailsPrinciple.builder()
        .email(user.getEmail())
        .password(user.getPassword())
        .username(user.getUsername())
        .build();
  }

  private Supplier<UsernameNotFoundException> error(String username) {
    return () -> new UsernameNotFoundException("User Not Found with username: " + username);
  }
}
