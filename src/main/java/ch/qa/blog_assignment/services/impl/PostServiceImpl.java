package ch.qa.blog_assignment.services.impl;

import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.entities.Author;
import ch.qa.blog_assignment.entities.Category;
import ch.qa.blog_assignment.entities.Post;
import ch.qa.blog_assignment.entities.Tag;
import ch.qa.blog_assignment.repositories.AuthorRepository;
import ch.qa.blog_assignment.repositories.CategoryRepository;
import ch.qa.blog_assignment.repositories.PostRepository;
import ch.qa.blog_assignment.repositories.TagRepository;
import ch.qa.blog_assignment.services.PostService;
import ch.qa.blog_assignment.services.specifications.PostSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

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

    @Override
    public PostDTO createNewPost(Post post) {
        for ( Tag tag : post.getTags() )
            tag.setPost(post);
        postRepository.save(post);

        return modelMapper.map(post, PostDTO.class);
    }
}
