package ch.qa.blog_assignment.controllers;

import ch.qa.blog_assignment.DTOs.BasicPostDTO;
import ch.qa.blog_assignment.DTOs.CategoryDTO;
import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.DTOs.TagDTO;
import ch.qa.blog_assignment.enums.BlogErrorsEnum;
import ch.qa.blog_assignment.exceptions.BlogException;
import ch.qa.blog_assignment.services.PostService;
import ch.qa.blog_assignment.utilities.Response;
import ch.qa.blog_assignment.utilities.ResponseUtility;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<Response<List<PostDTO>>> getPosts(
        @RequestParam(name = "title", required = false) String title,
        @RequestParam(name = "category", required = false) String category,
        @RequestParam(name = "tags", required = false) String tags
    ) {
        List<PostDTO> posts = postService.getAllPosts(title, category, tags);

        String okMessage = "Posts list retrieved successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, posts, log);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Response<BasicPostDTO>> createPost(
            @Valid @RequestBody BasicPostDTO post
    ) {
        BasicPostDTO postDTO = postService.createNewPost(post);

        String okMessage = "Posts created successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, postDTO, log);
    }

    @PostMapping(value = "/{id}/assignCategory", produces = "application/json")
    public ResponseEntity<Response<PostDTO>> assignCategory(
            @PathVariable("id") int id,
            @RequestBody CategoryDTO category
    ) throws BlogException {
        PostDTO postDTO = postService.assignCategory(id, category);

        String okMessage = "Category assigned successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, postDTO, log);
    }

    @PostMapping(value="/{id}/assignTags", produces = "application/json")
    public ResponseEntity<Response<PostDTO>> assignTags(
            @PathVariable("id") int id,
            @RequestBody List<TagDTO> tags
    ) throws BlogException {
        PostDTO postDTO = postService.assignTags(id, tags);

        String okMessage = "Tags assigned successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, postDTO, log);
    }

    @DeleteMapping(value="/{id}/removeTags", produces = "application/json")
    public ResponseEntity<Response<PostDTO>> removeTags(
            @PathVariable("id") int id,
            @RequestBody List<String> nameList
    ) throws BlogException {
        PostDTO postDTO = postService.removeTags(id, nameList);

        String okMessage = "Tags removed successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, postDTO, log);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Response<PostDTO>> updatePost(
            @PathVariable("id") int id,
            @RequestBody PostDTO postDTO
    ) throws BlogException {
        PostDTO updatedPostDTO = postService.updatePost(id, postDTO);

        String okMessage = "Post updated successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, updatedPostDTO, log);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Response<PostDTO>> deletePost(
            @PathVariable("id") int id,
            @RequestHeader("X-User") String userRole
    ) throws BlogException {
        if ( !userRole.equals("admin") )
            throw new BlogException(BlogErrorsEnum.ROLE_NOT_AUTHORIZED.getCode(), BlogErrorsEnum.ROLE_NOT_AUTHORIZED.getDescription(), BlogErrorsEnum.ROLE_NOT_AUTHORIZED.getStatus());

        postService.deletePost(id);
        String okMessage = "Post deleted successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, null, log);
    }
}
