package com.ua.filmFinder.controller;

import com.ua.filmFinder.domain.Role;
import com.ua.filmFinder.domain.User;
import com.ua.filmFinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/editUser")
  public String getEditPage(Model model, @AuthenticationPrincipal User user) {
    model.addAttribute("user", user);
    model.addAttribute("roles", Role.values());
    model.addAttribute("userRoles", user.getRoles());
    return "editUser";
  }

  @PostMapping("/editUser")
  public String updateUser(@AuthenticationPrincipal User user,
                           @RequestParam("role") List<String> roles,
                           @RequestParam String name,
                           @RequestParam String username,
                           @RequestParam String email,
                           @RequestParam("password") String password) {
    userService.update(user, name, username, roles, password, email);
    return "redirect:/main";
  }
}
