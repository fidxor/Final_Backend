package project.lincook.backend.entity;

import lombok.*;
import project.lincook.backend.dto.AuthDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email; // Principal
    private String password; // Credential
    private String name;
    private String gender;  // Enum : M, W
    private String address;
    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자 권한

    // == 생성 메서드 == //
    public static Member registerUser(AuthDto.SignupDto signupDto) {
        Member member = new Member();
        member.email = signupDto.getEmail();
        member.password = signupDto.getPassword();
        member.role = Role.USER;
        member.name = signupDto.getName();
        member.latitude = signupDto.getLatitude();
        member.longitude = signupDto.getLongitude();
        member.gender = signupDto.getGender();

        return member;
    }
}
