package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.DetailContent;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<DetailContent> findByContentsId(Long contentsId) {
        return em.createQuery("select d from DetailContent d" +
                        " join fetch d.detailContentProducts dc" +
                        " join fetch dc.product p" +
                        " where d.contents.id = :contentsId", DetailContent.class)
                .setParameter("contentsId", contentsId)
                .getResultList();
    }
}
