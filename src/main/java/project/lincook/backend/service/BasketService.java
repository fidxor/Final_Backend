package project.lincook.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.lincook.backend.entity.*;
import project.lincook.backend.repository.*;

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

    @Transactional
    public Long addBasket(Long memberId, Long contentsId, Long martId, Long productId) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Contents contents = contentsRepository.findOne(contentsId);
        Mart mart = martRepository.findOne(martId);
        Product product = productRepository.findOne(productId);

        // basket 생성
        Basket basket = new Basket();

        BasketDetailContent basketDetailContent = BasketDetailContent.createBasketDetailContent(basket, contents);
        BasketMart basketMart = BasketMart.createBasketMart(basket, mart);
        BasketProduct basketProduct = BasketProduct.createBasketProduct(basket, product);

        basket.setMember(member);
        basket.addBasketDetailContent(basketDetailContent);
        basket.addBasketMart(basketMart);
        basket.addBasketProduct(basketProduct);

        // db 저장
        basketRepository.save(basket);

        return basket.getId();
    }

    @Transactional
    public Long deleteBasket(Long basketId) {
        Basket basket = basketRepository.findOne(basketId);

        // TODO: Basket entity에서 id값으로 삭제를 하면 cascade 관계로 묶여있는 것들도 전부 삭제 되겠지??????돼야 한다.꼭......
        basketRepository.remove(basket);

        return 0L;
    }
}
