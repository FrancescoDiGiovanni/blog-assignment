package ch.qa.blog_assignment.controllers;

import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.exceptions.BlogException;
import ch.qa.blog_assignment.services.PostService;
import ch.qa.blog_assignment.utilities.Response;
import ch.qa.blog_assignment.utilities.ResponseUtility;
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
    ) throws BlogException {
        List<PostDTO> posts = postService.getAllPosts(title, category, tags);

        String okMessage = "Posts list retrieved successfully.";
        return ResponseUtility.buildSuccessResponseEntity(okMessage, posts, log);
    }

}
