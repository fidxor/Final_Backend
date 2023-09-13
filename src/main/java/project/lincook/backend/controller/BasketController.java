package project.lincook.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.lincook.backend.common.DistanceCal;
import project.lincook.backend.dto.BasketDto;
import project.lincook.backend.dto.ContentsDto;
import project.lincook.backend.dto.MartDto;
import project.lincook.backend.dto.ProductDto;
import project.lincook.backend.entity.Basket;
import project.lincook.backend.entity.Contents;
import project.lincook.backend.entity.Mart;
import project.lincook.backend.entity.Product;
import project.lincook.backend.repository.BasketRepository;
import project.lincook.backend.service.BasketService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final BasketRepository basketRepository;

    @GetMapping("/basket-info")
    public BasketDto findBaskets(@RequestBody findBasketRequest request) {
        List<Basket> baskets = basketRepository.findByMemberID(request.memberId);

        List<findBasketCollect> collect = baskets.stream()
                .map(b -> new findBasketCollect(b, request.latitude, request.longitude))
                .collect(Collectors.toList());


        List<BasketDto.BasketContents> basketContentsList = new ArrayList<>();

        for (findBasketCollect basketCollect : collect) {

            // 장바구니에 동일한 contentsId가 있는지 확인
            BasketDto.BasketContents bb= basketContentsList.stream()
                    .filter(contents -> basketCollect.contentsDto.getId().equals(contents.getContentsDto().getId()))
                    .findAny()
                    .orElse(null);

            if (bb == null) {
                BasketDto.BasketContents basketContents = new BasketDto.BasketContents(basketCollect.contentsDto, null);
                basketContentsList.add(basketContents);

                // 장바구니에 처음 입력되는 contents이기 때문에 마트정보와 상품정보도 바로 넣어준다.
                BasketDto.BasketMartProduct martProduct = new BasketDto.BasketMartProduct(basketCollect.martDto, basketCollect.productDto);
                basketContents.addMartProductList(martProduct);
            } else {
                // contents에 같은 마트 정보가 있는지 확인.
                BasketDto.BasketMartProduct martProduct = bb.getBasketMartProductList().stream()
                        .filter(mart -> basketCollect.martDto.getId().equals(mart.getMartDto().getId()))
                        .findAny()
                        .orElse(null);

                if (martProduct != null) {
                    // 장바구니 컨텐츠안에 같은 마트정보가 있을 경우 상품정보만 추가해준다.
                    martProduct.addProductDtoList(basketCollect.productDto);
                } else {
                    // 새로 마트정보와 상품 정보를 추가해 준다.
                    BasketDto.BasketMartProduct basketMartProduct = new BasketDto.BasketMartProduct(basketCollect.martDto, basketCollect.productDto);
                    bb.addMartProductList(basketMartProduct);
                }
            }
        }

        return new BasketDto(basketContentsList);
    }

    /**
     * 장바구니에 상품 추가.
     * @param request
     * @return
     */
    @PostMapping("/create-basket")
    public Long createBasket(@RequestBody CreateBasketRequest request) {
        // 장바구니에 포함되어있는 상품인지 검사.
        validateDuplicateBasket(request);
        Long basketId = basketService.addBasket(request.memberId, request.contentsId, request.martId, request.productId);

        return basketId;
    }

    private void validateDuplicateBasket(CreateBasketRequest request) {
        List<Basket> baskets = basketRepository.findByProductID(request.memberId, request.contentsId, request.martId, request.productId);

        if (!baskets.isEmpty()) {
            throw new IllegalStateException("이미 등록되어있는 상품입니다.");
        }
    }

    @Data
    static class CreateBasketRequest {
        private Long memberId;
        private Long contentsId;
        private Long productId;
        private Long martId;
    }

    @Data
    static class findBasketRequest {
        private Long memberId;
        private double latitude;
        private double longitude;
    }

    @Data
    static class findBasketCollect {
        private ContentsDto contentsDto;
        private MartDto martDto;
        private ProductDto productDto;

        public findBasketCollect(Basket basket, double latitude, double longitude) {
            Contents contents = List.copyOf(basket.getBasketDetailContents()).get(0).getContents();
            Mart mart = List.copyOf(basket.getBasketMarts()).get(0).getMart();
            Product product = List.copyOf(basket.getBasketProducts()).get(0).getProduct();

            this.contentsDto = new ContentsDto(contents.getId(), contents.getMember().getId(),
                    contents.getTitle(), contents.getDescription(), contents.getUrl());

            double distance = DistanceCal.distance(latitude, longitude, mart.getLatitude(), mart.getLongitude());
            this.martDto = new MartDto(mart.getId(), mart.getName(), mart.getAddress(), mart.getPhone(), distance);
            this.productDto = new ProductDto(product);
        }
    }

}
