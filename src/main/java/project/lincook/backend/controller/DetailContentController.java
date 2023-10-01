package project.lincook.backend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.lincook.backend.common.DistanceCal;
import project.lincook.backend.common.DistanceCollectionSort;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.dto.*;
import project.lincook.backend.entity.Contents;
import project.lincook.backend.entity.DetailContent;
import project.lincook.backend.entity.Mart;
import project.lincook.backend.entity.Product;
import project.lincook.backend.repository.DetailContentRepository;
import project.lincook.backend.repository.MartRepository;
import project.lincook.backend.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DetailContentController {

    private final DetailContentRepository detailContentRepository;
    private final ProductRepository productRepository;
    private final MartRepository martRepository;

    /**
     * contentsID 로 contents 정보를 전달한다.
     * @param request
     * @return
     */
    @GetMapping("/detail-content")
    public Response detailContentResult(DetailContentRequest request) {
        List<DetailContent> detailContentList = detailContentRepository.findByContentsId(request.contents_id);

        if (detailContentList.isEmpty()) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_CONTENTS, String.format("id :", request.contents_id));
        }

        return makeDtoData(request.latitude, request.longitude, detailContentList);
    }

    /**
     * url 을 가지고 contents 정보를 전달한다.
     * @param request
     * @return
     */
    @GetMapping("url-detail-contents")
    public Response detailContentsByUrl(DetailContentRequest request) {
        List<DetailContent> detailContentList = detailContentRepository.findByUrl(request.contents_url);

        if (detailContentList.isEmpty()) {
            throw new LincookAppException(ErrorCode.NON_EXISTENT_CONTENTS, String.format("id :", request.contents_id));
        }

        return makeDtoData(request.latitude, request.longitude, detailContentList);
    }

    /**
     * Entity정보로 Dto를 만든다.
     * @param latitude
     * @param longitude
     * @param detailContentList
     * @return
     */
    private Response makeDtoData(double latitude, double longitude, List<DetailContent> detailContentList) {
        List<DetailContentCollect> collect = detailContentList.stream()
                .map(d -> new DetailContentCollect(d))
                .collect(Collectors.toList());

        // collect가 리스트 이기 때문에 collect내의 모든 값에 Contents 객체가 같은 값으로 들어가게 된다.
        // Contnets 객체 하나만 Dto에 넣어 주자.
        ContentsDto contentsDto = collect.get(0).contentsDto;

        List<DetailContentDto.ResponseDetailContent> responseDetailContentList = new ArrayList<>();

        for (DetailContentCollect detailContentDto : collect) {
            // 식재료 code 번호로 같은 code의 Product 정보를 가져온다.
            int code = detailContentDto.getProductDto().getCode();

            // db에 저장돼어있는 상품의 코드와 같은 상품의 리스트를 전부 가져온다.
            List<Product> products = productRepository.findByCode(code);

            // detailcontent에 출력되는 마트 정보에는 해당마트에서 판매하는 상품의 가격과 id가 필요하다.
            List<ProductMartDto> martDtoList = new ArrayList<>();

            for (Product product : products) {

                ProductDto productDto = new ProductDto(product);
                // 각각의 Product정보로 Mart정보를 가져온다.
                Long martId = product.getMart().getId();

                Mart mart = martRepository.findOne(martId);

                // 위도 경도 값으로 현재 나의 위치와 마트위치의 거리 계산
                double kilometer = DistanceCal.distance(latitude, longitude, mart.getLatitude(), mart.getLongitude());

                // 현재 지정된 위치부터 6Km 이내에 위치한 마트만 리스트에 넣어준다
                if (kilometer < 6.0) {
                    MartDto martDto = new MartDto(mart.getId(), mart.getName(), mart.getAddress(), mart.getPhone(), kilometer);
                    ProductMartDto productMartDto = new ProductMartDto(product.getId(), product.getSale_price(), martDto);
                    martDtoList.add(productMartDto);
                }
            }

            DetailContentDto.ResponseDetailContent responseDetailContent = new DetailContentDto.ResponseDetailContent(detailContentDto.getProductDto(), martDtoList);

            // 마트 거리별로 오름차순으로 정렬한다.
            responseDetailContent.getMartDtoList().sort(new DistanceCollectionSort.DistanceCollectionSortByProductMartDto());

            responseDetailContentList.add(responseDetailContent);
        }
        return Response.success(new DetailContentDto(contentsDto, responseDetailContentList));
    }

    @Data
    static class DetailContentRequest {
        private Long contents_id;
        public String contents_url; // "" 비어있는 스트링값일수 있음.
        private double latitude;
        private double longitude;
    }

    @Data
    static class DetailContentCollect {
        private Long detailContentId;
        private ContentsDto contentsDto;
        private ProductDto productDto;
        private List<MartDto> martDtoList = new ArrayList<>();

        public DetailContentCollect(DetailContent dc) {
            this.detailContentId = dc.getId();
            this.contentsDto = new ContentsDto(dc.getContents().getId(), dc.getContents().getMember().getId(), dc.getContents().getTitle(),
                    dc.getContents().getDescription(), dc.getContents().getUrl());
            this.productDto = new ProductDto(dc.getDetailContentProducts().get(0).getProduct());
        }
    }

}
