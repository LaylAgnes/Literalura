package com.alura.Literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String idioma;

    private Integer numeroDownloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}

    public Livro(String titulo, String idioma, Integer numeroDownloads, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDownloads = numeroDownloads;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "\n📖 Livro:" +
                "\n   Título: " + titulo +
                "\n   Autor: " + autor.getNome() +
                "\n   Idioma: " + idioma +
                "\n   Downloads: " + numeroDownloads;
    }
}
