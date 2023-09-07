package project.lincook.backend.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class DetailContent {

    @Id
    @GeneratedValue
    @Column(name = "detail_content_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;

    private String name;
    @OneToMany(mappedBy = "detailContent")
    private List<Product> products;
}
