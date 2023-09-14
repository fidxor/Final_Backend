package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.Basket;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasketRepository {

    private final EntityManager em;

    public void save(Basket basket) {
        em.persist(basket);
    }

    public void remove(Basket basket) {
        em.remove(basket);
    }

    public Basket findOne(Long id) {
        return em.find(Basket.class, id);
    }

    public List<Basket> findByMemberId(Long memberId, Long basketId) {
        return em.createQuery("select b from Basket b where b.member.id = :memberId and b.id = :basketId")
                .setParameter("memberId", memberId)
                .setParameter("basketId", basketId)
                .getResultList();
    }

    public List<Basket> findAllByMemberID(Long memberId) {

        return em.createQuery("select b from Basket b" +
                " join fetch b.basketDetailContents bdc" +
                " join fetch b.basketMarts bm" +
                " join fetch b.basketProducts bp" +
                " where b.member.id = :memberId")
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Basket> findAllByProductID(Long memberId, Long contentsId, Long martId, Long productId) {
        return em.createQuery("select b from Basket b" +
                        " join fetch b.basketDetailContents bdc" +
                        " join fetch b.basketMarts bm" +
                        " join fetch b.basketProducts bp" +
                        " where b.member.id = :memberId" +
                        " and bdc.contents.id = :contentsId" +
                        " and bm.mart.id = :martId" +
                        " and bp.product.id = :productId")
                .setParameter("memberId", memberId)
                .setParameter("contentsId", contentsId)
                .setParameter("martId", martId)
                .setParameter("productId", productId)
                .getResultList();
    }
}
