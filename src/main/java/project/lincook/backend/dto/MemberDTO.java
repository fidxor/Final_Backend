package project.lincook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.lincook.backend.entity.Gender;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private String token;
	private String email;
	private String password;
	private String name;
	private Gender gender;
	private String address; // 사용자 주소
	private double latitude; //위도
	private double longitude; //경도
}
