DROP DATABASE IF EXISTS library;
CREATE DATABASE library;
USE library;

CREATE TABLE `book_rent` (
	`rent_uid`	INT	NOT NULL	COMMENT 'Auto-Increment',
	`user_id`	VARCHAR(25)	NOT NULL,
	`rent_date`	DATETIME	NOT NULL	DEFAULT NOW(),
	`rent_status`	INT	NOT NULL	DEFAULT 0	COMMENT '{0 = 대여 중, 1 = 반납완료, 2 =연체중, 3 = 연체반납}',
	`rent_due`	DATETIME	NULL	COMMENT '대여일로부터 {n}일 후 (비즈니스 로직에서 결정)'
);

DROP TABLE IF EXISTS book_reservation;

CREATE TABLE book_reservation (
  reservation_uid INT NOT NULL AUTO_INCREMENT,
  book_uid INT NOT NULL,
  reservation_status INT NOT NULL DEFAULT 0,
  reservation_date DATETIME NOT NULL DEFAULT NOW(),
  reservation_due DATETIME NOT NULL,
  user_id VARCHAR(25) NOT NULL,
  PRIMARY KEY (reservation_uid)
);



CREATE TABLE `course` (
  `course_uid` INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
  `course_name` VARCHAR(25) NOT NULL,
  `course_open` INT NOT NULL DEFAULT 0 COMMENT '{0 = 닫힘, 1 = 신청 가능}',
  `course_graduate_date` DATETIME NOT NULL,
  PRIMARY KEY (`course_uid`)
);


CREATE TABLE `course_recommend` (
	`course_recommend_uid`	INT	NOT NULL	COMMENT 'Auto-Increment',
	`course_uid`	INT	NOT NULL,
	`book_uid`	INT	NOT NULL
);

CREATE TABLE `book_category` (
  category_uid INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
  category_name VARCHAR(25) NOT NULL,
  PRIMARY KEY (category_uid)
);

DROP TABLE IF EXISTS book;

CREATE TABLE book (
  book_uid INT NOT NULL AUTO_INCREMENT,
  book_name VARCHAR(50) NOT NULL,
  book_author VARCHAR(50) NOT NULL,
  book_publisher VARCHAR(50) NOT NULL,
  category_uid INT NOT NULL,
  category_uid2 INT NULL,
  category_uid3 INT NULL,
  book_store_date DATETIME NOT NULL,
  book_status INT NOT NULL DEFAULT 0 COMMENT '{0 = 대여 가능, 1 = 대여 중, 2 = 예약중}',
  PRIMARY KEY (book_uid)
);


CREATE TABLE `rent_detail` (
	`rent_detail_uid`	INT	NOT NULL	COMMENT 'Auto-Increment',
	`book_uid`	INT	NOT NULL,
	`rent_uid`	INT	NOT NULL,
	`rent_return_state`	INT	NOT NULL	DEFAULT 0	COMMENT '{0 = 미반납, 1 = 반납완료, 2 =연체중, 3 = 연체반납}',
	`rent_return_due`	DATETIME	NULL
);

CREATE TABLE `user` (
	`user_id`	VARCHAR(25)	NOT NULL,
	`user_password`	VARCHAR(128)	NOT NULL	COMMENT '추후 암호화 고려해 해시값 최대길이 고려한 길이 설정',
	`course_uid`	INT	NOT NULL,
	`user_status`	INT	NOT NULL	DEFAULT 0	COMMENT '{0 = 교육생, 1= 수료생}',
	`user_score`	INT	NOT NULL	DEFAULT 5	COMMENT '비즈니스 로직에서 결정',
    `user_name`  VARCHAR(50) NOT NULL
);


CREATE TABLE `user_favorite` (
  `user_favorite_id` INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
  `user_id` VARCHAR(25) NOT NULL,
  `category_uid` INT NOT NULL COMMENT 'Auto-Increment',
  PRIMARY KEY (`user_favorite_id`)
);


ALTER TABLE `book_rent` ADD CONSTRAINT `PK_BOOK_RENT` PRIMARY KEY (
	`rent_uid`
);

ALTER TABLE `book_reservation` ADD CONSTRAINT `PK_BOOK_RESERVATION` PRIMARY KEY (
	`reservation_uid`
);

ALTER TABLE `course` ADD CONSTRAINT `PK_COURSE` PRIMARY KEY (
	`course_uid`
);

ALTER TABLE `course_recommend` ADD CONSTRAINT `PK_COURSE_RECOMMEND` PRIMARY KEY (
	`course_recommend_uid`
);

ALTER TABLE `book_category` ADD CONSTRAINT `PK_BOOK_CATEGORY` PRIMARY KEY (
	`category_uid`
);

ALTER TABLE `book` ADD CONSTRAINT `PK_BOOK` PRIMARY KEY (
	`book_uid`
);

ALTER TABLE `rent_detail` ADD CONSTRAINT `PK_RENT_DETAIL` PRIMARY KEY (
	`rent_detail_uid`
);

ALTER TABLE `user` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
	`user_id`
);

ALTER TABLE `user_favorite` ADD CONSTRAINT `PK_USER_FAVORITE` PRIMARY KEY (
	`user_favorite_id`
);

ALTER TABLE `user_favorite`
ADD CONSTRAINT `FK_user_favorite_user`
FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`);

ALTER TABLE `user_favorite`
ADD CONSTRAINT `FK_user_favorite_category`
FOREIGN KEY (`category_uid`) REFERENCES `book_category`(`category_uid`);

select * from user;
DELETE FROM `user`;
select * from course;
DELETE FROM `course`;
select * from book_category;
DELETE FROM `book_category`;
select * from user_favorite;


INSERT INTO user (user_id, user_password, course_uid, category_uid, user_status, user_score,user_name) VALUES (?, ?, ?, ?, ?, ?,?);

INSERT INTO course (course_uid,course_name, course_open, course_graduate_date)
VALUES 
(1,'다크소울 만들기', 1, '2025-12-30'),
(null, '몬스터 헌터 만들기', 1, '2026-02-30'),
(null, '닌자 가이덴 만들기', 1, '2027-03-29');



INSERT INTO `book_category` (category_name)
VALUES
('JAVA'),
('C#'),
('C++'),
('Python'),
('JavaScript'),
('HTML/CSS'),
('SQL'),
('PHP'),
('Ruby'),
('Swift'),
('GoLang'),
('Kotlin'),
('Dart'),
('Rust'),
('Scala'),
('Perl'),
('Haskell'),
('Elixir'),
('Clojure');
