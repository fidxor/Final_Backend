package project.lincook.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_CONTENTS_URL(HttpStatus.CONFLICT, "Contents url is duplicated."),
    DUPLICATED_BASKET_PRODUCT(HttpStatus.CONFLICT, "BasketProduct is duplicated."),
    NOT_INCLUDE_BASKET_PRODUCT(HttpStatus.CONFLICT, "This product is not included in your basket."),
    NON_EXISTENT_MEMBER(HttpStatus.CONFLICT, "This member does not exist."),
    FAILED_SIGNUP_MEMBER(HttpStatus.CONFLICT, "Failed signup member"),
    NON_EXISTENT_CONTENTS(HttpStatus.CONFLICT, "This contents does not exist."),
    NOT_INCLUDE_PRODUCT(HttpStatus.CONFLICT, "The content you are trying to write does not include any products."),
    CONTENTS_EMPTY_URL(HttpStatus.CONFLICT, "Url String is Empty");

    private HttpStatus status;
    private String message;
}
