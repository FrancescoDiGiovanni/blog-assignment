package ch.qa.blog_assignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="Post")
@Table(name = "posts")
public class Post {

    @Id
    @Column(name="id", nullable = false)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    @OneToMany(mappedBy = "post")
    private List<Tag> tags;

}
