package project.lincook.backend.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletException;


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = parseBearerToken(request);
			log.warn("Filter is running....");
//				위조된 토큰은 예외처리
			if (token != null && !token.equalsIgnoreCase("null")) {
				String userID = tokenProvider.validateAndGetUserId(token);
				log.info("Authenticated user ID : " + userID);
//				인증완료 , 사용자 ID로 인증객체 만들고, 권한부여하지않음
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userID,
						null,
						AuthorityUtils.NO_AUTHORITIES);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContext sercurityContext = SecurityContextHolder.createEmptyContext();
				sercurityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(sercurityContext);
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}
		filterChain.doFilter(request, response);

	}

	private String parseBearerToken(HttpServletRequest request){
//		Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
		String bearerToken = request.getHeader("Authorization");

		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
			return bearerToken.substring(7);
		}
		return null;
	}
}
