package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class BasketMart {

    @Id @GeneratedValue
    @Column(name = "basket_mart_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Basket basket;

    @ManyToOne(fetch = LAZY)
    private Mart mart;
}
