package project.lincook.backend.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.lincook.backend.dto.ContentsDto;
import project.lincook.backend.dto.DetailContentDto;
import project.lincook.backend.dto.MartDto;
import project.lincook.backend.dto.ProductDto;
import project.lincook.backend.entity.DetailContent;
import project.lincook.backend.entity.Mart;
import project.lincook.backend.entity.Product;
import project.lincook.backend.repository.DetailContentRepository;
import project.lincook.backend.repository.MartRepository;
import project.lincook.backend.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DetailContentController {

    private final DetailContentRepository detailContentRepository;
    private final ProductRepository productRepository;
    private final MartRepository martRepository;

    @GetMapping("/detail-content")
    public List<DetailContentDto> detailContentResult(@RequestBody DetailContentRequest request) {
        List<DetailContent> detailContentList = detailContentRepository.findByContentsId(request.contents_id);

        List<DetailContentDto> collect = detailContentList.stream()
                .map(d -> new DetailContentDto(d))
                .collect(Collectors.toList());

        // TODO : product_code 값이 같은 product들 불러와서 해당 product들 마트 정보 가져오고, 마트 거리 계산해서 리스트 만들기.
        for (DetailContentDto detailContentDto : collect) {
            // 식재료 code 번호로 같은 code의 Product 정보를 가져온다.
            int code = detailContentDto.getProductDto().getCode();

            List<Product> products = productRepository.findByCode(code);
            List<MartDto> martDtoList = new ArrayList<>();
            for (Product product : products) {
                // 각각의 Product정보로 Mart정보를 가져온다.
                Long martId = product.getMart().getId();

                Mart mart = martRepository.findOne(martId);

                // 위도 경도 값으로 현재 나의 위치와 마트위치의 거리 계산
                double kilometer = distance(request.latitude, request.longitude, mart.getLatitude(), mart.getLongitude());

                // 현재 지정된 위치부터 6Km 이내에 위치한 마트만 리스트에 넣어준다.
                if (kilometer < 6.0) {
                    MartDto martDto = new MartDto(mart.getId(), mart.getName(), mart.getAddress(), mart.getPhone(), kilometer);
                    martDtoList.add(martDto);
                }

                detailContentDto.setMartDtoList(martDtoList);
            }
        }
        return collect;
    }

    private static double distance(double myLat, double myLong, double martLat, double martLong) {
        double theta = myLong - martLong;
        double dist = Math.sin(deg2rad(myLat)) * Math.sin(deg2rad(martLat)) + Math.cos(deg2rad(myLat)) * Math.cos(deg2rad(martLat)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        return Math.floor(dist * 100) / 100.0;
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    @Data
    static class DetailContentRequest {
        private Long contents_id;
        private double latitude;
        private double longitude;
    }
}
