package com.alura.Literalura.service;

import com.alura.Literalura.dto.AutorDTO;
import com.alura.Literalura.dto.LivroDTO;
import com.alura.Literalura.dto.RespostaGutendexDTO;
import com.alura.Literalura.model.Autor;
import com.alura.Literalura.model.Livro;
import com.alura.Literalura.repository.AutorRepository;
import com.alura.Literalura.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final ConsumoApi consumoApi;
    private final ObjectMapper mapper;
    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;

    public LivroService(AutorRepository autorRepository,
                        LivroRepository livroRepository) {
        this.consumoApi = new ConsumoApi();
        this.mapper = new ObjectMapper();
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void buscarESalvarLivroPorTitulo(String titulo) {

        try {
            String json = consumoApi.buscarLivroPorTitulo(titulo);

            RespostaGutendexDTO resposta =
                    mapper.readValue(json, RespostaGutendexDTO.class);

            if (resposta.results().isEmpty()) {
                System.out.println("❌ Nenhum livro encontrado.");
                return;
            }

            LivroDTO livroDTO = resposta.results().get(0);
            AutorDTO autorDTO = livroDTO.authors().get(0);

            Autor autor = autorRepository
                    .findByNomeIgnoreCase(autorDTO.name())
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor(
                                autorDTO.name(),
                                autorDTO.anoNascimento(),
                                autorDTO.anoFalecimento()
                        );
                        return autorRepository.save(novoAutor);
                    });
            Optional<Livro> livroExistente =
                    livroRepository.findByTituloIgnoreCaseAndAutor(livroDTO.title(), autor);

            if (livroExistente.isPresent()) {
                System.out.println("⚠️ Livro já cadastrado:");
                System.out.println(livroExistente.get());
                return;
            }

            Livro livro = new Livro(
                    livroDTO.title(),
                    livroDTO.languages().get(0),
                    livroDTO.numeroDownloads(),
                    autor
            );

            livroRepository.save(livro);

            System.out.println("✅ Livro salvo com sucesso!");
            System.out.println(livro);

        } catch (Exception e) {
            System.out.println("⚠️ Erro ao processar o livro.");
            e.printStackTrace();
        }
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdiomaIgnoreCase(idioma);
    }

    public List<Livro> top10LivrosMaisBaixados() {
        return livroRepository.findTop10ByOrderByNumeroDownloadsDesc();
    }


    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosEmAno(int ano) {
        return autorRepository.findAll()
                .stream()
                .filter(a ->
                        a.getAnoNascimento() != null &&
                                a.getAnoNascimento() <= ano &&
                                (a.getAnoFalecimento() == null || a.getAnoFalecimento() > ano)
                )
                .toList();
    }
}
