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
public class Basket {

    @Id @GeneratedValue
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "basket")
    private List<BasketProduct> basketProducts = new ArrayList<>();

    @OneToMany(mappedBy = "basket")
    private List<BasketMart> basketMarts = new ArrayList<>();

    @OneToMany(mappedBy = "basket")
    private List<BasketDetailContent> basketDetailContents = new ArrayList<>();
}
