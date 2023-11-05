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

    private Long authorId;


    public enum BookStatus {
        AVAILABLE,
        BORROWED,
    }
}
