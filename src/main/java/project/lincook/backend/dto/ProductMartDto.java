package project.lincook.backend.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ProductMartDto {

    private Long productId;
    private int price;
    private boolean isInBasket;
    private MartDto mart;

    public ProductMartDto(Long productId, int price, MartDto mart, boolean isInBasket) {
        this.productId = productId;
        this.price = price;
        this.mart = mart;
        this.isInBasket = isInBasket;
    }
}
