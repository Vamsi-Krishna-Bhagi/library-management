package learn.vk.microservices.service;

import jakarta.transaction.Transactional;
import learn.vk.microservices.controller.exception.GenericException;
import learn.vk.microservices.controller.exception.NotFoundException;
import learn.vk.microservices.entity.Book;
import learn.vk.microservices.entity.BorrowingRecord;
import learn.vk.microservices.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;

    @Autowired
    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository, BookService bookService) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
    }

    @Transactional
    public BorrowingRecord returnBook(Long borrowingId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(borrowingId)
                .orElseThrow(() -> new NotFoundException("Borrowing record not found"));


        if (borrowingRecord.getBook().getStatus().equals(Book.BookStatus.AVAILABLE)) {
            throw new GenericException("Book is already returned");
        }
        borrowingRecord.setReturnDate(java.time.LocalDate.now());
        borrowingRecord.getBook().setStatus(Book.BookStatus.AVAILABLE);
        borrowingRecordRepository.save(borrowingRecord);
        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long borrowerId) {
        Book book = bookService.getBookById(bookId);

        if (book.getStatus().equals(Book.BookStatus.BORROWED)) {
            throw new GenericException("Book is already borrowed");
        }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setBorrowingDate(java.time.LocalDate.now());
        borrowingRecord.setBorrowingUser(borrowerId);
        book.setStatus(Book.BookStatus.BORROWED);

        return borrowingRecordRepository.save(borrowingRecord);

    }

    public List<BorrowingRecord> getBorrowingsByUser(Long userId) {
        return borrowingRecordRepository.findByBorrowingUser(userId);
    }
}
