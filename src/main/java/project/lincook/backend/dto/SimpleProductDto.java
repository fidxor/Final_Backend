package project.lincook.backend.dto;

import lombok.Data;

@Data
public class SimpleProductDto {

    private String name;
    private String capacity;
    private int avg_price;
    private String img_url;

    public SimpleProductDto(String name, String capacity, int sale_price, String img_url) {
        this.name = name;
        this.capacity = capacity;
        this.img_url = img_url;
    }
}
