package ch.qa.blog_assignment.services.specifications;

import ch.qa.blog_assignment.entities.Post;
import ch.qa.blog_assignment.entities.Tag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    public static Specification<Post> filterByTitle(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%"+title+"%");
    }

    public static Specification<Post> filterByCategory(String category) {
        return (root, query, cb) -> cb.like(root.get("category").get("name"), "%"+category+"%");
    }

    public static Specification<Post> filterByTags(String tags) {
        return (root, query, cb) -> {
            Join<Post, Tag> join = root.join("tags", JoinType.LEFT);
            List<Predicate> predicates = new ArrayList<>();
            for (String tag : tags.split(","))
                predicates.add(cb.like(join.get("name"), "%"+tag+"%"));

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
