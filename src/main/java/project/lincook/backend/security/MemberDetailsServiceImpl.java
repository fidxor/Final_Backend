package project.lincook.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.lincook.backend.common.exception.ErrorCode;
import project.lincook.backend.common.exception.LincookAppException;
import project.lincook.backend.entity.Member;
import project.lincook.backend.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public MemberDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
		List<Member> findMember = memberRepository.findByUserEmail(email);

		if (findMember.isEmpty()) {
			throw new LincookAppException(ErrorCode.NON_EXISTENT_MEMBER, String.format("email : ", email));
		}


		if(!findMember.isEmpty()){
			MemberDetailsImpl userDetails = new MemberDetailsImpl(findMember.get(0));
			return  userDetails;
		}

		return null;
	}
}
