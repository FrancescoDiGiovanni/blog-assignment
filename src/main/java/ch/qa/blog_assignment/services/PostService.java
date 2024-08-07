package ch.qa.blog_assignment.services;

import ch.qa.blog_assignment.entities.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts(String title, String category, String tags);
}
