package com.jellmayer.literalura;

import com.jellmayer.literalura.model.Book;
import com.jellmayer.literalura.model.BookData;
import com.jellmayer.literalura.model.GutendexResponse;
import com.jellmayer.literalura.service.ApiClient;
import com.jellmayer.literalura.service.BookService;
import com.jellmayer.literalura.service.ResponseMapper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private static final ApiClient client = new ApiClient();
    private static final ResponseMapper mapper = new ResponseMapper();
    private final Scanner scanner = new Scanner(System.in);
    private static final String BASE_URL = "https://gutendex.com/books/";

    private final BookService service;

    public Console(BookService bookService) {
        this.service = bookService;
    }

    public void runConsole() throws IOException, InterruptedException {
        menuLoop: while (true){
            System.out.println("""
                    
                    
                    **************** MENU LITERALURA *****************
                        1. Buscar livro
                        2. Listar livros salvos
                        3. Listar livros por idioma
                        4. Listar autores salvos
                        5. Listar autores vivos em um determinado ano
                        0. SAIR
                    
                    """);

            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();

            switch (option){
                case 0:
                    System.out.println("Saindo do sistema...");
                    break menuLoop;
                case 1:
                    searchBook();
                case 2:
                    listAllBooks();
//                case 3:
//                    listBooksByLanguage();
//                case 4:
//                    listAllAuthors();
//                case 5:
//                    listAuthorsByYear();
                default:
                    System.out.println("Opção inválida! Escolha novamente.");
            }
        }
    }

    public void searchBook() throws IOException, InterruptedException {
        System.out.println("Qual é o nome do livro? ");
        String titleForSearch = scanner.nextLine().toLowerCase().replace(" ", "%20");
        String url = BASE_URL + "?search=" + titleForSearch;

        String json = client.fetchData(url);
        GutendexResponse response = mapper.parseJson(json, GutendexResponse.class);

        if (response.results().isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            BookData bookData = response.results().getFirst();

            Book savedBook = service.saveBookFromData(bookData);

            System.out.println("Livro salvo com sucesso!");
            System.out.println(savedBook);
        }
    }

    public void listAllBooks(){
        System.out.println("\n----- LIVROS CADASTRADOS -----");
        List<Book> books = service.findAllBooks();

        if (books.isEmpty()){
            System.out.println("Nenhum livro cadastrado.");
        } else {
            books.forEach(book -> {
                System.out.println("-------------------------------------");
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor().getName());
                System.out.println("Idioma: " + book.getLanguage());
                System.out.println("Downloads: " + book.getDownloadCount());
                System.out.println("-------------------------------------");
            });
        }
    }
}
