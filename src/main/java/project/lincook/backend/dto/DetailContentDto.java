package project.lincook.backend.dto;

import lombok.Data;
import lombok.Setter;
import project.lincook.backend.entity.DetailContent;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@Setter
public class DetailContentDto {

    private Long detailContentId;
    private ContentsDto contentsDto;
    private ProductDto productDto;
    private List<MartDto> martDtoList = new ArrayList<>();

    public DetailContentDto(Long detailContentId, ContentsDto contentsDto, ProductDto productDto) {
        this.detailContentId = detailContentId;
        this.contentsDto = contentsDto;
//        this.productDtoList = productDto;
    }

    public DetailContentDto(DetailContent dc) {
        this.detailContentId = dc.getId();
        this.contentsDto = new ContentsDto(dc.getContents().getId(), dc.getContents().getMember().getId(), dc.getContents().getTitle(),
                dc.getContents().getDescription(), dc.getContents().getUrl());
        this.productDto = new ProductDto(dc.getDetailContentProducts().get(0).getProduct());
    }
}


