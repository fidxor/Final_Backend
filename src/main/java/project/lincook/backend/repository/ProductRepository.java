package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.Product;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findByCode(int code) {

        return em.createQuery("select p from Product p where p.product_code = :code")
                .setParameter("code", code)
                .getResultList();
    }
}
