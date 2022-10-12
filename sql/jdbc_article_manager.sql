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

# article 데이터 추가
INSERT INTO article
SET
	regDate = NOW(),
	updateDate = NOW(),
	title = CONCAT("제목", RAND()),
	`body` = CONCAT("내용", RAND());

# meber 데이터 추가
INSERT INTO `member`
SET
	regDate = NOW(),
	updateDate = NOW(),
	loginID = CONCAT('TestID', RAND()),
	loginPw = CONCAT('TestPW', RAND()),
	`name` = CONCAT('TestNAME', RAND());

# INSERT INTO article(regDate, updateDate, title, `body`) VALUE(?, ?, ?, ?);

# article 조회
DESC article;
SELECT * FROM article ORDER BY id DESC;

# member 조회
DESC `member`;
SELECT * FROM `member`;

SELECT COUNT(loginId) > 0 FROM `member` WHERE loginId = 'a';
