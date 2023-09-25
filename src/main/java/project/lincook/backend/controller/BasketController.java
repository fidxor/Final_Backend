package project.lincook.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.lincook.backend.common.DistanceCal;
import project.lincook.backend.common.DistanceCollectionSort;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.dto.*;
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

    /**
     * 해당 유저의 장바구니 정보를 모두 찾아서 컨텐츠별, 마트별로 분류해 Dto 에 담아준다.
     *
     * @param request
     * @return
     */
    @GetMapping("/basket-info")
    public Response findBaskets(findBasketRequest request) {
        List<Basket> baskets = basketRepository.findAllByMemberID(request.memberId);

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
                BasketDto.BasketMartProduct martProduct = new BasketDto.BasketMartProduct(basketCollect.martDto, basketCollect.basketProductDto);
                basketContents.addMartProductList(martProduct);
            } else {
                // contents에 같은 마트 정보가 있는지 확인.
                BasketDto.BasketMartProduct martProduct = bb.getBasketMartProductList().stream()
                        .filter(mart -> basketCollect.martDto.getId().equals(mart.getMartDto().getId()))
                        .findAny()
                        .orElse(null);

                if (martProduct != null) {
                    // 장바구니 컨텐츠안에 같은 마트정보가 있을 경우 상품정보만 추가해준다.
                    martProduct.addProductDtoList(basketCollect.basketProductDto);
                } else {
                    // 새로 마트정보와 상품 정보를 추가해 준다.
                    BasketDto.BasketMartProduct basketMartProduct = new BasketDto.BasketMartProduct(basketCollect.martDto, basketCollect.basketProductDto);
                    bb.addMartProductList(basketMartProduct);
                }
            }

            // 마트 거리에 따라서 sort 해준다.
            if (bb != null) {
                bb.getBasketMartProductList().sort(new DistanceCollectionSort.DistanceCollectionSortByBasketMart());
            }
        }

        return Response.success(new BasketDto(basketContentsList));
    }

    /**
     * 장바구니에서 특정 상품을 삭제한다.
     * @param request
     * @return
     */
    @DeleteMapping("/delete-basket")
    public Long deleteBasket(@RequestBody DeleteBasketRequest request) {
        // 지우려는 상품이 db에 저장되어 있는 상품인지 확인한다.
        validateIncludeBasket(request.memberId, request.basketId);

        Long basketId = basketService.deleteBasket(request.basketId);
        return basketId;
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

    /**
     * 해당상품이 db에 있는지 확인.
     * @param basketId
     */
    private void validateIncludeBasket(Long memberId, Long basketId) {
        List<Basket> basket = basketRepository.findByMemberId(memberId, basketId);

        if (basket.isEmpty()) {
            throw new LincookAppException(ErrorCode.NOT_INCLUDE_BASKET_PRODUCT, String.format("basketId :", basketId));
        }
    }

    /**
     * 동일 컨텐츠의 동일 마트에 같은 상품이 장바구니에 등록되어있는지 확인.
     * @param request
     */
    private void validateDuplicateBasket(CreateBasketRequest request) {
        List<Basket> baskets = basketRepository.findAllByProductID(request.memberId, request.contentsId, request.martId, request.productId);

        if (!baskets.isEmpty()) {
            throw new LincookAppException(ErrorCode.DUPLICATED_BASKET_PRODUCT, String.format("productId :", request.productId));
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
    static class DeleteBasketRequest {
        private Long memberId;
        private Long basketId;
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
        private BasketProductDto basketProductDto;

        public findBasketCollect(Basket basket, double latitude, double longitude) {
            Contents contents = List.copyOf(basket.getBasketDetailContents()).get(0).getContents();
            Mart mart = List.copyOf(basket.getBasketMarts()).get(0).getMart();
            Product product = List.copyOf(basket.getBasketProducts()).get(0).getProduct();

            this.contentsDto = new ContentsDto(contents.getId(), contents.getMember().getId(),
                    contents.getTitle(), contents.getDescription(), contents.getUrl());

            // 현재 위치와 마트와의 거리를 계산해준다.
            double distance = DistanceCal.distance(latitude, longitude, mart.getLatitude(), mart.getLongitude());
            this.martDto = new MartDto(mart.getId(), mart.getName(), mart.getAddress(), mart.getPhone(), distance);
            this.basketProductDto = new BasketProductDto(basket.getId(), product);
        }
    }
}

