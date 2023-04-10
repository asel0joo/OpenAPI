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
public class HistoryDto {
	
		private int id;
		private float lat;
		private float lnt;
		private String date;
		
}
