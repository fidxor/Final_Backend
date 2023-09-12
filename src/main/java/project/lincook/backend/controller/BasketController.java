package project.lincook.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.lincook.backend.dto.BasketDto;
import project.lincook.backend.entity.Basket;
import project.lincook.backend.repository.BasketRepository;
import project.lincook.backend.service.BasketService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final BasketRepository basketRepository;

    @GetMapping("/basket-info")
    public List<BasketDto> findBaskets(@RequestBody findBasketRequest request) {
        List<Basket> baskets = basketRepository.findByMemberID(request.memberId);

        System.out.println(baskets);
        List<BasketDto> collect = baskets.stream()
                .map(b -> new BasketDto(b))
                .collect(Collectors.toList());

        return collect;
    }

    /**
     * 장바구니에 상품 추가.
     * @param request
     * @return
     */
    @PostMapping("/create-basket")
    public Long createBasket(@RequestBody CreateBasketRequest request) {
        Long basketId = basketService.addBasket(request.memberId, request.contentsId, request.martId, request.productId);

        return basketId;
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
    }
}
