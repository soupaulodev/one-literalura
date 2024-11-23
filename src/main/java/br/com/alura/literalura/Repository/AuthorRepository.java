package br.com.alura.literalura.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.literalura.Model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
    List<Author>findByDeathYearGreaterThan(Integer year);
}