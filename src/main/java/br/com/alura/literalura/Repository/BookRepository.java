package br.com.alura.literalura.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.literalura.Model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Book l WHERE l.language = :language")
    List<Book> findBooksByLanguage(@Param("language") String language);

}