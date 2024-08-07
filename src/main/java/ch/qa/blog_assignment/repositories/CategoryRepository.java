package ch.qa.blog_assignment.repositories;

import ch.qa.blog_assignment.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Integer> {
}
