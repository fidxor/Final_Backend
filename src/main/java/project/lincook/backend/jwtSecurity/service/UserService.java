package project.lincook.backend.jwtSecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.lincook.backend.jwtSecurity.dto.AuthDto;
import project.lincook.backend.jwtSecurity.exception.ErrorMessage;
import project.lincook.backend.jwtSecurity.member.User;
import project.lincook.backend.jwtSecurity.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public User registerUser(AuthDto.SignupDto signupDto) {

		if (userRepository.existsByEmail(signupDto.getEmail())) {
			throw new RuntimeException("이미 가입되어 있는 유저입니다");
		}

		User user = User.registerUser(signupDto);
		return userRepository.save(user);
	}


}
