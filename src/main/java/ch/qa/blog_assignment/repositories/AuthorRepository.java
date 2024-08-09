package ch.qa.blog_assignment.repositories;

import ch.qa.blog_assignment.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);
}
