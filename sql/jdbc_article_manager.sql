DROP DATABASE IF EXISTS jdbc_article_manager;
CREATE DATABASE jdbc_article_manager;

USE jdbc_article_manager;

CREATE TABLE article(
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title VARCHAR(200) NOT NULL,
	`body` TEXT NOT NULL
);

DESC article;
SELECT * FROM article;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT("제목", RAND()),
`body` = CONCAT("내용", RAND());

INSERT INTO article(regDate, updateDate, title, `body`) VALUE(NOW(), NOW(), CONCAT("제목", RAND()), CONCAT("내용", RAND()))