package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.DeleteBasket;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DeleteBasketRepository {

    private final EntityManager em;

    public void save(DeleteBasket deleteBasket) {
        em.persist(deleteBasket);
    }
}
