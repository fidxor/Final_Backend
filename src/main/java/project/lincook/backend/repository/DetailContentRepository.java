package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.DetailContent;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class DetailContentRepository {

    private final EntityManager em;

    public void save(DetailContent detailContent) {
        em.persist(detailContent);
    }

    public DetailContent findOne(Long id) {
        return em.find(DetailContent.class, id);
    }
}
