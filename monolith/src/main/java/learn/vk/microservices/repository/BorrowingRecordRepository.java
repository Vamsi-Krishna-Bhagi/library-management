package learn.vk.microservices.repository;

import learn.vk.microservices.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    List<BorrowingRecord> findByBorrowingUser(Long userId);

    @Query("select max(b.id) from BorrowingRecord b")
    Optional<Long> maxBorrowingId();
}
