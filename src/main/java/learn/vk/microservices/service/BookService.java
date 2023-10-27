package learn.vk.microservices.service;

import jakarta.annotation.PostConstruct;
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
    private final GenreService genreService;
    @Autowired
    private LibraryBranchService libraryBranchService;

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @PostConstruct
    public void init() {
        libraryBranchService.setBookService(this);
        borrowerService.setBookService(this);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book addBook(BookDto bookDto) {

        bookRepository.findById(bookDto.getBookId()).ifPresent(book -> {
            throw new RuntimeException("Book already exists");
        });
        Book book = new Book();
        book.setBookId(bookDto.getBookId());
        book.setTitle(bookDto.getTitle());
        book.setIsbn(bookDto.getIsbn());

        book.setAuthor(new ArrayList<>());
        book.setBorrower(new ArrayList<>());
        book.setGenre(new ArrayList<>());
        book.setLibraryBranch(new ArrayList<>());

        if (bookDto.getAuthors() == null) {
            bookDto.setAuthors(new ArrayList<>());
        }

        if (bookDto.getGenres() == null) {
            bookDto.setGenres(new ArrayList<>());
        }

        if (bookDto.getBranches() == null) {
            bookDto.setBranches(new ArrayList<>());
        }

        if (bookDto.getBorrowers() == null) {
            bookDto.setBorrowers(new ArrayList<>());
        }

        bookDto.getAuthors().forEach(authorId -> book.getAuthor().add(authorService.getAuthorById(authorId)));
        bookDto.getGenres().forEach(genreId -> book.getGenre().add(genreService.getGenreById(genreId)));
        bookDto.getBranches().forEach(libraryBranchId -> book.getLibraryBranch().add(libraryBranchService.getLibraryBranchById(libraryBranchId)));
        bookDto.getBorrowers().forEach(borrowerId -> book.getBorrower().add(borrowerService.getBorrowerById(borrowerId)));

        return bookRepository.save(book);
    }

    public Book updateBook(Long bookId, BookDto bookDto) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(bookDto.getTitle());
        existingBook.setIsbn(bookDto.getIsbn());

        if (existingBook.getAuthor() != null) {
            existingBook.getAuthor().clear();
        } else {
            existingBook.setAuthor(new ArrayList<>());
        }

        if (existingBook.getGenre() != null) {
            existingBook.getGenre().clear();
        } else {
            existingBook.setBorrower(new ArrayList<>());
        }

        if (existingBook.getLibraryBranch() != null) {
            existingBook.getLibraryBranch().clear();
        } else {
            existingBook.setLibraryBranch(new ArrayList<>());
        }

        if (existingBook.getBorrower() != null) {
            existingBook.getBorrower().clear();
        } else {
            existingBook.setBorrower(new ArrayList<>());
        }

        if (bookDto.getAuthors() == null) {
            bookDto.setAuthors(new ArrayList<>());
        }

        if (bookDto.getGenres() == null) {
            bookDto.setGenres(new ArrayList<>());
        }

        if (bookDto.getBranches() == null) {
            bookDto.setBranches(new ArrayList<>());
        }

        if (bookDto.getBorrowers() == null) {
            bookDto.setBorrowers(new ArrayList<>());
        }

        bookDto.getAuthors().forEach(authorId -> existingBook.getAuthor().add(authorService.getAuthorById(authorId)));

        bookDto.getGenres().forEach(genreId -> existingBook.getGenre().add(genreService.getGenreById(genreId)));

        bookDto.getBranches().forEach(libraryBranchId -> existingBook.getLibraryBranch().add(libraryBranchService.getLibraryBranchById(libraryBranchId)));

        bookDto.getBorrowers().forEach(borrowerId -> existingBook.getBorrower().add(borrowerService.getBorrowerById(borrowerId)));

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long bookId) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (Objects.nonNull(existingBook.getBorrower())) {
            existingBook.getBorrower().forEach(borrower -> borrower.getBook().remove(existingBook));
        }

        if (Objects.nonNull(existingBook.getLibraryBranch())) {
            existingBook.getLibraryBranch().forEach(libraryBranch -> libraryBranch.getBook().remove(existingBook));
        }

        if (Objects.nonNull(existingBook.getAuthor())) {
            existingBook.getAuthor().forEach(author -> author.getBook().remove(existingBook));
        }

        if (Objects.nonNull(existingBook.getGenre())) {
            existingBook.getGenre().forEach(genre -> genre.getBook().remove(existingBook));
        }


        bookRepository.deleteById(bookId);
    }

    public List<Book> getBooksByGenre(Long genreId) {
        return bookRepository.findByGenreGenreId(genreId);
    }

    public List<Book> getBooksByAuthor(Long authorId) {
        return bookRepository.findByAuthorAuthorId(authorId);
    }

    public List<Book> getBooksByLibraryBranch(Long libraryBranchId) {
        return bookRepository.findByLibraryBranchBranchId(libraryBranchId);
    }

    public List<Book> getBooksByBorrower(Long borrowerId) {
        return bookRepository.findByBorrowerCardNo(borrowerId);
    }
}
