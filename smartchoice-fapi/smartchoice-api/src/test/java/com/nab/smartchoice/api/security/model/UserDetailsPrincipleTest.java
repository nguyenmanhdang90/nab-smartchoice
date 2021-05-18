package com.nab.smartchoice.api.security.model;

import org.junit.Assert;
import org.junit.Test;

public class UserDetailsPrincipleTest {

  @Test
  public void UserDetailsPrinciple() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Assert.assertTrue(userDetailsPrinciple.isAccountNonExpired());
    Assert.assertTrue(userDetailsPrinciple.isAccountNonLocked());
    Assert.assertTrue(userDetailsPrinciple.isCredentialsNonExpired());
    Assert.assertTrue(userDetailsPrinciple.isEnabled());
  }

}