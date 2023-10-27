package learn.vk.microservices.service;

import learn.vk.microservices.dto.BorrowerDto;
import learn.vk.microservices.entity.Borrower;
import learn.vk.microservices.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    private BookService bookService;

    @Autowired
    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public List<Borrower> getAllBorrowers() {
        return borrowerRepository.findAll();
    }

    public Borrower getBorrowerById(Long borrowerId) {
        return borrowerRepository.findById(borrowerId).orElseThrow(() -> new RuntimeException("Borrower not found"));
    }

    public Borrower addBorrower(BorrowerDto borrowerDto) {
        borrowerRepository.findById(borrowerDto.getCardNo()).ifPresent(a -> {
            throw new RuntimeException("Borrower already exists");
        });

        Borrower borrower = new Borrower();
        borrower.setCardNo(borrowerDto.getCardNo());
        borrower.setName(borrowerDto.getName());
        borrower.setAddress(borrowerDto.getAddress());
        borrower.setPhone(borrowerDto.getPhone());
        borrower.setBook(new ArrayList<>());

        if (borrowerDto.getBooks() != null && !borrowerDto.getBooks().isEmpty()) {
            borrowerDto.getBooks().forEach(book -> borrower.getBook().add(bookService.getBookById(book)));
        }

        return borrowerRepository.save(borrower);
    }

    public Borrower updateBorrower(Long borrowerId, BorrowerDto borrowerDto) {
        Borrower existingBorrower = borrowerRepository.findById(borrowerId).orElseThrow(() -> new RuntimeException("Borrower not found"));
        existingBorrower.setName(borrowerDto.getName());
        existingBorrower.setAddress(borrowerDto.getAddress());
        existingBorrower.setPhone(borrowerDto.getPhone());

        if (borrowerDto.getBooks() != null && !borrowerDto.getBooks().isEmpty()) {
            existingBorrower.getBook().clear();
            borrowerDto.getBooks().forEach(book -> existingBorrower.getBook().add(bookService.getBookById(book)));
        }

        return borrowerRepository.save(existingBorrower);
    }

    public void deleteBorrower(Long borrowerId) {
        borrowerRepository.findById(borrowerId).orElseThrow(() -> new RuntimeException("Borrower not found"));
        borrowerRepository.deleteById(borrowerId);
    }
}
