package learn.vk.microservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Book {
    @Id
    private Long bookId;
    private String title;
    private Long price;
    private String isbn;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> author;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genre;

    @ManyToMany(mappedBy = "book")
    @JsonIgnore
    private List<LibraryBranch> libraryBranch;

    //    Relation with Borrower
    @ManyToMany(mappedBy = "book")
    @JsonIgnore
    private List<Borrower> borrower;

}
