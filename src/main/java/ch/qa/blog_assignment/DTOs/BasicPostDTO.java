package ch.qa.blog_assignment.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicPostDTO {
    private int id;
    private String title;
    private String content;
    private String image;
    private AuthorDTO author;
}
