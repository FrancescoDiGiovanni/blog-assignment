package ch.qa.blog_assignment.services;

import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.entities.Post;

import java.util.List;

public interface PostService {
    List<PostDTO> getAllPosts(String title, String category, String tags);
}
