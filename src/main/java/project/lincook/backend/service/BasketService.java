package project.lincook.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.controller.BasketController;
import project.lincook.backend.entity.*;
import project.lincook.backend.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final MemberRepository memberRepository;
    private final ContentsRepository contentsRepository;
    private final MartRepository martRepository;
    private final ProductRepository productRepository;
    private final DeleteBasketRepository deleteBasketRepository;

    @Transactional
    public Long addBasket(Long memberId, Long contentsId, Long martId, Long productId) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Contents contents = contentsRepository.findOne(contentsId);
        Mart mart = martRepository.findOne(martId);
        Product product = productRepository.findOne(productId);

        // 멤버, 컨텐츠, 마트, 상품정보가 존재하는지 확인.
        validateRequestInfo(member, contents, mart, product);

        // basket 생성
        Basket basket = new Basket();

        BasketDetailContent basketDetailContent = BasketDetailContent.createBasketDetailContent(basket, contents);
        BasketMart basketMart = BasketMart.createBasketMart(basket, mart);
        BasketProduct basketProduct = BasketProduct.createBasketProduct(basket, product);

        basket.setMember(member);
        basket.addBasketDetailContent(basketDetailContent);
        basket.addBasketMart(basketMart);
        basket.addBasketProduct(basketProduct);
        basket.setRegDate(LocalDateTime.now());

        // db 저장
        basketRepository.save(basket);

        return basket.getId();
    }

    @Transactional
    public Long deleteBasket(Long basketId) {
        Basket basket = basketRepository.findOne(basketId);

        DeleteBasket deleteBasket = DeleteBasket.createDeleteBasket(basket.getMember().getId(), basket.getId(),
                                                    basket.getContentsOfList().getId(), basket.getProductOfList().getId(),
                                                    basket.getMartOfList().getId());

        deleteBasketRepository.save(deleteBasket);

        basketRepository.remove(basket);

        return 0L;
    }

    private void validateRequestInfo(Member member, Contents contents, Mart mart, Product product) {

        if (member == null) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_MEMBER, "");
        }
        if (contents == null) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_CONTENTS, "");
        }
        if (mart == null) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_MART, "");
        }
        if (product == null) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_PRODUCT, "");
        }
    }
}
