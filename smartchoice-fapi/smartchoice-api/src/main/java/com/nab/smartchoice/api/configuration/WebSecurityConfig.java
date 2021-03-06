package com.nab.smartchoice.api.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nab.smartchoice.api.security.AuthEntryPointJwt;
import com.nab.smartchoice.api.security.AuthTokenFilter;
import com.nab.smartchoice.api.security.services.UserDetailsSecurityServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] METHOD_ALLOWED = {"HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"};

  private static final String[] NO_AUTH_PATH = {"/auth/signin"};

  private static final String API = "/api/**";

  @Autowired
  private UserDetailsSecurityServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean(name = "restTemplateForCrawler")
  public RestTemplate restTemplateForCrawler() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().add("Authorization", "Bearer TOKEN");
      return execution.execute(new HttpRequestWrapper(request), body);
    });
    return restTemplate;
  }

  @Bean(name = "restTemplateForLogs")
  public RestTemplate restTemplateForLogs() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().add("Authorization", "Bearer TOKEN_LOGS");
      return execution.execute(new HttpRequestWrapper(request), body);
    });
    return restTemplate;
  }

  @Bean
  public TaskExecutor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(8);
    executor.setMaxPoolSize(50);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.setThreadNamePrefix("Async-");
    executor.initialize();
    return new DelegatingSecurityContextAsyncTaskExecutor(executor);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()// for development
        .cors()
        .configurationSource(getCorsConfigurationSource())
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers(NO_AUTH_PATH).permitAll()
        .antMatchers(API).authenticated()
        .anyRequest().permitAll();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  private CorsConfigurationSource getCorsConfigurationSource() {
    return request -> {
      final CorsConfiguration corsConfiguration = new CorsConfiguration();
      corsConfiguration.setAllowedMethods(Arrays.asList(METHOD_ALLOWED));
      return corsConfiguration.applyPermitDefaultValues();
    };
  }
}
