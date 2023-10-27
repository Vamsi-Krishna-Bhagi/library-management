package learn.vk.microservices.controller;

import learn.vk.microservices.dto.BorrowerDto;
import learn.vk.microservices.entity.Borrower;
import learn.vk.microservices.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {
    private final BorrowerService borrowerService;

    @Autowired
    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping
    public List<Borrower> getBorrower() {
        return borrowerService.getAllBorrowers();
    }

    @GetMapping("/{borrowerId}")
    public Borrower getBorrowerById(@PathVariable Long borrowerId) {
        return borrowerService.getBorrowerById(borrowerId);
    }

    @PostMapping
    public Borrower addBorrower(@RequestBody BorrowerDto borrowerDto) {
        return borrowerService.addBorrower(borrowerDto);
    }

    @PutMapping("/{borrowerId}")
    public Borrower updateBorrower(@PathVariable Long borrowerId, @RequestBody BorrowerDto borrowerDto) {
        return borrowerService.updateBorrower(borrowerId, borrowerDto);
    }

    @DeleteMapping("/{borrowerId}")
    public void deleteBorrower(@PathVariable Long borrowerId) {
        borrowerService.deleteBorrower(borrowerId);
    }
}
