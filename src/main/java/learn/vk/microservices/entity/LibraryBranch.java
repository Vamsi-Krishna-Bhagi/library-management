package learn.vk.microservices.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class LibraryBranch {
    @Id
    private Long branchId;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "branch_book",
        joinColumns = @jakarta.persistence.JoinColumn(name = "branch_id"),
        inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "book_id"))
    private List<Book> book;
}
