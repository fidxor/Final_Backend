package project.lincook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import project.lincook.backend.entity.DetailContent;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Data
@AllArgsConstructor
public class DetailContentDto<T> {

    private ContentsDto contentsDto;
    private T data;

    @Data
    @AllArgsConstructor
    public static class ResponseDetailContent {
        private ProductDto productDto;
        private List<MartDto> martDtoList;
    }
}


