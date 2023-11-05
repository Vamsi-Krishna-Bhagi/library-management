package learn.vk.microservices.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long bookId;
    private String title;
    private Long price;
    private String isbn;

    private AuthorDto author;

}
