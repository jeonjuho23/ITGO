# ITGO
> ITGO는 중고거래 사이트의 게시글을 자동으로 스크래핑하여 하나의 모바일 애플리케이션으로 통합하는 서비스입니다.  
이곳 저장소는 ITGO 서비스의 애플리케이션 서버와 스크래퍼를 보여줍니다.  
스크래퍼는 독립적인 API로 구현되어 자동으로 중고거래 사이트에서 최신 게시글을 가져와 DB의 게시글 테이블의 스키마에 맞게 중복 없이 저장해줍니다.  
애플리케이션 서버는 사용자의 요청에 맞는 데이터를 DB에서 조회하여 응답해줍니다. 



<br/><br/>

## 기술 스택
- Server
  - Java 17
  - Spring Boot 3.1.4
  - Spring Data JPA
- Testing
  - Mockito
  - Junit 5
  - Postman
- Scrapper
  - Python 3
  - FastAPI
- DB
  - MySQL
  - MongoDB
- DevOps
  - AWS EC2 (Ubuntu)
  - AWS RDS
  - MongoDB Atlas
  - Nginx
  - Jenkins
  - Docker
  - Github
- Communication
  - Notion
  - Swagger
  

<br/><br/>

## Update (시간순)

- 2024
  - 배포
    - Nginx로 로드밸런싱하여 블루/그린 방식의 무중단 배포 파이프라인 구축
  - [API 명세서](https://iamjeonjuho.notion.site/API-Doc-bc06ae45699542618be24db7850c249d?pvs=74) 작성
  - 리팩터링
    - 적절한 HTTP 메서드와 URL 적용
    - 모든 응답 데이터를 json 객체 타입으로 반환하기 위해 공통 ResponseDTO<T>를 적용
    - 전역 예외처리 적용
  - 테스트 코드
    - 단위 테스트 코드 작성
      - Repository
      - Service
      - Controller
    - 단위 테스트 리팩터링
      - Controller
        - 상태 검증 -> 행위 검증
      - Service, Repository
        - Stub 객체를 생성하는 팩토리 클래스 사용

<br/><br/>

## Backend System Architecture
![SystemArchitecture](./image/SystemArchitecture.drawio.png)

<br/>

## Backend Deploy Architecture
![DeployArchitecture](./image/DeployArchitecture.drawio.png)

<br/>

## ERD
![ERD](./image/gul_erd.drawio.png)

<br/><br/>

## 구현 기능
> 제가 구현한 기능을 `표시`했습니다.

<br/>

#### 애플리케이션 서버
- 회원
  - 회원가입 
- `프로필`
  - 프로필 조회
  - 프로필 수정
- `기기`
  - 전체 기기 리스트 조회
  - 기기 카테고리 조회
  - 카테고리별 기기 리스트 조회
  - 모바일 기기 정보 조회
  - 노트북 기기 정보 조회
- `중고 게시글`
  - 전체 게시글 리스트 조회
  - 좋아요한 게시글 리스트 조회
  - 카테고리별 게시글 리스트 조회
  - 위치별 게시글 리스트 조회
  - 게시글 세부 조회
  - 게시글 검색
    - 키워드로 검색
    - 최근 검색 키워드 리스트 조회
- `관심`
  - 관심 기기 등록
  - 관심 게시글 등록
  - 관심 위치 등록
  - 관심 기기 삭제
  - 관심 게시글 삭제
  - 관심 위치 삭제
  - 관심 기기 리스트 조회
  - 관심 게시글 리스트 조회
  - 관심 위치 리스트 조회
  - 관심 기기 검색
  - 관심 위치 검색

<br/>

#### 중고 거래 게시글 자동 스크래퍼
- `중고 거래 게시글 스크래핑`
  - 게시글에서 GPT를 사용해 제품명 추출
  - 중복을 제외한 게시글 데이터베이스에 저장

<br/>
