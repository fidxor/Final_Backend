package project.lincook.backend.dto;

import lombok.Data;

@Data
public class MartDto {

    private Long id;
    private String name;
    private String address;
    private String mart_call;
    private double distance;

    public MartDto(Long id, String name, String address, String mart_call, double distance) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mart_call = mart_call;
        this.distance = distance;
    }
}
