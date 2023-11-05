package learn.vk.microservices.service;

import learn.vk.microservices.controller.exception.GenericException;
import learn.vk.microservices.controller.exception.NotFoundException;
import learn.vk.microservices.dto.BookDto;
import learn.vk.microservices.dto.BorrowingDto;
import learn.vk.microservices.entity.Book;
import learn.vk.microservices.repository.BookRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BorrowingService borrowingService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService, BorrowingService borrowingService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.borrowingService = borrowingService;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(book -> {
            BookDto bookDto = new BookDto();
            BeanUtils.copyProperties(book, bookDto);
            authorService.getAuthorById(book.getAuthorId()).ifPresent(bookDto::setAuthor);
            return bookDto;
        }).toList();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
    }

    public Book addBook(BookDto bookDto) {

        bookRepository.findById(bookDto.getBookId()).ifPresent(book -> {
            throw new GenericException("Book already exists");
        });
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());
        book.setPrice(bookDto.getPrice());

        book.setStatus(Book.BookStatus.AVAILABLE);
        book.setAuthorId(authorService.getOrCreateAuthorId(bookDto.getAuthor()));

        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, BookDto bookDto) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setIsbn(bookDto.getIsbn());
        existingBook.setPrice(bookDto.getPrice());


        existingBook.setAuthorId(authorService.updateOrCreateAuthorId(bookDto.getAuthor()));
//        bookDto.getAuthor().forEach(authorId -> existingBook.getAuthor().add(authorService.getAuthorById(authorId)));
//        existingBook.setAuthorId();
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long bookId) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));

//        if (Objects.nonNull(existingBook.getAuthor())) {
//            existingBook.getAuthor().forEach(author -> author.getBook().remove(existingBook));
//        }

        bookRepository.deleteById(bookId);
    }

    public List<Book> getBooksByAuthor(Long authorId) {
//        return bookRepository.findByAuthorAuthorId(authorId);
        return null;
    }

    public List<Book> getBooksByBorrower(Long borrowerId) {
//        return bookRepository.findByBorrowingRecordBorrowingUser(borrowerId);
        return null;
    }

    public Book borrowBook(Long bookId, Long borrowerId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));

        if (book.getStatus().equals(Book.BookStatus.BORROWED)) {
            throw new GenericException("Book is already borrowed");
        }

        borrowingService.borrowBook(bookId, borrowerId);

        book.setStatus(Book.BookStatus.BORROWED);

        return bookRepository.save(book);
    }

    public Book returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));

        if (book.getStatus().equals(Book.BookStatus.AVAILABLE)) {
            throw new GenericException("Book is already returned");
        }

        BorrowingDto borrowingDto = borrowingService.returnBook(bookId);

        book.setStatus(Book.BookStatus.AVAILABLE);

        return bookRepository.save(book);
    }
}
