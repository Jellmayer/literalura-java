package com.jellmayer.literalura;

import com.jellmayer.literalura.model.Author;
import com.jellmayer.literalura.model.Book;
import com.jellmayer.literalura.model.BookData;
import com.jellmayer.literalura.model.GutendexResponse;
import com.jellmayer.literalura.service.ApiClient;
import com.jellmayer.literalura.service.BookService;
import com.jellmayer.literalura.service.ResponseMapper;

import java.io.IOException;
import java.time.Year;
import java.util.*;

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
            int option;
            try {
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
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                option = -1; // Direciona para o caso default (opção inválida) no switch
                scanner.nextLine();
            }

            switch (option){
                case 0:
                    System.out.println("Saindo do sistema...");
                    break menuLoop;
                case 1:
                    searchBook();
                    break;
                case 2:
                    listAllBooks();
                    break;
                case 3:
                    listBooksByLanguage();
                    break;
                case 4:
                    listAllAuthors();
                    break;
                case 5:
                    listAuthorsByYear();
                    break;
                default:
                    System.out.println("Opção inválida! Escolha novamente.");
            }
        }
    }

    public void searchBook() throws IOException, InterruptedException {
        System.out.println("Qual é o nome do livro? ");
        String titleForSearch = scanner.nextLine().toLowerCase().replace(" ", "%20");
        String url = BASE_URL + "?search=" + titleForSearch;

        System.out.println("Buscando por título...");

        String json = client.fetchData(url);
        GutendexResponse response = mapper.parseJson(json, GutendexResponse.class);

        if (response.results().isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            BookData bookData = response.results().getFirst();

            Book savedBook = service.saveBookFromData(bookData);

            System.out.println("'" + savedBook.getTitle() +"' salvo com sucesso!");
        }
    }

    private void displayBooks(List<Book> books) {
        books.forEach(book -> {
            System.out.println("----------------------------------------");
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor().getName());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Downloads: " + book.getDownloadCount());
            System.out.println("----------------------------------------");
        });
    }

    public void listAllBooks(){
        List<Book> books = service.findAllBooks();

        if (books.isEmpty()){
            System.out.println("Nenhum livro cadastrado.");
        } else {
            System.out.println("\n********** LIVROS CADASTRADOS **********");
            displayBooks(books);
        }
    }

    public void listBooksByLanguage(){
        System.out.println("""
                
                            ************ IDIOMAS DISPONÍVEIS PARA BUSCA ************
                            1. Inglês
                            2. Português
                            
                            Qual opção deseja?
                            """);
        int option = scanner.nextInt();
        scanner.nextLine();
        List<Book> books = new ArrayList<>();
        if (option == 1){
            books = service.findBooksByLanguage("en");
        } else if (option == 2) {
            books = service.findBooksByLanguage("pt");
        }

        if (books.isEmpty()){
            System.out.println("Nenhum livro encontrado.");
        } else {
            System.out.println("\n********** LIVROS ENCONTRADOS **********");
            displayBooks(books);
        }
    }

    public void displayAuthors(List<Author> authors){
        authors.forEach(author -> {
            System.out.println("----------------------------------------");
            System.out.println("Nome: " + author.getName());
            System.out.println("Ano de nascimento: " + author.getBirthYear());
            System.out.println("Ano de falecimento: " + (author.getDeathYear() != null ? author.getDeathYear() : "N/A"));
            System.out.println("----------------------------------------");
        });
    }

    public void listAllAuthors(){
        List<Author> authors = service.findAllAuthors();

        if (authors.isEmpty()){
            System.out.println("Nenhum autor cadastrado.");
        } else {
            System.out.println("\n********** AUTORES CADASTRADOS **********");
            displayAuthors(authors);
        }
    }

    public void listAuthorsByYear(){
        System.out.println("Qual ano deseja consultar? \n(Anos antes de Cristo devem ser negativos)");
        try {
            int yearInput = scanner.nextInt();
            scanner.nextLine();
            Year year = Year.of(yearInput);

            List<Author> authors = service.findAuthorsByYear(year);

            if (authors.isEmpty()) {
                System.out.println("Nenhum autor vivo encontrado no ano de " + yearInput);
            } else {
                System.out.println("\n********** AUTORES VIVOS EM " + yearInput + " **********");
                displayAuthors(authors);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Resposta inválida. Por favor, digite um número.");
            scanner.nextLine();
        }
    }
}
