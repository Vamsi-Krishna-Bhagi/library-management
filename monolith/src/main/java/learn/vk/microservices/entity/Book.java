package learn.vk.microservices.entity;

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

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> author;

    @OneToOne(mappedBy = "book")
    private BorrowingRecord borrowingRecord;


    public enum BookStatus {
        AVAILABLE,
        BORROWED,
    }
}
