package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Basket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = LAZY, mappedBy = "basket", cascade = CascadeType.ALL)
    private Set<BasketProduct> basketProducts = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "basket", cascade = CascadeType.ALL)
    private Set<BasketMart> basketMarts = new HashSet<>();

    @OneToMany(fetch = LAZY, mappedBy = "basket", cascade = CascadeType.ALL)
    private Set<BasketDetailContent> basketDetailContents = new HashSet<>();

    private LocalDateTime regDate;

    public void addBasketProduct(BasketProduct basketProduct) {
        basketProducts.add(basketProduct);
        basketProduct.setBasket(this);
    }

    public void addBasketMart(BasketMart basketMart) {
        basketMarts.add(basketMart);
        basketMart.setBasket(this);
    }

    public void addBasketDetailContent(BasketDetailContent basketDetailContent) {
        basketDetailContents.add(basketDetailContent);
        basketDetailContent.setBasket(this);
    }

    /**
     * Set을 List로 변환해서 contents를 리턴한다.
     * @return
     */
    public Contents getContentsOfList() {
        return List.copyOf(this.getBasketDetailContents()).get(0).getContents();
    }

    public Product getProductOfList() {
        return List.copyOf(this.getBasketProducts()).get(0).getProduct();
    }

    public Mart getMartOfList() {
        return List.copyOf(this.getBasketMarts()).get(0).getMart();
    }
}
