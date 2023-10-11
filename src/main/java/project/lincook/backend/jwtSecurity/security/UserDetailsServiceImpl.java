package project.lincook.backend.jwtSecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.lincook.backend.jwtSecurity.member.User;
import project.lincook.backend.jwtSecurity.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
		User findUser = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Can't find user with this email. -> " + email));

		if(findUser != null){
			UserDetailsImpl userDetails = new UserDetailsImpl(findUser);
			return  userDetails;
		}

		return null;
	}
}
