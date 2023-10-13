package project.lincook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.lincook.backend.entity.Gender;

import javax.persistence.Column;

@Data
public class MemberDTO {

	private Long memberId;
	private String email;
	private double latitude; //위도
	private double longitude; //경도

	public MemberDTO(Long memberId, String email, double latitude, double longitude) {
		this.memberId = memberId;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
