package ch.qa.blog_assignment.services;

import ch.qa.blog_assignment.DTOs.BasicPostDTO;
import ch.qa.blog_assignment.DTOs.CategoryDTO;
import ch.qa.blog_assignment.DTOs.PostDTO;
import ch.qa.blog_assignment.DTOs.TagDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts(String title, String category, String tags);
    BasicPostDTO createNewPost(BasicPostDTO post);
    PostDTO assignCategory(int id, CategoryDTO category);
    PostDTO assignTags(int id, List<TagDTO> tags);
    PostDTO removeTags(int id, List<String> nameList);
    PostDTO updatePost(int id, PostDTO postDTO);
    void deletePost(int id);
}
