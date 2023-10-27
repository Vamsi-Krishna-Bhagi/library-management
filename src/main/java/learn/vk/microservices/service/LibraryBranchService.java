package learn.vk.microservices.service;

import learn.vk.microservices.dto.BranchDto;
import learn.vk.microservices.entity.LibraryBranch;
import learn.vk.microservices.repository.LibraryBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryBranchService {
    public static final String LIBRARY_BRANCH_NOT_FOUND = "LibraryBranch not found";

    private BookService bookService;
    private final LibraryBranchRepository libraryBranchRepository;

    @Autowired
    public LibraryBranchService( LibraryBranchRepository libraryBranchRepository) {
        this.libraryBranchRepository = libraryBranchRepository;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public List<LibraryBranch> getAllLibraryBranches() {
        return libraryBranchRepository.findAll();
    }

    public LibraryBranch getLibraryBranchById(Long libraryBranchId) {
        return libraryBranchRepository.findById(libraryBranchId).orElseThrow(() -> new RuntimeException(LIBRARY_BRANCH_NOT_FOUND));
    }

    public LibraryBranch addLibraryBranch(BranchDto libraryBranchDto) {
        libraryBranchRepository.findById(libraryBranchDto.getBranchId()).ifPresent(a -> {
            throw new RuntimeException("LibraryBranch already exists");
        });

        LibraryBranch libraryBranch = new LibraryBranch();
        libraryBranch.setBranchId(libraryBranchDto.getBranchId());
        libraryBranch.setName(libraryBranchDto.getName());
        libraryBranch.setBook(new ArrayList<>());

        if (libraryBranchDto.getBookIds() != null)
            libraryBranchDto.getBookIds().forEach(bookId -> libraryBranch.getBook().add(bookService.getBookById(bookId)));

        return libraryBranchRepository.save(libraryBranch);
    }

    public LibraryBranch updateLibraryBranch(Long libraryBranchId, BranchDto libraryBranch) {
        LibraryBranch existingLibraryBranch = libraryBranchRepository.findById(libraryBranchId).orElseThrow(() -> new RuntimeException(LIBRARY_BRANCH_NOT_FOUND));
        existingLibraryBranch.setName(libraryBranch.getName());

        if (libraryBranch.getBookIds() != null) {
            existingLibraryBranch.getBook().clear();
            libraryBranch.getBookIds().forEach(bookId -> existingLibraryBranch.getBook().add(bookService.getBookById(bookId)));
        }

        return libraryBranchRepository.save(existingLibraryBranch);
    }

    public void deleteLibraryBranch(Long libraryBranchId) {
        LibraryBranch libraryBranch = libraryBranchRepository.findById(libraryBranchId).orElseThrow(() -> new RuntimeException(LIBRARY_BRANCH_NOT_FOUND));

        libraryBranch.getBook().forEach(book -> book.getLibraryBranch().remove(libraryBranch));

        libraryBranchRepository.deleteById(libraryBranchId);
    }
}
