package project.lincook.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private Gender gender;  // Enum : M, W
    private String address;
    private double latitude;
    private double longitude;

    @Column(nullable = false)
    private String email;
    private String password;
    private String role;
    private String authProvider;
}
