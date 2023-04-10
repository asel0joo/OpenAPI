package kr.co.wifiinfo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class WifiResultDto {
	
	private String CODE;  // 실행 결과 코드
	private String MESSAGE; // 실행 결과 메시지
		

}
