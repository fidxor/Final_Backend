package project.lincook.backend.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.lincook.backend.entity.Member;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY =
			"c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

	public String create(Member memberEntity) {
//		기한 지금으로부터 1일로 설정
		Date expiryDate = Date.from(
				Instant.now().plus(1, ChronoUnit.DAYS));
//		JWT 토큰 생성
		return Jwts.builder()
//		header에 들어갈 내용 및 서명을 하기 위한 SECRET_key
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setSubject(String.valueOf(memberEntity.getId())) // sub
				.setIssuer("demo app") //iss
				.setIssuedAt(new Date()) //iat
				.setExpiration(expiryDate) //exp
				.compact();
	}

//	Refresh Token


	public String validateAndGetUserId(String token){
		Claims claims = Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();

		return claims.getSubject();
	}
}
