package com.ua.filmFinder.service;

import com.ua.filmFinder.domain.Country;
import com.ua.filmFinder.domain.Film;
import com.ua.filmFinder.domain.Genre;
import com.ua.filmFinder.repo.FilmRepo;
import com.ua.filmFinder.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

  @Autowired
  private FilmRepo filmRepo;

  @Autowired
  private GenreRepo genreRepo;

  public void save(Film film, MultipartFile file, List<String> countries, List<String> ids) throws IOException {
    film.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
    List<Long> id = ids.stream().map(Long::parseLong).collect(Collectors.toList());
    List<Genre> genres = id.stream().map(e -> genreRepo.findById(e).get()).peek(e -> e.getFilmList().add(film)).collect(Collectors.toList());

    List<Country> countryList = countries.stream().map(Country::valueOf).collect(Collectors.toList());
    film.setCountries(new HashSet<>(countryList));

    film.setGenres(new HashSet<>(genres));

    filmRepo.save(film);
  }

  public List<Film> findAll() {
    return filmRepo.findAll();
  }

}
