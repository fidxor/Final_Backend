package project.lincook.backend.controller;

import antlr.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.dto.MemberDTO;
import project.lincook.backend.dto.Response;
import project.lincook.backend.dto.AuthDto;
import project.lincook.backend.security.JwtTokenProvider;
import project.lincook.backend.security.TokenProvider;
import project.lincook.backend.entity.Member;
import project.lincook.backend.repository.MemberRepository;

import project.lincook.backend.service.AuthService;
import project.lincook.backend.service.MemberService;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

	private final AuthService authService;
	private final MemberService memberService;
	private final BCryptPasswordEncoder encoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;


	private final long COOKIE_EXPIRATION = 7776000; // 90일

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<Response<Object>> signup(@RequestBody @Valid AuthDto.SignupDto signupDto) {
		try {
			if(signupDto == null || signupDto.getPassword() == null){
				throw new LincookAppException(ErrorCode.INVALID_PASSWORD, "wrong password");
			}
		}catch (Exception e) {
			final Response<Object> responseDTO = new Response<>(e.getMessage(), null);
			return ResponseEntity.badRequest().body(responseDTO);
		}
		String encodedPassword = encoder.encode(signupDto.getPassword());
		AuthDto.SignupDto newSignupDto = AuthDto.SignupDto.encodePassword(signupDto, encodedPassword);

		memberService.registerUser(newSignupDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 로그인 -> 토큰 발급
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid AuthDto.LoginDto loginDto) {
		// User 등록 및 Refresh Token 저장
		AuthDto.TokenDto tokenDto = authService.login(loginDto);

		List<Member> members = memberRepository.findByUserEmail(loginDto.getEmail());

		if (members.isEmpty()) {
			throw new LincookAppException(ErrorCode.NON_EXISTENT_MEMBER, String.format("email: ", loginDto.getEmail()));
		}

		MemberDTO memberDTO = new MemberDTO(members.get(0).getId(), members.get(0).getEmail(), members.get(0).getLatitude(), members.get(0).getLongitude());


		// RT 저장
		HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
				.maxAge(COOKIE_EXPIRATION)
				.httpOnly(true)
				.secure(true)
				.build();

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, httpCookie.toString())
				// AT 저장
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken())
				.body(memberDTO);
	}

	@PostMapping("/validate")
	public ResponseEntity<?> validate(@RequestBody AuthDto.ValidateTokenDto requestAccessToken) {
		String accessToken = authService.resolveToken(requestAccessToken.getAccessToken());
		if (accessToken != null && !authService.validate(requestAccessToken.getAccessToken()) && jwtTokenProvider.validateAccessToken(accessToken)) {
			return ResponseEntity.status(HttpStatus.OK).build(); // 재발급 필요X
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // 재발급 필요
		}
	}
	// 토큰 재발급
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
	                                 @RequestHeader("Authorization") String requestAccessToken) {
		AuthDto.TokenDto reissuedTokenDto = authService.reissue(requestAccessToken, requestRefreshToken);

		if (reissuedTokenDto != null) { // 토큰 재발급 성공
			// RT 저장
			ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
					.maxAge(COOKIE_EXPIRATION)
					.httpOnly(true)
					.secure(true)
					.build();
			return ResponseEntity
					.status(HttpStatus.OK)
					.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
					// AT 저장
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
					.build();

		} else { // Refresh Token 탈취 가능성
			// Cookie 삭제 후 재로그인 유도
			ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
					.maxAge(0)
					.path("/")
					.build();
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
					.build();
		}
	}

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
		authService.logout(requestAccessToken);
		ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
				.maxAge(0)
				.path("/")
				.build();

		return ResponseEntity
				.status(HttpStatus.OK)
				.header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.build();
	}
}