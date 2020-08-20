

![버전](https://img.shields.io/badge/%20version-1.0-green)![node버전](https://img.shields.io/badge/node-12.18.2-green)

## 🚀Demo Link https://ssalog.gq/

## 프로젝트 요약

> **알고리즘 풀이를 정리하고 공유하는 기술 블로그**
>
> SSALOG 는 알고리즘 풀이 정리를 도와주는 것에 그치지 않고, 
>
> 더 나아가 다른 사람들과 풀이를 공유하고 함께 공부하는 기능을 제공하고 있습니다. 
>
> 지금 당장 SSaLog 하세요
>
> SSALOG 는 SSAFY + ALGORITHM + BLOG 의 합성어입니다

# 프로젝트 정보

## Install & Run

- 😘Front-End

```jsx
cd ssalog_front
npm install
npm run serve
```

- 🏗Back-End

```jsx
cd ssalog_backend
mvn package
cd target
java -jar SSalog_ver1.0-0.0.1-SNAPSHOT.jar
```

## Required

- Maven (Build tool)
- Git
- Java
- Mysql
- MongoDB
- Redis

## 📒 Tech Log

<details>
<summary>1주차 - 서비스 기획 + 개발 세팅 + 브랜치 전략 ( 7/9 ~ 7/12)</summary>

기획 ,  어떤 아이템으로 정할 것인가?

- **SSalog (알고리즘 풀이 정리 블로그)**

  ```
   - 기술 블로그라는 주제에 적합
  
   - SSAFY생들을 타겟으로 배포를 통해, 직접적인 유저층을 확보하기 쉽다.
  
   - 기존에 존재하지 않던 서비스라서 참신함을 느낄 수 있음
  
   - Vue를 이용한 프론트 화면 개발과 Rest api 서버를 구현해 적용가능
  
   - ChromeExtension 영역을 공부해 볼 수 있음
  ```

- **개발 세팅 (개발 도구, 개발 환경)**

  - 개발 도구 : VSCode, Spring STS, Intellij
  - 개발 환경 : AWS server, ubuntu

- **이슈 관리 도구, CI 도구**

  ```
   - 이슈 관리 도구 : Git Lab 프로젝트, Jira, MatterMost
  
   -  CI 도구 : jenkins
  ```

- **브랜치 전략**

  참고 : ( https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)

</details>

<details>
<summary>2주차 -  SSalog ver 1.0 pre.ver 개발 ( 7/13 ~ 7/19)</summary>

명세서 작성 및 기능 구현 

- **명세서 작성 ( 참고 : 명세서 1.2.docx )**

 2주차에 개발할 내용

      - 로그인

      - 회원가입

      - MyPage 설정

      - Chrome extension 개발

- **front-side**

      - Vue.js와 Vuetify를 활용한, SPA 구조로 개발

      - 로그인 페이지 단 구상 및 component 구현

 - 회원가입 페이지 단 구상 및 component 구현

 - Mypage 단 구상 및 component 구현

 - 각 페이지에서 필요한 데이터를 Rest api가 주는 곳으로 연결 

- **server-side**

       - jwt 인증방식을 이용한 로그인 구현

       - Spring Security와 JPA를 이용해 로그인, 회원가입 과정 구현

       - 파일 업로드 로직 구현

- **ChromeExtension**

       - JavaScript를 이용해 backjoon online judge에서 원하는 정보 얻어오기 구현

// SUB-PJT l 종료 (2020.07.13 ~ 2020.07.17 (1주)

</details>

<details>
<summary>3주차 -  SSalog ver 1.0 개발 ( 7/20 ~ 7/26)</summary>

기능 구현 및 배포를 위한 작업 진행

 3주차에 개발할 내용

      - 게시물 포스팅

      -  aws 서버 환경설정 및 필요한 툴 설치

      - Chrome Extension 마무리

      - Editor 구현

- **front-side**

      - 게시물 관련 페이지 단 구상 및 component 구현

 - 서비스의 논리적 흐름대로 페이지들을 연결

 - Chrome extension에서 넘어온 정보를 사용하기 위해 Rest api 와 통신

 -  tiptap 오픈 소스를 활용해 editor기능 customizing

- **server-side**

       - 게시물 포스팅 CRUD 구현

       - 게시물 검색 기능 구현 ( 조건별 검색, 회원 검색, 키워드 검색 등)

       - AWS 서버 환경설정 및 필요한 툴 설치

- **ChromeExtension**

      - 유의미한 데이터를 가공해 Rest api와 연결

      - 에러 처리와 로직 꼼꼼하게 구현

</details>

<details>
<summary>4주차 -  SSalog ver 1.0 개발 ( 7/27 ~ 8/2)</summary>

기능 구현 및 배포를 위한 작업 진행

 4주차에 개발할 내용

   - front단과 back단 서비스 배포

   - Editor 구현

   - 중간 발표 준비 (ppt ,시연동영상 제작)

- **front-side**

      - Editor 기초 기능 구현 ( 버그 수정 필요 )

 - 중간 발표에 사용할 시연동영상 촬영을 위해 페이지 연결과 서버와 통신

 - page 단 디자인 작업 진행

 - 자잘한 에러 수정

- **server-side**

       - jenkins를 이용한 자동 배포 구현

       - NGINX를 프록시 서버로 구현

       - Domain 주소 설정 및 ssl 인증 방식 구현

       - exception처리 및 에러 수정

- 공통

    - 중간 발표준비를 위한 ppt 제작

    - 시연 동영상에 필요한 핵심기능 개발

// SUB-PJT ll 종료 (2020.07.20 ~ 2020.07.31 (2주)

</details>

## 📚[Documentation](https://www.notion.so/SSAFY-2-1-2afd33bdfb9d4831af618d4ab914a0ea)

## 🗃Project Folder

```
📁Redis
📁Mongo
📁mysql
📁ChromeExtension
📁client
├── 📁src
│   ├── App
│   ├── 📁assets
│   ├── 📁components
│   ├── 📁plugins
│   ├── 📁components
│   ├── 📁store(Vuex)
│   ├── 📁Router
│   ├── 📁utils
│   └── 📁views
│       ├── 📁Account
│       ├── 📁Problem
│       ├── 📁Search
│       └── 📁SSALOG
│ 
📁server
├── 📁src
│   ├── 📁main
│	│	├── 📁java
│	│	│	├── 📁Application
│	│	│	├── 📁config
│	│	│	├── 📁controller
│	│	│	├── 📁dto
│	│	│	├── 📁jwt
│	│	│	├── 📁repository
│	│	│	└── 📁service
│	│	└── 📁resources
│	└── 📁test
│       ├── 📁java
│	    └── 📁resources
└── pom.xml
```

## 🔧 Tech Stack

 ![기술스택](./ReadmeImg/TechStack.png)

## 👨‍👩‍👦‍👦 Author

### SSAFY 2학기 1차 프로젝트

🤠 공필상 : 풀스택 담당, 프로젝트 일정 조율

💀 정성오 : 백엔드 개발, DB 총괄

😜 김인호 : 프론트 개발, 크롬 확장프로그램 담당

🤓 김태인 : 프론트 개발, 디자인