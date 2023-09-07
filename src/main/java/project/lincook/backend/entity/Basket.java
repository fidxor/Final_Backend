package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Basket {

    @Id @GeneratedValue
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "mart_id")
    private Mart mart;

    @ManyToOne
    @JoinColumn(name = "contents_id")
    private Contents contents;
}
