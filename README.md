# 내 위치 기반 공공 와이파이 정보를 제공하는 웹서비스
## 서울 열린데이터광장 Open API를 활용해, 근처 와이파이를 20개 보여주기

*****

### 홈화면
<img width="800" alt="홈화면" src="https://user-images.githubusercontent.com/111031449/230828745-f224335e-8b62-4f99-afde-15ecfaa5b1d0.png">

*****
### 기능 구현
1. Open Api 정보 가져오기

2. 내 위치 가져오기

3. 근처 와이파이 정보 가져오기

4. 위치 히스토리 목록 저장

5. 위치 히스토리 목록 삭제

*****
### 파일 설명
<img width="300" alt="파일 구조" src="https://user-images.githubusercontent.com/111031449/230829987-3a47c747-c0b5-4db6-8619-79fb7052b05b.png">
 - java/kr/co.wifiinfo 
  - Dto.java : 데이터 객체
  - ApiExplorer.java : 서울 열린데이터 광장의 Open Api의 json DB 가져오기
  - Services.java : DB insert, select, delete 기능
  
 - webapp
  - dataProcess.jsp : history.jsp에 db insert, 근처 와이파이 정보 db 출력을 위해 id 전달 역할
  - history.jsp : 위치 히스토리 목록 페이지 
  - index.jsp : 메인 페이지
  - load_wifi.jsp : Open Api db 정상호출 완료 페이지
