package project.lincook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BasketDto<T> {

    private T data;

    /**
     * 하나의 컨텐츠가 마트를 리스트로 가지고있고
     * 또 각각의 마트가 상품을 리스트로 가지고 있다.
     * 그리고 BasketDto 는 컨텐츠를 리스트로 가진다.
     */
    @Data
    @Getter
    public static class BasketContents {
        private ContentsDto contentsDto;
        private List<BasketMartProduct> basketMartProductList = new ArrayList<>();

        public void addMartProductList(BasketMartProduct martProduct) {
            basketMartProductList.add(martProduct);
        }

        public BasketContents(ContentsDto contentsDto, BasketMartProduct martProduct) {
            this.contentsDto = contentsDto;
            if(martProduct != null)
                addMartProductList(martProduct);
        }
    }

    @Data
    public static class BasketMartProduct {
        private MartDto martDto;
        private List<ProductDto> productDtoList = new ArrayList<>();

        public void addProductDtoList(ProductDto productDto) {
            productDtoList.add(productDto);
        }

        public BasketMartProduct(MartDto martDto, ProductDto productDto) {
            this.martDto = martDto;
            if(productDto != null)
                addProductDtoList(productDto);
        }
    }

}
