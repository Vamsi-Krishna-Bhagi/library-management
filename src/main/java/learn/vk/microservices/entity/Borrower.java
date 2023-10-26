package learn.vk.microservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Borrower {

    @Id
    private Long cardNo;

    private String name;
    private String address;
    private String phone;

//    @ManyToOne
//    @JoinColumn(name = "branch_id")
//    private LibraryBranch libraryBranch;

    //Relation with Book
    @ManyToMany
    @JoinTable(
            name = "book_borrower",
            joinColumns = @JoinColumn(name = "card_no"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> book;
}
