package com.ua.filmFinder.service;

import com.ua.filmFinder.domain.Genre;
import com.ua.filmFinder.repo.GenreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenreService {

  @Autowired
  private GenreRepo genreRepo;

  public void save(Genre genre) {
    genreRepo.save(genre);
  }

  public List<Genre> findAll() {
    return genreRepo.findAll();
  }
}
