package ch.qa.blog_assignment.services.impl;

import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.entities.Post;
import ch.qa.blog_assignment.repositories.PostRepository;
import ch.qa.blog_assignment.services.PostService;
import ch.qa.blog_assignment.services.specifications.PostSpecification;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<PostDTO> getAllPosts(String title, String category, String tags) {
        Specification<Post> spec = Specification.where(null);
        if ( title != null )
            spec = spec.and(PostSpecification.filterByTitle(title));

        if (category != null)
            spec = spec.and(PostSpecification.filterByCategory(category));

        if (tags != null)
            spec = spec.and(PostSpecification.filterByTags(tags));

        List<Post> postList = postRepository.findAll(spec);
        List<PostDTO> posts = Arrays.asList(modelMapper.map(postList, PostDTO[].class));

        return posts;
    }
}
