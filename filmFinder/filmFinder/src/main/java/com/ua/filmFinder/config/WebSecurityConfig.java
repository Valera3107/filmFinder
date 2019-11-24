package com.ua.filmFinder.config;

import com.ua.filmFinder.domain.Role;
import com.ua.filmFinder.domain.User;
import com.ua.filmFinder.repo.UserRepo;
import com.ua.filmFinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
      .antMatchers("/", "/loginForm", "/registration", "/static/**")
      .permitAll().anyRequest().authenticated()
      .and()
      .formLogin().loginPage("/loginForm").permitAll()
      .and()
      .rememberMe()
      .and()
      .logout().permitAll()
      .and()
      .csrf().disable();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(8);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }

  @Bean
  public PrincipalExtractor principalExtractor(UserRepo userRepo) {
    return map -> {

      String email = (String) map.get("email");

      User user = userRepo.findByEmail(email).orElseGet(() -> {
        User newUser = new User();

        String name = (String) map.get("given_name");
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setRoles(Collections.singleton(Role.USER));
        newUser.setUsername(name+"User");
        newUser.setPassword(passwordEncoder.encode("root"));

        return newUser;
      });

      return userRepo.save(user);
    };
  }
}
