package uz.pdp.school_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Where(clause = "active = true")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private boolean active = true;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> authors;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Attachment file;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Attachment photo;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Class classi;
}
