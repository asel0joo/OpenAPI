package kr.co.wifiinfo;

import com.google.gson.Gson;

import kr.co.wifiinfo.Dto.HistoryDto;
import kr.co.wifiinfo.Dto.PublicWifiDto;
import kr.co.wifiinfo.Dto.PublicWifiListDto;
import kr.co.wifiinfo.Dto.PublicWifiListResultDto;
import kr.co.wifiinfo.Dto.WifiKMDto;
import kr.co.wifiinfo.Dto.WifiResultDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Services {
	
	//Open API 와이파이 정보 가져오기
	public int getInform(int startNum, int endNum) {
		Integer cnt = 0 ; // 총 조회개수
        WifiResultDto result = null; // 실행결과 (정상/실패)
        ArrayList<PublicWifiDto> items = null; // wifi 정보들
		
		try {
			String response = ApiExplorer.requestInform(startNum,endNum);
			
			if (response != null) {
			       
		        Gson gson = new Gson();
		        PublicWifiListDto wifiList = gson.fromJson(response, PublicWifiListDto.class);
	
		        PublicWifiListResultDto wifiResult = null;
		        
		        if(wifiList != null){
		        	wifiResult = wifiList.getTbPublicWifiInfo();
		            cnt = wifiResult.getList_total_count();
		            result = wifiResult.getRESULT();
		            items = wifiResult.getRow(); 
		        }
		        
				System.out.println(cnt);
				System.out.println(result.getCODE() + result.getMESSAGE());//정상실행
				
				
				
				for (PublicWifiDto item : items) {
					dbInsert(item);
					
				}
				
			}
		
		} catch (Exception e){
			
			System.out.println("search error : " + e.getMessage());
        }
		
		return cnt;	
	}
	
	//Open API 와이파이 정보 db insert
	public boolean dbInsert(PublicWifiDto item){
		boolean success = false;

        String url = "jdbc:mysql://localhost:3306/wifi?serverTimezone=UTC";
        String dbUserId = "root";
        String dbPassword = "0111";

        //1. 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2. 커넥션 개채 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3. 스테이트먼트 객체 생성
            String sql = " insert INTO wifi "
            		+ " (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) "
            		+ " values "
            		+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getX_SWIFI_MGR_NO());
            preparedStatement.setString(2, item.getX_SWIFI_WRDOFC());
            preparedStatement.setString(3, item.getX_SWIFI_MAIN_NM());
            preparedStatement.setString(4, item.getX_SWIFI_ADRES1()); 
            preparedStatement.setString(5, item.getX_SWIFI_ADRES2());
            preparedStatement.setString(6, item.getX_SWIFI_INSTL_FLOOR());
            preparedStatement.setString(7, item.getX_SWIFI_INSTL_TY());
            preparedStatement.setString(8, item.getX_SWIFI_INSTL_MBY());
            preparedStatement.setString(9, item.getX_SWIFI_SVC_SE());
            preparedStatement.setString(10, item.getX_SWIFI_CMCWR());
            preparedStatement.setString(11, item.getX_SWIFI_CNSTC_YEAR());
            preparedStatement.setString(12, item.getX_SWIFI_INOUT_DOOR());
            preparedStatement.setString(13, item.getX_SWIFI_REMARS3());
            preparedStatement.setString(14, item.getLAT());
            preparedStatement.setString(15, item.getLNT());
            preparedStatement.setString(16, item.getWORK_DTTM());

            //4. 쿼리실행
            int effected = preparedStatement.executeUpdate();

            // 5. 결과 확인
            if (effected > 0){
            	success = true;

            } else {
                System.out.println("저장 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            //6. 객체 연결 해제(close) - 무조건 실행 되어야하므로 마지막에 실행
            try {
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return success;
    }
	
	
	//위치 히스토리 목록 db insert
	public void insertHistory(String Lat, String Lnt){

        String url = "jdbc:mysql://localhost:3306/wifi?serverTimezone=UTC";
        String dbUserId = "root";
        String dbPassword = "0111";

        //1. 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2. 커넥션 개채 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3. 스테이트먼트 객체 생성
            String sql = " insert into LOCATION_HISTORY "
            		+ " (H_LAT, H_LNT, SEARCH_DATE) "
            		+ " values "
            		+ " (?, ?, now()) ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Lat);
            preparedStatement.setString(2, Lnt);
            
            //4. 쿼리실행
            int effected = preparedStatement.executeUpdate();

            // 5. 결과 확인
            if (effected > 0){
                System.out.println("저장 성공");
            } else {
                System.out.println("저장 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            //6. 객체 연결 해제(close) - 무조건 실행 되어야하므로 마지막에 실행
            try {
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
	 
	//위치 히스토리 목록 db select
	public static List<HistoryDto> historyList(){
    	List<HistoryDto> itemList = new ArrayList<>();
    	
        String url = "jdbc:mysql://localhost:3306/wifi?serverTimezone=UTC";
        String dbUserId = "root";
        String dbPassword = "0111";


        //1. 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2. 커넥션 개채 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3. 스테이트먼트 객체 생성
            String sql = " select ID , H_LAT , H_LNT , SEARCH_DATE "
            		+ " from LOCATION_HISTORY ; ";

            preparedStatement = connection.prepareStatement(sql);

            //4. 쿼리실행
            rs = preparedStatement.executeQuery();

            //5. 결과 수행
            while (rs.next()) {

                int id = rs.getInt("ID");
                float lat = rs.getFloat("H_LAT");
                float lnt = rs.getFloat("H_LNT");
                String search_date = rs.getString("SEARCH_DATE");
                
                HistoryDto item = new HistoryDto();
                item.setId(id);
                item.setLat(lat);
                item.setLnt(lnt);
                item.setDate(search_date);
                
                itemList.add(item); 
               }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6. 객체 연결 해제(close) - 무조건 실행 되어야하므로 마지막에 실행
            try {
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return itemList;
    }
	
	//위치 히스토리 목록 db 삭제
	public void deleteHistory (int id){

        String url = "jdbc:mysql://localhost:3306/wifi?serverTimezone=UTC";
        String dbUserId = "root";
        String dbPassword = "0111";

        //1. 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2. 커넥션 개채 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3. 스테이트먼트 객체 생성
            String sql = " Delete from LOCATION_HISTORY "
            		+ " where ID = ?; ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            //4. 쿼리실행
            int effected = preparedStatement.executeUpdate();

            // 5. 결과 확인
            if (effected > 0){
            	System.out.println("삭제 성공");

            } else {
                System.out.println("삭제 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            //6. 객체 연결 해제(close) - 무조건 실행 되어야하므로 마지막에 실행
            try {
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
	
	//근처 wifi 정보 보기 db select
	public PriorityQueue<WifiKMDto> wifiList(String nowLat, String nowLnt){
		PriorityQueue<WifiKMDto> itemList = new PriorityQueue<>();
		PriorityQueue<WifiKMDto> reverseList = new PriorityQueue<>(Collections.reverseOrder());
    	
        String url = "jdbc:mysql://localhost:3306/wifi?serverTimezone=UTC";
        String dbUserId = "root";
        String dbPassword = "0111";


        //1. 드라이버 로드
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2. 커넥션 개채 생성
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            //3. 스테이트먼트 객체 생성
            String sql = " SELECT X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1,"
            		+ "X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE,"
            		+" X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM"
            		+ " from WIFI ";

            preparedStatement = connection.prepareStatement(sql);

            //4. 쿼리실행
            rs = preparedStatement.executeQuery();

            //5. 결과 수행
            while (rs.next()) {

                String manageNo = rs.getString("X_SWIFI_MGR_NO");
                String gu = rs.getString("X_SWIFI_WRDOFC");
                String wifiName = rs.getString("X_SWIFI_MAIN_NM");
                String streetAdr = rs.getString("X_SWIFI_ADRES1");
                String detailAdr = rs.getString("X_SWIFI_ADRES2");
                String installLoc= rs.getString("X_SWIFI_INSTL_FLOOR");
                String installType = rs.getString("X_SWIFI_INSTL_TY");
                String installAgency = rs.getString("X_SWIFI_INSTL_MBY");
                String serviceType = rs.getString("X_SWIFI_SVC_SE");
                String netType = rs.getString("X_SWIFI_CMCWR");
                String installYear = rs.getString("X_SWIFI_CNSTC_YEAR");
                String inOrOut = rs.getString("X_SWIFI_INOUT_DOOR");
                String connectInv = rs.getString("X_SWIFI_REMARS3");
                String lat = rs.getString("LAT");
                String lnt = rs.getString("LNT");
                String workDate = rs.getString("WORK_DTTM");
                
                
                PublicWifiDto item = new PublicWifiDto();
                item.setX_SWIFI_MGR_NO(manageNo);
                item.setX_SWIFI_WRDOFC(gu);
                item.setX_SWIFI_MAIN_NM(wifiName);
                item.setX_SWIFI_ADRES1(streetAdr); 
                item.setX_SWIFI_ADRES2(detailAdr);
                item.setX_SWIFI_INSTL_FLOOR(installLoc);
                item.setX_SWIFI_INSTL_TY(installType);
                item.setX_SWIFI_INSTL_MBY(installAgency);
                item.setX_SWIFI_SVC_SE(serviceType);
                item.setX_SWIFI_CMCWR(netType);
                item.setX_SWIFI_CNSTC_YEAR(installYear);
                item.setX_SWIFI_INOUT_DOOR(inOrOut);
                item.setX_SWIFI_REMARS3(connectInv);
                item.setLAT(lat);
                item.setLNT(lnt);
                item.setWORK_DTTM(workDate);
                
                WifiKMDto wifiKilometer = new WifiKMDto();
                wifiKilometer.setItem(item); 
                double kilo = Math.round(distanceInKilometer(Double.parseDouble(lat), Double.parseDouble(lnt), 
                		Double.parseDouble(nowLat), Double.parseDouble(nowLnt)) * 1000) / 1000.0;
                
                wifiKilometer.setKilo(kilo);
                
                itemList.offer(wifiKilometer);
                
                if (itemList.size() > 20) {
                	itemList.poll();
                } 
               }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6. 객체 연결 해제(close) - 무조건 실행 되어야하므로 마지막에 실행
            try {
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        while (!itemList.isEmpty()) {
        	reverseList.offer(itemList.poll());
        }
        
        return reverseList;
    }
    
	
	// 하버파인 -> 위경도 거리 구하기
	public double distanceInKilometer(double x1, double y1, double x2, double y2) {
	    double distance;
	    double radius = 6371; // 지구 반지름(km)
	    double toRadian = Math.PI / 180;

	    double deltaLatitude = Math.abs(x1 - x2) * toRadian;
	    double deltaLongitude = Math.abs(y1 - y2) * toRadian;

	    double sinDeltaLat = Math.sin(deltaLatitude / 2);
	    double sinDeltaLng = Math.sin(deltaLongitude / 2);
	    double squareRoot = Math.sqrt(
	        sinDeltaLat * sinDeltaLat +
	        Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

	    distance = 2 * radius * Math.asin(squareRoot);

	    return distance;
	}

	// 기능 추가하기
	// 중복되는 것은 기본키가 관리번호로 설정되어있어서 제거된다.
	//(중복되었는데 모든 내용이 같으면 넘어가고 update 된 내용이 있으면 수정)
	public int insertAll() {
		// 페이지 변수 추가해서 1000개씩 가져오기
		Integer totalCnt = 0;
		int start = 0;
		int end = start + 999;
		
		while (totalCnt != null && start <= totalCnt) {
			totalCnt = getInform(start, end);
			
			start += 1000;
			end += 1000;
			if (end > totalCnt) {
				end = totalCnt;
			}
		}
		System.out.print("done");
		
		return totalCnt;
	}

}