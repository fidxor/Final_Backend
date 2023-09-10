package project.lincook.backend.dto;

import lombok.Data;

@Data
public class ContentsDto {

    private Long id;
    private Long member_id;
    private String title;
    private String description;
    private String url;

    public ContentsDto(Long id, Long member_id, String title, String description, String url) {
        this.id = id;
        this.member_id = member_id;
        this.title = title;
        this.description = description;
        this.url = url;
    }
}
