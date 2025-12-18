package com.alura.Literalura.principal;

import com.alura.Literalura.service.LivroService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    private final LivroService livroService;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(LivroService livroService) {
        this.livroService = livroService;
    }

    public void exibirMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("""
                    
                    ====== LITERALURA ======
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros por idioma
                    6 - Top 10 livros mais baixados
                    
                    0 - Sair
                    """);

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> buscarLivro();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLivrosPorIdioma();
                case 6 -> listarTop10();

                case 0 -> System.out.println("👋 Encerrando aplicação...");
                default -> System.out.println("❌ Opção inválida.");
            }
        }
    }

    private void buscarLivro() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        livroService.buscarESalvarLivroPorTitulo(titulo);
    }

    private void listarLivros() {
        livroService.listarLivros()
                .forEach(System.out::println);
    }

    private void listarTop10() {
        livroService.top10LivrosMaisBaixados()
                .forEach(System.out::println);
    }


    private void listarAutores() {
        livroService.listarAutores()
                .forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.print("Digite o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine();

        livroService.listarAutoresVivosEmAno(ano)
                .forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.print("Digite o idioma (ex: pt, en, fr): ");
        String idioma = scanner.nextLine();

        livroService.listarLivrosPorIdioma(idioma)
                .forEach(System.out::println);
    }
}
