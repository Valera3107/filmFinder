package com.ua.filmFinder.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.Set;

@Entity
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@ToString(of = {"id", "name"})
public class Film {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "film_name")
  @NonNull
  private String name;

  @Column(name = "film_url")
  @NonNull
  private String url;

  @Column(name = "film_rating")
  @NonNull
  private Double rating;

  @Column(name = "film_views")
  private Long views;

  @NonNull
  @ElementCollection(targetClass = Country.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "film_country", joinColumns = @JoinColumn(name = "film_id"))
  @Enumerated(EnumType.STRING)
  private Set<Country> countries;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "film_genre", joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genres;

  @ManyToMany(mappedBy = "favoriteFilms", fetch = FetchType.EAGER)
  private List<User> users;

  @Lob
  private String image;

  @Lob
  @NonNull
  private String description;
}
