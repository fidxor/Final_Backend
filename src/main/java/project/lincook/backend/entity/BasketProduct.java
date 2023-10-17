package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class BasketProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_product_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Basket basket;

    @ManyToOne(fetch = LAZY)
    private Product product;

    public static BasketProduct createBasketProduct(Basket basket, Product product) {
        BasketProduct basketProduct = new BasketProduct();

        basketProduct.setBasket(basket);
        basketProduct.setProduct(product);

        return basketProduct;
    }

}
