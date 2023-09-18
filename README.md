# YouTube API Data Fetcher

YouTube 데이터 수집기는 YouTube에서 원하는 키워드를 검색하고 검색 결과를 추출하여 비디오 및 채널 정보를 가져오는 프로그램입니다.

![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/8a49f9ec-e62c-4f3c-89cf-49441bd72c04)
![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/9c4de766-9396-484a-8cda-5971d829c585)
![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/f78643c8-8f6c-434c-b69a-2f1c5d3a85f8)

## 개발 환경

- Java 17
- Spring Boot 3.1.3
- IntelliJ IDEA 2023

## 종속성

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web:3.1.3'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	implementation 'org.postgresql:postgresql:42.6.0'
	implementation 'com.googlecode.json-simple:json-simple:1.1.1'
}
```

## 폴더 구조

![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/fbfda6e6-606b-4db3-bcb4-023f6b61fcc4)

## 사용 방법

1. Postgres DB를 생성합니다.  
  - [[postgreSQL] 데이터베이스 생성, 테이블 생성 & 데이터 입력](https://benn.tistory.com/28)
2. `config` 폴더를 만듭니다.
3. `config` 폴더 내에 `keywords.txt` 파일을 생성하고 수집하려는 키워드를 줄바꿈으로 나누어 나열합니다.
   - 이때, 파일명은 DB에 적재시 `client` 컬럼에 들어가게 됩니다.
4. `application.properties` 파일에 DB 정보와 하나 이상의 Youtube API Key를 추가합니다.
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/[DB NAME]
   spring.datasource.username=[USER ID]
   spring.datasource.password=[USER PWD]

   api_key1=[YOUR API KEY 1]
   api_key2=[YOUR API KEY 2]
   api_key3=[YOUR API KEY 3]
   # 필요한 경우 더 많은 API 키를 추가하세요.
   ```
5. `DatafetcherApplication`를 실행합니다.
