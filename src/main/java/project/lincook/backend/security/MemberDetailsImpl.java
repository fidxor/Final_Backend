package project.lincook.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.lincook.backend.entity.Member;

import java.util.ArrayList;
import java.util.Collection;

public class MemberDetailsImpl implements UserDetails {

	private final Member member;

	public MemberDetailsImpl(Member member) {
		this.member = member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(() -> member.getRole().getKey()); // key: ROLE_권한
		return authorities;
	}

	@Override
	public String getUsername() {
		return member.getEmail();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}


	// == 세부 설정 == //

	@Override
	public boolean isAccountNonExpired() { // 계정의 만료 여부
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // 계정의 잠김 여부
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // 비밀번호 만료 여부
		return true;
	}

	@Override
	public boolean isEnabled() { // 계정의 활성화 여부
		return true;
	}
}