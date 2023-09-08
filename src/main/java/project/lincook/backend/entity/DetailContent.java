package project.lincook.backend.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class DetailContent {

    @Id
    @GeneratedValue
    @Column(name = "detail_content_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    private String name;
    @OneToMany(mappedBy = "detailContent")
    private List<DetailContentProduct> detailContentProducts = new ArrayList<>();
}
