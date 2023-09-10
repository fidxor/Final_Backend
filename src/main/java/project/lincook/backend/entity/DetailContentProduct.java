package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class DetailContentProduct {

    @Id @GeneratedValue
    @Column(name = "detail_content_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private DetailContent detailContent;

    @ManyToOne(fetch = LAZY)
    private Product product;

    public static DetailContentProduct createDetailContentProduct(DetailContent detailContent, Product product) {

        DetailContentProduct detailContentProduct = new DetailContentProduct();

        detailContentProduct.setDetailContent(detailContent);
        detailContentProduct.setProduct(product);

        return detailContentProduct;
    }
}
