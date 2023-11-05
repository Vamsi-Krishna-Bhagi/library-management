package learn.vk.microservices.service;

import learn.vk.microservices.controller.exception.NotFoundException;
import learn.vk.microservices.dto.BookDto;
import learn.vk.microservices.entity.Book;
import learn.vk.microservices.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public Book addBook(BookDto bookDto) {

        bookRepository.findById(bookDto.getBookId()).ifPresent(book -> {
            throw new RuntimeException("Book already exists");
        });
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());

        book.setAuthor(new ArrayList<>());

        if (bookDto.getAuthors() == null) {
            bookDto.setAuthors(new ArrayList<>());
        }

        book.setStatus(Book.BookStatus.AVAILABLE);
        bookDto.getAuthors().forEach(authorId -> book.getAuthor().add(authorService.getAuthorById(authorId)));

        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, BookDto bookDto) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setPrice(bookDto.getPrice());
        if (existingBook.getAuthor() != null) {
            existingBook.getAuthor().clear();
        } else {
            existingBook.setAuthor(new ArrayList<>());
        }

        if (bookDto.getAuthors() == null) {
            bookDto.setAuthors(new ArrayList<>());
        }

        if (bookDto.getBorrowers() == null) {
            bookDto.setBorrowers(new ArrayList<>());
        }

        bookDto.getAuthors().forEach(authorId -> existingBook.getAuthor().add(authorService.getAuthorById(authorId)));

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long bookId) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));

        if (Objects.nonNull(existingBook.getAuthor())) {
            existingBook.getAuthor().forEach(author -> author.getBook().remove(existingBook));
        }

        bookRepository.deleteById(bookId);
    }

    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorAuthorId(authorId);
    }

    public List<Book> getBooksByBorrower(Long borrowerId) {
        return bookRepository.findByBorrowingRecordBorrowingUser(borrowerId);
    }
}
