package learn.vk.microservices.repository;

import learn.vk.microservices.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenreGenreId(Long genreId);

    List<Book> findByAuthorAuthorId(Long authorId);

    List<Book> findByLibraryBranchBranchId(Long libraryBranchId);

    List<Book> findByBorrowerCardNo(Long borrowerId);
}
