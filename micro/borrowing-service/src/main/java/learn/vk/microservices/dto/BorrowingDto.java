package learn.vk.microservices.dto;

import lombok.Data;

@Data
public class BorrowingDto {
    private Long bookId;
    private Long borrowingUser;
}
