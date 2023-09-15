package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.Mart;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MartRepository {

    private final EntityManager em;

    public Mart findOne(Long id) {
        return em.find(Mart.class, id);
    }
}
