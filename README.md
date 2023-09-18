# YouTube API Data Fetcher

YouTube 데이터 수집기는 YouTube에서 원하는 키워드를 검색하고 검색 결과를 추출하여 비디오 및 채널 정보를 가져오는 프로그램입니다.

![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/5f32f4ee-5c3c-4fed-888e-0837b6149024)
![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/4761cc94-615f-49c0-97c7-0633353854d6)
![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/b5d729dc-199d-447a-ae98-efa519f6dcf1)


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
![image](https://github.com/KathleenJung/youtube_api_data_fetcher_db/assets/85939045/583609ff-6b37-4db9-bb34-36e9d1d12f1c)

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
