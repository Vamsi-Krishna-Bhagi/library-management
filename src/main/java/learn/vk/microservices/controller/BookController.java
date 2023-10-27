package learn.vk.microservices.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import learn.vk.microservices.dto.BookDto;
import learn.vk.microservices.entity.Book;
import learn.vk.microservices.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    public Book getBookById(@NotNull @PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public Book addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @PutMapping("/{bookId}")
    public Book updateBook(@RequestBody BookDto bookDto, @PathVariable Long bookId) {
        return bookService.updateBook(bookId, bookDto);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
    }

    @GetMapping("/genre/{genreId}")
    public List<Book> getBooksByGenre(@NotNull @PathVariable Long genreId) {
        return bookService.getBooksByGenre(genreId);
    }

    @GetMapping("/author/{authorId}")
    public List<Book> getBooksByAuthor(@NotNull @PathVariable Long authorId) {
        return bookService.getBooksByAuthor(authorId);
    }

    @GetMapping("/library-branch/{libraryBranchId}")
    public List<Book> getBooksByLibraryBranch(@NotNull @PathVariable Long libraryBranchId) {
        return bookService.getBooksByLibraryBranch(libraryBranchId);
    }

    @GetMapping("/borrower/{borrowerId}")
    public List<Book> getBooksByBorrower(@NotNull @PathVariable Long borrowerId) {
        return bookService.getBooksByBorrower(borrowerId);
    }
}
