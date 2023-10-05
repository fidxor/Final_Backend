package project.lincook.backend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private int product_code;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mart_id")
    private Mart mart;

    private String name;
    private String capacity;

    private String add_date;

    private int origin_price;
    private int sale_price;

    private String img_url;
    private String detail_url;
}
