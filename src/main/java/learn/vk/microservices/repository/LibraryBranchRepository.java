package learn.vk.microservices.repository;

import learn.vk.microservices.entity.LibraryBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, Long> {

}
