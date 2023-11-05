package learn.vk.microservices.service;

import jakarta.transaction.Transactional;
import learn.vk.microservices.controller.exception.GenericException;
import learn.vk.microservices.controller.exception.NotFoundException;
import learn.vk.microservices.entity.BorrowingRecord;
import learn.vk.microservices.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowingService {
    private final BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    @Transactional
    public BorrowingRecord returnBook(Long borrowingId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findById(borrowingId)
                .orElseThrow(() -> new NotFoundException("Borrowing record not found"));

        borrowingRecord.setReturnDate(java.time.LocalDate.now());

        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long borrowerId) {

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBookId(bookId);
        borrowingRecord.setBorrowingDate(java.time.LocalDate.now());
        borrowingRecord.setBorrowingUser(borrowerId);

        return borrowingRecordRepository.save(borrowingRecord);

    }

    public List<BorrowingRecord> getBorrowingsByUser(Long userId) {
        return borrowingRecordRepository.findByBorrowingUser(userId);
    }
}
