package kr.co.wifiinfo;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
	
	public static String requestInform(int startPage, int endPage) {
		
		String request = null;
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		try {
			urlBuilder.append("/" + URLEncoder.encode("4c59495946736f6f39367772457975","UTF-8") );/*인증키*/
			urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") ); /*요청파일타입(xml,xmlf,xls,json) */
			urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
			urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startPage),"UTF-8")); /*요청시작위치*/
			urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endPage),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
			urlBuilder.append("/" + URLEncoder.encode("","UTF-8")); /* 서비스별추가 요청인자들*/
			urlBuilder.append("/" + URLEncoder.encode("","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		try {
			url = new URL(urlBuilder.toString());
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/xml");
			System.out.println("Response code: " + conn.getResponseCode()); /* 연결자체에 대한 확인이 필요하므로 추가합니다.*/
			
			// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			
			rd.close();
			conn.disconnect();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return request;
		
	}
}