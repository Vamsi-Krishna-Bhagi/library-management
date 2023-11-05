package learn.vk.microservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Author {

    @Id
    private Long authorId;
    private String name;
    private String email;
    private String country;

    @ManyToMany(mappedBy = "author")
    @JsonIgnore
    private List<Book> book;


}
