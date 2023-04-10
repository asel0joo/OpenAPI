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
public class WifiKMDto implements  Comparable<WifiKMDto> {

		Double kilo;
		PublicWifiDto item;
		
			
		@Override
		public int compareTo(WifiKMDto o) {
			return o.kilo >= this.kilo ? 1 : -1;
		}

}
