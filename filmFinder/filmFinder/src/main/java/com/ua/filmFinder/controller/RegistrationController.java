package com.ua.filmFinder.controller;

import com.ua.filmFinder.domain.User;
import com.ua.filmFinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
  @Autowired
  private UserService userService;

  @GetMapping
  public String getRegistration(){
    return "registration";
  }

  @PostMapping
  public String addUser(User user){
    userService.save(user);
    return "redirect:/loginForm";
  }
}
