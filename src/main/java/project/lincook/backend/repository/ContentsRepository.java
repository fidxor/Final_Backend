package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.Contents;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContentsRepository {

    private final EntityManager em;

    public void save(Contents contents) {
        em.persist(contents);
    }

    public Contents findOne(Long id) {
        return em.find(Contents.class, id);
    }

    public List<Contents> findAll(int offset, int limit) {
        return em.createQuery("select c from Contents c", Contents.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public List<Contents> findById(Long id, int offset, int limit) {
        return em.createQuery("select c from Contents c where c.id = :id", Contents.class)
                .setParameter("id", id)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Contents> findByUrl(String url) {
        return em.createQuery("select c from Contents c where c.url = :url", Contents.class)
                .setParameter("url", url)
                .getResultList();
    }
}
