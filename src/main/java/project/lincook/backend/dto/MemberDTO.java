package project.lincook.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.lincook.backend.entity.Gender;

import javax.persistence.Column;

@Data
public class MemberDTO {

	private Long membeId;
	private String email;
	private double latitude; //위도
	private double longitude; //경도

	public MemberDTO(Long membeId, String email, double latitude, double longitude) {
		this.membeId = membeId;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
