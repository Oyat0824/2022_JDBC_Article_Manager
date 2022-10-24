# 아티클 매니저 생성 쿼리문

# DB 삭제 / 생성 / 선택
DROP DATABASE IF EXISTS jdbc_article_manager;
CREATE DATABASE jdbc_article_manager;
USE jdbc_article_manager;

# article 테이블 생성
CREATE TABLE article(
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title VARCHAR(200) NOT NULL,
	`body` TEXT NOT NULL
);

# member 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId VARCHAR(20) NOT NULL,
	loginPw VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL
);

# article 테이블에 회원 번호 컬럼 추가, updateDate 컬럼 오른쪽에 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

# article 테이블에 조회수 컬럼 추가
ALTER TABLE article ADD COLUMN hit INT UNSIGNED NOT NULL DEFAULT 0;

# article 조회
DESC article;
SELECT * FROM article ORDER BY id DESC;

# member 조회
DESC `member`;
SELECT * FROM `member`;

# article 데이터 추가
INSERT INTO article
SET
	regDate = NOW(),
	updateDate = NOW(),
	memberId = 1,
	title = "제목 1",
	`body` = "내용 1",
	hit = 5;

INSERT INTO article
SET
	regDate = NOW(),
	updateDate = NOW(),
	memberId = 2,
	title = "제목 2",
	`body` = "내용 2";
	
INSERT INTO article
SET
	regDate = NOW(),
	updateDate = NOW(),
	memberId = 2,
	title = "제목 3",
	`body` = "내용 3";
	
INSERT INTO article
SET
	regDate = NOW(),
	updateDate = NOW(),
	memberId = 1,
	title = "제목 4",
	`body` = "내용 4";

# meber 데이터 추가
INSERT INTO `member`
SET
	regDate = NOW(),
	updateDate = NOW(),
	loginID = 'test1',
	loginPw = 'test1',
	`name` = '박명수';
	
INSERT INTO `member`
SET
	regDate = NOW(),
	updateDate = NOW(),
	loginID = 'test2',
	loginPw = 'test2',
	`name` = '유재석';

# 이너 조인 연습
SELECT a.*, m.name AS writerName
FROM article AS a INNER JOIN `member` AS m ON a.memberId = m.id
WHERE a.id = 2;

# 업데이트 연습
UPDATE article
SET hit = hit + 1
WHERE id = 1;



# meber 데이터 추가
INSERT INTO `member`
SET
	regDate = NOW(),
	updateDate = NOW(),
	loginID = CONCAT('TestID', RAND()),
	loginPw = CONCAT('TestPW', RAND()),
	`name` = CONCAT('TestNAME', RAND());
	
# INSERT INTO article(regDate, updateDate, title, `body`) VALUE(?, ?, ?, ?);

# 특정 부분 검색
SELECT COUNT(loginId) > 0 FROM `member` WHERE loginId = 'test1';
SELECT loginPw FROM `member` WHERE loginId = 'test1';
