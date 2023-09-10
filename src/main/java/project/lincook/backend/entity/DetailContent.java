package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class DetailContent {

    @Id
    @GeneratedValue
    @Column(name = "detail_content_id")
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    private String name;
    @OneToMany(mappedBy = "detailContent", cascade = CascadeType.ALL)
    private List<DetailContentProduct> detailContentProducts = new ArrayList<>();

    public void addProduct(DetailContentProduct detailContentProduct) {
        detailContentProducts.add(detailContentProduct);
        detailContentProduct.setDetailContent(this);
    }

    public static DetailContent createDetailContent(Contents contents, String name, DetailContentProduct... detailContentProducts) {
        DetailContent detailContent = new DetailContent();

        detailContent.setContents(contents);
        detailContent.setName(name);

        for (DetailContentProduct dcp : detailContentProducts) {
            detailContent.addProduct(dcp);
        }

        return detailContent;
    }
}
