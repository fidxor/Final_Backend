package project.lincook.backend.dto;

import lombok.Data;
import project.lincook.backend.entity.Product;

@Data
public class BasketProductDto {

    private Long basketId;
    private Long productId;
    private String name;
    private String capacity;
    private int salePrice;
    private String imgUrl;

    public BasketProductDto(Long basketId, Product product) {
        this.basketId = basketId;
        this.productId = product.getId();
        this.name = product.getName();
        this.capacity = product.getCapacity();
        this.salePrice = product.getSale_price();
        this.imgUrl = product.getImg_url();
    }
}
