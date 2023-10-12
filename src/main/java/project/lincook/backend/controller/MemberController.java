package project.lincook.backend.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.dto.MemberDTO;
import project.lincook.backend.dto.Response;
import project.lincook.backend.entity.Gender;
import project.lincook.backend.entity.Member;
import project.lincook.backend.security.TokenProvider;
import project.lincook.backend.service.MemberService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/auth")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private TokenProvider tokenProvider;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/signup")
	public Response registerUser(@RequestBody SignupRequest request) {
		try {
			if (request.email == null || request.password == null) {
				throw new RuntimeException("유효하지 않은 입력입니다.");
			}

//		요청을 이용해 저장할 유저 만들기 / 메서드 체이닝
			Member user = Member.builder()
					.email(request.email)
					.password(passwordEncoder.encode(request.password))
					.name(request.name)
					.address(request.address)
					.gender(request.gender)
					.latitude(request.latitude)
					.longitude(request.longitude)
					.joinDate(LocalDateTime.now())
					.build();
//		서비스를 이용해 리포지터리에 유저 저장
			Member registeredUser = memberService.create(user);
			MemberDTO responseMemberDTO = MemberDTO.builder()
					.email(registeredUser.getEmail())
					.password(registeredUser.getPassword())
					.name(registeredUser.getName())
					.gender(registeredUser.getGender())
					.address(registeredUser.getAddress())
					.longitude(registeredUser.getLongitude())
					.latitude(registeredUser.getLatitude())
					.build(); // 객체 반환
			//ResponseEntity.ok().body(responseMemberDTO);
//Response.success(new DetailContentDto(contentsDto, responseDetailContentList));
			return Response.success(responseMemberDTO);

		} catch (Exception e) {

			throw new LincookAppException(ErrorCode.FAILED_SIGNUP_MEMBER, String.format("productId :", request.email));
		}
	}

	@PostMapping("/signin")
	public Response authenticate(@RequestBody SigninRequest request) {
		Member user = memberService.getByCredentials(
				request.email,
				request.password,
				passwordEncoder);
		if (user != null) {
			final String token = tokenProvider.create(user);
			final MemberDTO responseMemberDTO = MemberDTO.builder()
					.email(user.getEmail())
					.password(user.getPassword())
					.token(token)
					.build();

			return Response.success(responseMemberDTO);
		} else {
			throw new LincookAppException(ErrorCode.NON_EXISTENT_MEMBER, String.format("productId :", request.email));
		}
	}
	@Data
	static class SignupRequest {

		private String email;
		private String password;
		private String address;
		private Gender gender;
		private String name;
		private double latitude;
		private double longitude;
	}
	@Data
	static class SigninRequest {

		private String email;
		private String password;
		private String token;
	}

}
