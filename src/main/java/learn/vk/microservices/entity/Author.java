package learn.vk.microservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;
    private String name;
    private String email;
    private String country;

    @ManyToMany(mappedBy = "author")
    private List<Book> book;


}
