package learn.vk.microservices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BorrowingDto {
    private Long id;
    private Long bookId;
    private LocalDate borrowingDate;
    private LocalDate returnDate;
    private Long borrowingUser;
}
