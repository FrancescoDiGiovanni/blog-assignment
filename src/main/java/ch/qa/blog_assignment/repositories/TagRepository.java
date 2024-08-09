package ch.qa.blog_assignment.repositories;

import ch.qa.blog_assignment.DTOs.TagDTO;
import ch.qa.blog_assignment.entities.Post;
import ch.qa.blog_assignment.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository  extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
}
