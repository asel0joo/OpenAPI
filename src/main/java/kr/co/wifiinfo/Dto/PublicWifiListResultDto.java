package kr.co.wifiinfo.Dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class PublicWifiListResultDto {
		
	 private Integer list_total_count;  // 전체 정보 개수
	 private WifiResultDto RESULT;  // 실행 성공여부 결과
	 private ArrayList<PublicWifiDto> row = null; // wifi 정보
		

}
