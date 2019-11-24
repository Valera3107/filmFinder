package com.ua.filmFinder.controller;

import com.ua.filmFinder.domain.Country;
import com.ua.filmFinder.domain.Film;
import com.ua.filmFinder.domain.User;
import com.ua.filmFinder.service.FilmService;
import com.ua.filmFinder.service.GenreService;
import com.ua.filmFinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class FilmController {

  @Autowired
  private FilmService filmService;

  @Autowired
  private UserService userService;

  @Autowired
  private GenreService genreService;

  @GetMapping("/addFilm")
  public String addFilmPage(Model model) {
    model.addAttribute("genres", genreService.findAll());
    model.addAttribute("countries", Country.values());
    return "addFilm";
  }

  @PostMapping("/addFilm")
  public String addFilm(Film film, @RequestParam MultipartFile file, @RequestParam("country") List<String> countries, @RequestParam("genre") List<String> ids) throws IOException {
    filmService.save(film, file, countries, ids);
    return "redirect:/filmList";
  }

  @GetMapping("/filmList")
  public String list(Model model) {
    model.addAttribute("list", filmService.findAll());
    return "filmList";
  }

  @GetMapping("/filmList/{id}")
  public String moreInfo(Model model, @PathVariable("id") Film film) {
    model.addAttribute("film", film);
    return "more";
  }

  @GetMapping("/editFilm/{id}")
  public String editPage(Model model, @PathVariable("id") Film film) {
    model.addAttribute("film", film);
    model.addAttribute("img", film.getImage());
    model.addAttribute("countries", Country.values());
    model.addAttribute("filmGenres", film.getGenres());
    model.addAttribute("genres", genreService.findAll());
    return "editFilm";
  }

  @PostMapping("/edit/{id}")
  public String edit(Film film, @RequestParam MultipartFile file, @RequestParam("genre") List<String> ids, @RequestParam("country") List<String> countries) throws IOException {
    filmService.save(film, file, countries, ids);
    return "redirect:/filmList";
  }

  @GetMapping("/favorite")
  public String getFavoritePage(Model model, @AuthenticationPrincipal User user) {
    model.addAttribute("list", user.getFavoriteFilms());
    return "favorites";
  }

  @GetMapping("/filmList/film/{id}")
  public String addToFavorite(@AuthenticationPrincipal User user, @PathVariable("id") Film film) {
    userService.addToFavorite(user, film);
    return "redirect:/filmList";
  }

  @GetMapping("/favorite/delete/{id}")
  public String deleteFromFavorite(@PathVariable("id") Film film, @AuthenticationPrincipal User user){
    userService.deleteFromFavorite(user, film);
    return "redirect:/favorite";
  }
}
