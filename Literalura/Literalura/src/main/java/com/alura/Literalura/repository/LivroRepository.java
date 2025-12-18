package com.alura.Literalura.repository;

import com.alura.Literalura.model.Autor;
import com.alura.Literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdiomaIgnoreCase(String idioma);
    List<Livro> findTop10ByOrderByNumeroDownloadsDesc();

    Optional<Livro> findByTituloIgnoreCaseAndAutor(String titulo, Autor autor);

}
