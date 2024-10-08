package ch.qa.blog_assignment.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String title;
    private String content;
    private String image;
    private AuthorDTO author;
    private List<TagDTO> tags;
    private CategoryDTO category;
}
