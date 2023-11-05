package learn.vk.microservices.service;

import learn.vk.microservices.dto.BorrowingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BorrowingService {

    @Value("${url.borrowing-service}")
    private String borrowingServiceUrl;

    private RestTemplate restTemplate;

    @Autowired
    public BorrowingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public void borrowBook(Long bookId, Long borrowerId) {
        BorrowingDto borrowingDto = new BorrowingDto();
        borrowingDto.setBookId(bookId);
        borrowingDto.setBorrowingUser(borrowerId);
        restTemplate.postForObject(borrowingServiceUrl, borrowingDto, BorrowingDto.class);
    }

    public BorrowingDto returnBook(Long bookId) {
       return restTemplate.patchForObject(borrowingServiceUrl + "/return/" + bookId, null, BorrowingDto.class);
    }
}
