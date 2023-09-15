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

    private double latitude;
    private double longitude;
}
