package project.lincook.backend.dto;

import lombok.Data;
import project.lincook.backend.entity.Product;

@Data
public class ProductDto {

    private Long id;
    private int code;
    private String name;
    private String capacity;
    private int original_price;
    private int sale_price;
    private String img_url;
    private String detail_url;

    public ProductDto(Long id, int code, String name, String capacity, int original_price, int sale_price, String img_url, String detail_url) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        this.original_price = original_price;
        this.sale_price = sale_price;
        this.img_url = img_url;
        this.detail_url = detail_url;
    }

    public ProductDto(Product product) {
        this.id = product.getId();
        this.code = product.getProduct_code();
        this.name = product.getName();
        this.capacity = product.getCapacity();
        this.original_price = product.getOriginal_price();
        this.sale_price = product.getSale_price();
        this.img_url = product.getImg_url();
        this.detail_url = product.getDetail_url();
    }
}
