package project.lincook.backend.common;

import project.lincook.backend.dto.BasketDto;
import project.lincook.backend.dto.MartDto;

public class DistanceCollectionSort {

    public static class DistanceCollectionSortByMartDto implements java.util.Comparator<MartDto> {

        @Override
        public int compare(MartDto mart1, MartDto mart2) {
            return Double.compare(mart1.getDistance(), mart2.getDistance());
        }
    }

    public static class DistanceCollectionSortByBasketMart implements java.util.Comparator<BasketDto.BasketMartProduct> {

        @Override
        public int compare(BasketDto.BasketMartProduct mart1, BasketDto.BasketMartProduct mart2) {
            return Double.compare(mart1.getMartDto().getDistance(), mart2.getMartDto().getDistance());
        }
    }


}