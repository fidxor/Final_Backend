package project.lincook.backend.jwtSecurity.exception;

public final class ErrorMessage {
	public static final String ENTITY_IS_NOT_NULL = "Entity는 null이 될 수 없습니다";
	public static final String UNKNOWN_USER = "존재하지 않는 유저입니다.";
	public static final String DELETE_ERROR = "Delete 하는데 Error가 발생했습니다";
	public static final String INVALID_ARGUMENT = "유효하지 않은 인자를 받았습니다.";
	public static final String ALREADY_EXISTS_USERNAME = "이미 존재하는 유저 이름입니다.";
	public static final String INVALID_PASSWORD = "pasword가 틀립니다.";
	public static final String LOGIN_FAILED = "로그인에 실패했습니다.";
}

