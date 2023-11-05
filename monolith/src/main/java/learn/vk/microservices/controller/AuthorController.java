package learn.vk.microservices.controller;

import jakarta.validation.constraints.NotNull;
import learn.vk.microservices.entity.Author;
import learn.vk.microservices.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/authors")
@Validated
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{authorId}")
    public Author getAuthorById(@NotNull @PathVariable Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @PostMapping
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }

    @PutMapping("/{authorId}")
    public Author updateAuthor(@RequestBody Author author, @PathVariable Long authorId) {
        return authorService.updateAuthor(authorId, author);
    }

    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable Long authorId) {
        authorService.deleteAuthor(authorId);
    }

}
