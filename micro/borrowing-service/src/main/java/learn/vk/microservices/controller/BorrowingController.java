package learn.vk.microservices.controller;

import learn.vk.microservices.dto.BorrowingDto;
import learn.vk.microservices.entity.BorrowingRecord;
import learn.vk.microservices.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;

    @Autowired
    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @GetMapping("/user/{userId}")
    public List<BorrowingRecord> getBorrowingsByUser(@PathVariable Long userId) {
        return borrowingService.getBorrowingsByUser(userId);
    }

    @PostMapping
    public BorrowingRecord borrowBook(@RequestBody BorrowingDto borrowingDto) {
        return borrowingService.borrowBook(borrowingDto.getBookId(), borrowingDto.getBorrowingUser());
    }

    @PatchMapping   ("/return/{borrowingId}")
    public BorrowingRecord returnBook(@PathVariable Long borrowingId) {
        return borrowingService.returnBook(borrowingId);
    }


}
