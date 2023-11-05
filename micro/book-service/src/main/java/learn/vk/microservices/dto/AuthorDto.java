package learn.vk.microservices.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AuthorDto {

    private Long authorId;
    private String name;
    private String email;
    private String country;
}


