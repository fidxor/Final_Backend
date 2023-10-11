package project.lincook.backend.jwtSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.lincook.backend.jwtSecurity.member.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);


}
