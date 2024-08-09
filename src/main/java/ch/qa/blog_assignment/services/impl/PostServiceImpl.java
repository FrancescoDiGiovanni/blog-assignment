package ch.qa.blog_assignment.services.impl;

import ch.qa.blog_assignment.DTOs.BasicPostDTO;
import ch.qa.blog_assignment.DTOs.CategoryDTO;
import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.DTOs.TagDTO;
import ch.qa.blog_assignment.entities.Author;
import ch.qa.blog_assignment.entities.Category;
import ch.qa.blog_assignment.entities.Post;
import ch.qa.blog_assignment.entities.Tag;
import ch.qa.blog_assignment.enums.BlogErrorsEnum;
import ch.qa.blog_assignment.exceptions.BlogException;
import ch.qa.blog_assignment.repositories.AuthorRepository;
import ch.qa.blog_assignment.repositories.CategoryRepository;
import ch.qa.blog_assignment.repositories.PostRepository;
import ch.qa.blog_assignment.repositories.TagRepository;
import ch.qa.blog_assignment.services.PostService;
import ch.qa.blog_assignment.services.specifications.PostSpecification;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
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
    public BasicPostDTO createNewPost(BasicPostDTO post) {
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setImage(post.getImage());

        Optional<Author> author = authorRepository.findByEmail(post.getAuthor().getEmail());
        if ( author.isPresent() )
            newPost.setAuthor(author.get());
        else
            newPost.setAuthor(modelMapper.map(post.getAuthor(), Author.class));

        postRepository.save(newPost);

        return modelMapper.map(post, BasicPostDTO.class);
    }

    @Override
    public PostDTO assignCategory(int id, CategoryDTO category) {
        Post postToUpdate = getPostById(id);

        Optional<Category> categoryFromDb = categoryRepository.findByName(category.getName());
        Category categoryToHandle = new Category();
        if ( !categoryFromDb.isPresent() ) {
            categoryToHandle.setName(category.getName());
            categoryRepository.save(categoryToHandle);
        } else
            categoryToHandle = categoryFromDb.get();

        postToUpdate.setCategory(categoryToHandle);
        postRepository.save(postToUpdate);

        return modelMapper.map(postToUpdate, PostDTO.class);
    }

    @Override
    public PostDTO assignTags(int id, List<TagDTO> tags) {
        Post postToUpdate = getPostById(id);

        tags.forEach(tag -> {
            Tag tagToHandle = new Tag();
            tagToHandle.setName(tag.getName());
            tagToHandle.setPost(postToUpdate);
            tagRepository.save(tagToHandle);
            postToUpdate.getTags().add(tagToHandle);
        });
        postRepository.save(postToUpdate);

        return modelMapper.map(postToUpdate, PostDTO.class);
    }

    @Override
    public PostDTO removeTags(int id, List<String> nameList) {
        Post post = getPostById(id);

        post.setTags(post.getTags().stream().filter(tag -> nameList.contains(tag.getName())).collect(Collectors.toList()));
        postRepository.save(post);

        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(int id, PostDTO postDTO) {
        Post post = getPostById(id);

        if ( postDTO.getTitle() != null )
            post.setTitle(postDTO.getTitle());

        if ( postDTO.getContent() != null )
            post.setContent(postDTO.getContent());

        if ( postDTO.getImage() != null )
            post.setImage(postDTO.getImage());

        if ( postDTO.getAuthor() != null ) {
            Optional<Author> author = authorRepository.findByEmail(postDTO.getAuthor().getEmail());
            if ( !author.isPresent() ) {
                Author newAuthor = modelMapper.map(postDTO.getAuthor(), Author.class);
                authorRepository.save(newAuthor);
                post.setAuthor(newAuthor);
            } else
                post.setAuthor(author.get());
        }

        postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public void deletePost(int id) {
        Post post = getPostById(id);
        postRepository.delete(post);
    }

    private Post getPostById(int id) {
        Optional<Post> post = postRepository.findById(id);
        if ( !post.isPresent() )
            throw new BlogException(BlogErrorsEnum.POST_NOT_FOUND.getCode(), BlogErrorsEnum.POST_NOT_FOUND.getDescription(), BlogErrorsEnum.POST_NOT_FOUND.getStatus());

        return post.get();
    }
}
