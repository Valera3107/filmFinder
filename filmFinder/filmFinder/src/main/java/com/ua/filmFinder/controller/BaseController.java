package com.ua.filmFinder.controller;

import com.ua.filmFinder.domain.Genre;
import com.ua.filmFinder.repo.GenreRepo;
import com.ua.filmFinder.repo.UserRepo;
import com.ua.filmFinder.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BaseController {

  @Autowired
  private GenreService genreService;

  @GetMapping("/")
  public String greeting() {
    return "greeting";
  }

  @GetMapping("/addGenre")
  public String getGenrePage(Model model) {
    model.addAttribute("genres", genreService.findAll());
    return "addGenre";
  }

  @PostMapping("/addGenre")
  public String addGenre(Genre genre) {
    genreService.save(genre);
   return "redirect:/addGenre";
  }
}
