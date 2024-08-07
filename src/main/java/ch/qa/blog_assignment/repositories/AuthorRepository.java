package ch.qa.blog_assignment.repositories;

import ch.qa.blog_assignment.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
