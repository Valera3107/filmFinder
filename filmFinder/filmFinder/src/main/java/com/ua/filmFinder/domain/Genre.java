package com.ua.filmFinder.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "name"})
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NonNull
  private String name;

  @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
  private List<Film> filmList;
}
