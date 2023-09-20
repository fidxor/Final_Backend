package project.lincook.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.lincook.backend.entity.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }


    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Member findByUserEmail(String email) {
        return em.find(Member.class, email);
    }

    public static boolean isEmpty(Object obj) {
        // null 이라면
        if (obj == null)
            return false;

        return true;
    }
}
