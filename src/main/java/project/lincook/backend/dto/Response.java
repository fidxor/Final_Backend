package project.lincook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * 모든 Response는 현재 Response클래스로 감싸서 return 한다.
 */
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> error(String resultCode) {
        return new Response<>(resultCode, null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("Success", result);
    }
}
