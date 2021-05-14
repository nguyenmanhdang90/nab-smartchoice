package com.nab.smartchoice.api.security.services;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nab.smartchoice.db.entities.User;
import com.nab.smartchoice.db.repo.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsSecurityServiceImplTest {

  @InjectMocks
  private UserDetailsSecurityServiceImpl userDetailsSecurityService;

  @Mock
  private UserRepository userRepository;

  @Test(expected = UsernameNotFoundException.class)
  public void loadUserByUsername_should_throwException_when_wrongUsername() {
    String username = "username";
    userDetailsSecurityService.loadUserByUsername(username);
    Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
  }

  @Test
  public void loadUserByUsername_should_returnUserDetails_when_loginSuccess() {
    String username = "username";
    User user = User.builder().username(username).build();
    Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
    UserDetails response = userDetailsSecurityService.loadUserByUsername(username);
    Assert.assertEquals(username, response.getUsername());
  }

}