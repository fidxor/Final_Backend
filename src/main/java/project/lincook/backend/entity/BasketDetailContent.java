package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class BasketDetailContent {

    @Id @GeneratedValue
    @Column(name = "basket_detail_content_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Basket basket;

    @ManyToOne(fetch = LAZY)
    private Contents contents;

    public static BasketDetailContent createBasketDetailContent(Basket basket, Contents contents) {
        BasketDetailContent bdc = new BasketDetailContent();

        bdc.setBasket(basket);
        bdc.setContents(contents);

        return bdc;
    }
}
