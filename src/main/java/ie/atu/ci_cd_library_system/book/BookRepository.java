package ie.atu.ci_cd_library_system.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Books, Long> {
    Optional<Books> findByTitleIgnoreCase(String title);
}
