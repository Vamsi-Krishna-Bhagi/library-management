package learn.vk.microservices.dto;

import lombok.Data;

import java.util.List;

@Data
public class BorrowerDto {
    private Long cardNo;
    private String name;
    private String address;
    private String phone;

    private List<Long> books;
}
