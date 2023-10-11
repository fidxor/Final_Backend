package project.lincook.backend.jwtSecurity.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.lincook.backend.entity.Gender;
import project.lincook.backend.jwtSecurity.dto.AuthDto;

import javax.persistence.*;

@Entity(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	private String email; // Principal
	private String password; // Credential
	private String name;
	private Gender gender;  // Enum : M, W
	private String address;
	private double latitude;
	private double longitude;

	@Enumerated(EnumType.STRING)
	private Role role; // 사용자 권한

	// == 생성 메서드 == //
	public static User registerUser(AuthDto.SignupDto signupDto) {
		User user = new User();

		user.email = signupDto.getEmail();
		user.password = signupDto.getPassword();
		user.role = Role.USER;

		return user;
	}
}