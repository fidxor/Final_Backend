package project.lincook.backend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "mart_id")
    private Mart mart;
    private String capacity;

    private String origin_price;
    private String sale_price;

    private String img_url;
    private String detail_url;

    private LocalDateTime add_time;
}
