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

    public Basket findOne(Long id) {
        return em.find(Basket.class, id);
    }

    public List<Basket> findByMemberID(Long memberId) {
        System.out.println(memberId);
        return em.createQuery("select b from Basket b" +
                " join fetch b.basketDetailContents bdc" +
                " join fetch b.basketMarts bm" +
                " join fetch b.basketProducts bp" +
                " join fetch bdc.contents c" +
                " join fetch bm.mart m" +
                " join fetch bp.product p" +
                " where b.member.id = :memberId")
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
