package ch.qa.blog_assignment.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name="Category")
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
    private Post post;
}
