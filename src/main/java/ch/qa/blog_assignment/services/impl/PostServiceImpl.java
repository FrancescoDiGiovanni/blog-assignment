package ch.qa.blog_assignment.services.impl;

import ch.qa.blog_assignment.entities.Post;
import ch.qa.blog_assignment.repositories.PostRepository;
import ch.qa.blog_assignment.services.PostService;
import ch.qa.blog_assignment.services.specifications.PostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts(String title, String category, String tags) {
        Specification<Post> spec = Specification.where(null);
        if ( title != null )
            spec = spec.and(PostSpecification.filterByTitle(title));

        if (category != null)
            spec = spec.and(PostSpecification.filterByCategory(category));

        if (tags != null)
            spec = spec.and(PostSpecification.filterByTags(tags));

        List<Post> posts = postRepository.findAll(spec);
        return posts;
    }
}
