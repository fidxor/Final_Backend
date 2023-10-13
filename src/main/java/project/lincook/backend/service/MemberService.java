package project.lincook.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import project.lincook.backend.dto.AuthDto;
import project.lincook.backend.entity.Member;
import project.lincook.backend.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	public Member create(final Member memberEntity){
		if(memberEntity == null || memberEntity.getEmail() == null){
			throw new RuntimeException("유효하지 않은 입력입니다.");
		}
		final String email = memberEntity.getEmail();
		List<Member> member = memberRepository.findByUserEmail(email);
		if(!member.isEmpty()) {
			log.warn("이미 존재하는 사용자 이메일입니다.",email);
			throw new RuntimeException("이미 존재하는 사용자 이메일입니다.");
		}
		memberRepository.save(memberEntity);
		return memberEntity;

	}

//	public Member getByCredentials(final String username, final String password){
//		return memberRepository.findByUsernameAndPassword(username,password);
//	}
	//	패스워드 암호화username
	public Member getByCredentials(final String email,
	                                   final String password,
	                                   final PasswordEncoder encoder){
		final List<Member> originalUser = memberRepository.findByUserEmail(email);

//		matches 메서드를 이용해 패스워드가 같은지 확인
		if (originalUser != null && encoder.matches(password,originalUser.get(0).getPassword())){
			return originalUser.get(0);
		}
		return null;
	}

	@Transactional
	public Long registerUser(AuthDto.SignupDto signupDto) {

		if (!memberRepository.findByUserEmail(signupDto.getEmail()).isEmpty()) {
			throw new RuntimeException("이미 가입되어 있는 유저입니다");
		}

		Member member = Member.registerUser(signupDto);
		memberRepository.save(member);

		return member.getId();
	}
}
