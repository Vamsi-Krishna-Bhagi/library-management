package learn.vk.microservices.service;

import learn.vk.microservices.entity.Author;
import learn.vk.microservices.repository.AuthorRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class AuthorService {
    private final AuthorRepository authorRepository;
//    CRUD operations for Author entity
    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long authorId) {
        log.info("Author Id: " + authorId);
        return authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public Author addAuthor(Author author) {
        authorRepository.findById(author.getAuthorId()).ifPresent(a -> {
            throw new RuntimeException("Author already exists");
        });
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long authorId, Author author) {
        Author existingAuthor = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        existingAuthor.setName(author.getName());
        existingAuthor.setEmail(author.getEmail());
        existingAuthor.setCountry(author.getCountry());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long authorId) {
        authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found"));
        authorRepository.deleteById(authorId);
    }


}
