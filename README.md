# 오픈 API를 이용한 "책 검색 서비스"

## 기능
1. 회원가입/로그인
2. 책 검색
3. 검색된 책의 상세 조회
4. 내 검색 히스토리
5. 인기 키워드 목록

## 실행
```
체크아웃
cd ${project_base}
git clone ${url}

빌드
cd ui
npm install
npm run build
mvn clean build

실행
java -Dspring.profiles.active=local -jar book.jar
```
```
다운로드 링크 : 
https://drive.google.com/open?id=1n0MmnEjT4wFMg3mD_Pv50SVyKc51ylb5
https://nas.hohoon.xyz:10003/d/f/513913325220836624
```

## 구현
### Server
#### Server 사용기술
- org.springframework.boot:spring-boot-starter-data-jpa : JPA 인터페이스로 database 조작
- org.springframework.boot:spring-boot-starter-data-redis : 캐시 저장소로 redis를 사용
- org.springframework.boot:spring-boot-starter-security : 로그인 및 웹 보안 기능
- org.springframework.boot:spring-boot-starter-web : 웹 프로젝트
- org.springframework.boot:spring-boot-starter-thymeleaf : 스프링 부트에서 타임리프 템플릿엔진 사용
- org.springframework.retry:spring-retry : 특정 메소드 호출에 대해 재시도 설정
- com.h2database:h2 : 로컬 환경에서 개발할 때 메모리 DB로 사용
- org.projectlombok:lombok : POJO 클래스에 필요한 getter, setter 등 자동 생성
- org.modelmapper:modelmapper : POJO 클래스 간에 맵핑
- com.fasterxml.jackson.datatype:jackson-datatype-jsr310 : LocalDataTime - jackson 맵핑을 위해 사용. jpa 등에서 필요함.
- it.ozimov:embedded-redis : 로컬 환경에서 개발할 때 임시 redis 서버로 사용

#### Server 제작 내용
- 사용자는 회원가입을 통해 아이디와 비번을 등록합
- 회원가입 후 사용자의 아이디와 비번으로 로그인
- 비밀번호는 암호화
- 키워드를 통해 책을 검색
- 검색 결과는 Pagination
- 검색 소스는 카카오 API / NAVER API
- 검색된 결과에서 원하는 책을 선택하여 상세 정보를 확인
- 상세 정보에는 제목, 도서 썸네일, 소개, ISBN, 저자, 출판사, 출판일, 정가, 판매가가 포함
- 나의 검색 히스토리(키워드, 검색 일시)를 최신 순으로 노출
- 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공(키워드 별로 검색된 횟수 포함)
- 재시도 : 카카오 API 호출에 실패하는 경우 2회 재시도 후 NAVER API 호출
- 캐싱 : 검색요청은 redis에 저장되어 동일 요청에 대해 캐싱 처리

### Client
#### Client 사용기술
- vue-cli : project scaffolding
- vue.js : frontend framework
- axios : HTTP client
- bootstrap : ui 컴포넌트 사용
- bootstrap-vue : vue.js에서 bootstrap 사용
