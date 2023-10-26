package learn.vk.microservices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Genre {
    @Id
    private Long genreId;

    private String name;

    @ManyToMany(mappedBy = "genre")
    private List<Book> book;
}
