package project.lincook.backend.jwtSecurity.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class LoginDto {
		private String email;
		private String password;

		@Builder
		public LoginDto(String email, String password) {
			this.email = email;
			this.password = password;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class SignupDto {
		private String email;
		private String password;
		private String name;
		private String gender;
		private double latitude;
		private double longitude;


		@Builder
		public SignupDto(String email,
		                 String password,
		                 String name,
		                 String gender,
		                 double latitude,
		                 double longitude){

			this.email = email;
			this.password = password;
			this.gender = gender;
			this.latitude = latitude;
			this.longitude = longitude;
		}

		public static SignupDto encodePassword(SignupDto signupDto, String encodedPassword) {
			SignupDto newSignupDto = new SignupDto();
			newSignupDto.email = signupDto.getEmail();
			newSignupDto.password = encodedPassword;
			return newSignupDto;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class TokenDto {
		private String accessToken;
		private String refreshToken;

		public TokenDto(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}
	}
}
