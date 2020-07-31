# SSALOG 소개

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

1주차 - 서비스 기획 + 개발 세팅 + 브랜치 전략 ( 7/9 ~ 7/17)

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

  [참고문서](https://woowabros.github.io/experience/2017/10/30/baemin-mobile-git-branch-strategy.html)

2주차 -  ( 7/9 ~ 7/17)

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
│   └── 📁main
│	│	├── 📁java
│	│	│		├── 📁Application
│	│	│		├── 📁config
│	│	│		├── 📁controller
│	│	│		├── 📁dto
│	│	│		├── 📁jwt
│	│	│		├── 📁repository
│	│	│		└── 📁service
│	│	└── 📁resources
│	└── 📁test
│       ├── 📁java
│	    └── 📁resources
└── pom.xml
```

## 🔧 Tech Stack

 사진 넣어주세요 ㅠㅠㅠㅠ

## 👨‍👩‍👦‍👦 Author

### SSAFY 2학기 1차 프로젝트

🤠 공필상 : 풀스택 담당, 프로젝트 일정 조율

💀 정성오 : 백엔드 개발, DB 총괄

😜 김인호 : 프론트 개발, 크롬 확장프로그램 담당

🤓 김태인 : 프론트 개발, 디자인