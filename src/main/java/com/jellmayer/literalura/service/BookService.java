package com.jellmayer.literalura.service;

import com.jellmayer.literalura.model.Author;
import com.jellmayer.literalura.model.AuthorData;
import com.jellmayer.literalura.model.Book;
import com.jellmayer.literalura.model.BookData;
import com.jellmayer.literalura.repository.AuthorRepository;
import com.jellmayer.literalura.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Book saveBookFromData(BookData bookData){
        AuthorData authorData = bookData.authors().getFirst();
        Optional<Author> optionalAuthor = authorRepository.findAuthorByName(authorData.name());

        Author author;
        // Impede duplicidade de autor no banco de dados
        if (optionalAuthor.isPresent())
            author = optionalAuthor.get();
        else {
            author = new Author(authorData);
            authorRepository.save(author);
        }

        Book book = new Book();
        book.setTitle(bookData.title());
        book.setLanguage(bookData.languages().getFirst());
        book.setDownloadCount(bookData.downloadCount());
        book.setAuthor(author);

        author.addBook(book);

        return bookRepository.save(book);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBooksByLanguage(String searchLanguage) {
        return bookRepository.findBooksByLanguageContaining(searchLanguage);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorsByYear(Year year) {
        return authorRepository.findAuthorsAliveInYear(year);
    }
}
