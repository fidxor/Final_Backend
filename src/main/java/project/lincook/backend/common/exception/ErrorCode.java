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
    NON_EXISTENT_MEMBER(HttpStatus.CONFLICT, "This member does not exist.");

    private HttpStatus status;
    private String message;
}
