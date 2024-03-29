# 스프링부트 JPA 블로그 V3

### 1. 의존성
- devtools
- spring web (mvc)
- mustache
- lombok
- jpa
- mysql
- security
- validation

### 2. DB설정
```sql
CREATE USER 'blue'@'%' IDENTIFIED BY 'blue1234';
CREATE DATABASE bluedb;
GRANT ALL PRIVILEGES ON bluedb.* TO 'blue'@'%';
```

### 3. 에디터
- https://quilljs.com/

### 4. 댓글
- https://livere.com/

### 5. 메뉴 - Drawer
- https://git.blivesta.com/drawer/


### 6. 주소 설계
```txt
localhost:8080/ (메인페이지 - 글 있는 곳 아님)
localhost:8080/user/{userId}/post
localhost:8080/user/{userId}/post/{postId}
localhost:8080/user/{userId}/category/{title}
```

### 7. 모델링
```sql
Visit
id
userId
totalCount
createDate
updateDate

User
id
username
password
createDate
updateDate

Post
id
title
content
thumbnail
userId
categoryId
createDate
updateDate

Love
id
postId
userId
createDate
updateDate

Category
id
title
userId
createDate
updateDate
```

### 8. 기능정리
- 카테고리 등록
- 글쓰기
- 글목록보기
- 페이징
- 글상세보기
- 검색
- 글삭제
- 글수정
- 회원정보 수정, 프로필 사진 변경
- 댓글 (라이브러리 사용)

- 로그관리 (Log4j)
- 단위테스트
- 배포
- AOP 처리


### Gradle depenency update
```txt
./gradlew --refresh-dependencie
```

### 페이징 참고
```sql
-- currentPage, totalPages
SELECT TRUE last FROM dual;

SELECT 
true LAST,
false FIRST,
3 size, 
0 currentPage,
(SELECT COUNT(*) FROM post WHERE userId = 1) totalCount,
(SELECT CEIL(COUNT(*)/3) FROM post WHERE userId = 1) totalPages,
p.*
FROM post p
ORDER BY p.id DESC
LIMIT 0, 3;
-- LIMIT (0*3), 3;
```