package project.lincook.backend.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Mart {

    @Id @GeneratedValue
    @Column(name = "mart_id")
    private Long id;

    private String name;
    private String address;
    private String phone;

    // TODO : 위도, 경도 좌표가 들어간다고 하면 추가

}
