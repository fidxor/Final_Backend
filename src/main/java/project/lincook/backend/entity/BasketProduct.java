package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class BasketProduct {

    @Id @GeneratedValue
    @Column(name = "basket_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Basket basket;

    @ManyToOne(fetch = LAZY)
    private Product product;

}
