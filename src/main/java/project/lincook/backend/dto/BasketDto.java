package project.lincook.backend.dto;

import lombok.Data;
import project.lincook.backend.entity.*;

import java.util.List;

@Data
public class BasketDto {

    private ContentsDto contentsDto;
    private MartDto martDto;
    private ProductDto productDto;

    public BasketDto(Basket basket) {
        Contents contents = List.copyOf(basket.getBasketDetailContents()).get(0).getContents();
        Mart mart = List.copyOf(basket.getBasketMarts()).get(0).getMart();
        Product product = List.copyOf(basket.getBasketProducts()).get(0).getProduct();

        System.out.println(contents);
        System.out.println(mart);
        System.out.println(product);

        this.contentsDto = new ContentsDto(contents.getId(), contents.getMember().getId(),
                contents.getTitle(), contents.getDescription(), contents.getUrl());
        // TODO : 거리계산...
        this.martDto = new MartDto(mart.getId(), mart.getName(), mart.getAddress(), mart.getPhone(), 0.1);
        this.productDto = new ProductDto(product);
    }

}
