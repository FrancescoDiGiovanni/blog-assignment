package ch.qa.blog_assignment.repositories;

import ch.qa.blog_assignment.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository  extends JpaRepository<Tag, Integer> {
}
