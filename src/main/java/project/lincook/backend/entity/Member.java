package project.lincook.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String password;
    private String name;
    private String email;
    private Gender gender;  // Enum : M, W
    private String address;
    private float latitude;
    private float longitude;
}
