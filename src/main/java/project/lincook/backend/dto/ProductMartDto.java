package project.lincook.backend.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class ProductMartDto {

    private Long productId;
    private int price;
    private boolean isInBasket;
    private double latitude;
    private double longitude;
    private MartDto mart;

    public ProductMartDto(Long productId, int price, MartDto mart, double latitude, double longitude, boolean isInBasket) {
        this.productId = productId;
        this.price = price;
        this.mart = mart;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isInBasket = isInBasket;
    }
}
