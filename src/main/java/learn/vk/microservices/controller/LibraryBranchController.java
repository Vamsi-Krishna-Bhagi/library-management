package learn.vk.microservices.controller;

import learn.vk.microservices.dto.BranchDto;
import learn.vk.microservices.entity.LibraryBranch;
import learn.vk.microservices.service.LibraryBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/library-branches")
public class LibraryBranchController {

    private final LibraryBranchService libraryBranchService;

    @Autowired
    public LibraryBranchController(LibraryBranchService libraryBranchService) {
        this.libraryBranchService = libraryBranchService;
    }

    @GetMapping
    public List<LibraryBranch> getAllLibraryBranches() {
        return libraryBranchService.getAllLibraryBranches();
    }

    @GetMapping("/{libraryBranchId}")
    public LibraryBranch getLibraryBranchById(@PathVariable Long libraryBranchId) {
        return libraryBranchService.getLibraryBranchById(libraryBranchId);
    }

    @PostMapping
    public LibraryBranch addLibraryBranch(@RequestBody BranchDto libraryBranch) {
        return libraryBranchService.addLibraryBranch(libraryBranch);
    }

    @PutMapping("/{libraryBranchId}")
    public LibraryBranch updateLibraryBranch(@PathVariable Long libraryBranchId, @RequestBody BranchDto libraryBranch) {
        return libraryBranchService.updateLibraryBranch(libraryBranchId, libraryBranch);
    }

    @DeleteMapping("/{libraryBranchId}")
    public void deleteLibraryBranch(@PathVariable Long libraryBranchId) {
        libraryBranchService.deleteLibraryBranch(libraryBranchId);
    }

}
