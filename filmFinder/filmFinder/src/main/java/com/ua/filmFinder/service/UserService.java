package com.ua.filmFinder.service;

import com.ua.filmFinder.domain.Film;
import com.ua.filmFinder.domain.Role;
import com.ua.filmFinder.domain.User;
import com.ua.filmFinder.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return userRepo.findByUsername(s);
  }

  public void save(User user) {
    user.setRoles(Collections.singleton(Role.USER));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepo.save(user);
  }

  public void update(User user, String name, String username, List<String> roles, String password, String email) {
    user.getRoles().clear();
    user.setPassword(passwordEncoder.encode(password));
    List<Role> userRoles = roles.stream().map(Role::valueOf).collect(Collectors.toList());
    user.setRoles(new HashSet<>(userRoles));
    user.setEmail(email);
    user.setName(name);
    user.setUsername(username);
    userRepo.save(user);
  }

  public void addToFavorite(User user, Film film) {
    if (user.getFavoriteFilms().isEmpty())
      user.setFavoriteFilms(Collections.singleton(film));
    else
      user.getFavoriteFilms().add(film);
  }

  public void deleteFromFavorite(User user, Film film) {
    if (!user.getFavoriteFilms().isEmpty()) {
      user.getFavoriteFilms().remove(film);
      userRepo.save(user);
    }
  }
}
