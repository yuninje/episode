# [Project3] Episode
> 웹 에디터 기반 소설 작성 플랫폼 :: 에피소드

<p align="center"><img src="https://i.imgur.com/CmGVVR8.png"> </p>

<a href="http:k02a2061.p.ssafy.io" target="_blank"> Demo link </a>

## Project Info
### R&R
- Front-end : 김동훈, 박신원
- Back-end : 김민경, 윤인제
- PM, UI/UX : 백창현

### Development environment
- Front-end : Vue, Nginx
- Back-end : Spring Boot ^2.6.11 (Maven), Lombok, JPA
- DB : mysql 8.0.12 - Docker
- ERD : ERD Cloud
- IDE : STS 3.9.7, VS code
- Language : Java 1.8
- OS : Ubuntu 18.04
- Storage : AWS S3

## Folder organization
```
backend : 백엔드 관련 코드
frontend : 프론트엔드 관련 코드
```
---
## Getting Started

```
Front-end

> git clone https://lab.ssafy.com/s02-final/s02p31a206.git

> cd frontend

> npm install

> npm run serve

Back-end

# Create maven jar file with the following command & Run.

> "c:\gitlab\project3\s02p31a206\backend\mvnw.cmd" install -f "c:\gitlab\project3\s02p31a206\backend\pom.xml"

> sudo java -jar "builded file name" &

```

## Release Note

# Version 1.0 (2020.05.22)
## 주요 기능
- 웹 에디터 페이지 : 프로그래스바, 스탑워치, 글자수 체크, 자동 저장
- 메인 페이지 : NAV 바, 상단 배너, 우측 광고 배너, TOP100 표시, 느낌별 소설 표시, 실시간 검색어 표시
- 소설 관리 페이지 : 작성중 소설 전체 보기, 소설 생성, 소설 상세정보
- 소설 뷰어 페이지 : 소설 보기, 우클릭 방지, f12 방지
- 로그인, 회원가입 : 로그인, 회원가입, 유효성 체크

# Version 1.1 (2020.05.28)
## 주요 기능
- 검색 : 작품, 작가 검색, 실시간 검색어 API 연동
- 소설 관리 페이지 : 소설 에피소드 생성, 소설 삭제

# Version 1.2 (2020.06.04)
## 주요 기능
- 소설 관리 페이지 : 캐릭터 생성, 소설 세부 설정
- 메인 페이지 : 메인 페이지 디자인 변경
- 인물 관계 : 인물 관계 생성 기능 1차 배포

# Version 2.0 (2020.06.08)
## 주요 기능
- 캐릭터 셋 : 캐릭터 모음집, 내 소설로 캐릭터 불러오기
- 인물 관계 : 인물 관계 생성 기능 최종 배포
- 소설 뷰어 : 드래그 방지