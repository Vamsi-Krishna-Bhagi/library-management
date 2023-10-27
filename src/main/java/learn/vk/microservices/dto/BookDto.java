package learn.vk.microservices.dto;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private Long bookId;
    private String title;
    private Long price;
    private String isbn;

    private List<Long> authors;
    private List<Long> genres;

    @Nullable
    private List<Long> branches;

    @Nullable
    private List<Long> borrowers;
}
