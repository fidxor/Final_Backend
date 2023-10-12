package project.lincook.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_CONTENTS_URL(HttpStatus.CONFLICT, "Contents url is duplicated."),    // 컨텐츠 URL 중복
    DUPLICATED_BASKET_PRODUCT(HttpStatus.CONFLICT, "BasketProduct is duplicated."), // 장바구니에 이미 있는 상품
    NOT_INCLUDE_BASKET_PRODUCT(HttpStatus.CONFLICT, "This product is not included in your basket."),    // 장바구니에 없는 상품
    NON_EXISTENT_MEMBER(HttpStatus.CONFLICT, "This member does not exist."),    // 존재하지 않는 회원정보
    FAILED_SIGNUP_MEMBER(HttpStatus.CONFLICT, "Failed signup member"),          // 회원가입 실패
    NON_EXISTENT_CONTENTS(HttpStatus.CONFLICT, "This contents does not exist."),    // 존재하지 않는 컨텐츠
    EMPTY_PRODUCT_LIST(HttpStatus.CONFLICT, "The content you are trying to write does not include any products."), // 컨텐츠 생성때 상품리스트가 비어있음.
    CONTENTS_EMPTY_URL(HttpStatus.CONFLICT, "Url String is Empty"), // 컨텐츠 생성때 URL 값이 비어있음
    NON_EXISTENT_MART(HttpStatus.CONFLICT, "This mart does not exist."),    // 존재하지 않는 마트정보
    NON_EXISTENT_PRODUCT(HttpStatus.CONFLICT, "This product does not exist.");  // 존재하지 않는 상품정보


    private HttpStatus status;
    private String message;
}
