DROP DATABASE IF EXISTS library;
CREATE DATABASE library;
USE library;

CREATE TABLE `book_rent` ( -- 대여 테이블
	`rent_uid`	int	NOT NULL COMMENT 'Auto-Increment',
	`user_id`	VARCHAR(25)	NOT NULL,
	`rent_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`rent_status`	INT	NOT NULL DEFAULT 0 COMMENT '{0 = 대여 중, 1 = 반납완료, 2 =연체중, 3 = 연체반납}',
	`rent_due`	DATETIME	NULL COMMENT '대여일로부터 {n}일 후 (비즈니스 로직에서 결정)',
	PRIMARY KEY (`rent_uid`)
);

CREATE TABLE `course` ( -- 과정 테이블
	`course_uid`	INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
	`course_name`	VARCHAR(25)	NOT NULL,
	`course_open`	INT	NOT NULL DEFAULT 0 COMMENT '{0 = 닫힘, 1 = 신청 가능}',
	`course_graduate_date`	DATETIME	NOT NULL,
	PRIMARY KEY (`course_uid`)
);

CREATE TABLE `user` ( -- 회원 테이블
	`user_id`	VARCHAR(25)	NOT NULL,
	`user_password`	VARCHAR(128)	NOT NULL COMMENT '추후 암호화 고려해 해시값 최대길이 고려한 길이 설정',
    `user_name` VARCHAR(50) NULL,
	`course_uid`	INT	NOT NULL,
	`category_uid`	INT	NULL DEFAULT NULL COMMENT '초기값은 null, 회원가입 시 혹은 마이페이지에서 설정 가능',
	`user_status`	INT	NOT NULL DEFAULT 0 COMMENT '{0 = 교육생, 1= 수료생}',
	`user_score`	INT	NOT NULL DEFAULT 5 COMMENT '비즈니스 로직에서 결정',
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `rent_detail` ( -- 대여 상세 테이블
	`rent_detail_uid`	INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
	`book_uid`	INT	NOT NULL,
	`rent_uid`	VARCHAR(25)	NOT NULL,
	`rent_return_state`	INT	NOT NULL DEFAULT 0 COMMENT '{0 = 미반납, 1 = 반납완료, 2 =연체중, 3 = 연체반납}',
	`rent_return_due`	DATETIME	NULL,
	PRIMARY KEY (`rent_detail_uid`)
);

CREATE TABLE `book_reservation` ( -- 예약 테이블
	`reservation_uid`	INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
	`book_uid`	INT	NOT NULL,
	`reservation_status`	INT	NOT NULL DEFAULT 0 COMMENT '{0 = 예약 중, 1 = 기간 만료, 2 = 정상 대여, 3 = 예약 취소}}',
	`reservation_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`reservation_due`	DATETIME	NOT NULL COMMENT '예약일로부터 {n}일 후 (비즈니스 로직에서 결정)',
	`user_id`	VARCHAR(25)	NOT NULL,
	PRIMARY KEY (`reservation_uid`)
);

CREATE TABLE `book_category` ( -- 도서 카테고리 테이블
	`category_uid`	INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
	`category_name`	VARCHAR(25)	NOT NULL,
	PRIMARY KEY (`category_uid`)
);

CREATE TABLE `book` ( -- 도서 테이블
	`book_uid`	INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
	`book_name`	VARCHAR(50)	NOT NULL,
	`book_author`	VARCHAR(50)	NOT NULL,
	`book_publisher`	VARCHAR(50)	NOT NULL,
	`category_uid`	INT	NOT NULL,
	`category_uid2`	INT	NULL,
	`category_uid3`	INT	NULL,
	`book_store_date`	DATETIME	NOT NULL,
	`book_status`	INT	NOT NULL DEFAULT 0 COMMENT '{0 = 대여 가능, 1 = 대여 중}',
	PRIMARY KEY (`book_uid`)
);

CREATE TABLE `course_recommend` ( -- 과정별 추천도서 테이블
	`course_recommend_uid`	INT NOT NULL AUTO_INCREMENT COMMENT 'Auto-Increment',
	`course_uid`	INT	NOT NULL,
	`book_uid`	INT	NOT NULL,
	PRIMARY KEY (`course_recommend_uid`)
);

-- 외래 키 제약 조건 설정

ALTER TABLE `book_rent` ADD CONSTRAINT `FK_user_TO_book_rent_1`
FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

ALTER TABLE `user` ADD CONSTRAINT `FK_course_TO_user_1`
FOREIGN KEY (`course_uid`) REFERENCES `course` (`course_uid`);

ALTER TABLE `user` ADD CONSTRAINT `FK_book_category_TO_user_1`
FOREIGN KEY (`category_uid`) REFERENCES `book_category` (`category_uid`);

ALTER TABLE `rent_detail` ADD CONSTRAINT `FK_book_TO_rent_detail_1`
FOREIGN KEY (`book_uid`) REFERENCES `book` (`book_uid`);

ALTER TABLE `rent_detail` ADD CONSTRAINT `FK_book_rent_TO_rent_detail_1`
FOREIGN KEY (`rent_uid`) REFERENCES `book_rent` (`rent_uid`);

ALTER TABLE `book_reservation` ADD CONSTRAINT `FK_book_TO_book_reservation_1`
FOREIGN KEY (`book_uid`) REFERENCES `book` (`book_uid`);

ALTER TABLE `book_reservation` ADD CONSTRAINT `FK_user_TO_book_reservation_1`
FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

ALTER TABLE `book` ADD CONSTRAINT `FK_book_category_TO_book_1`
FOREIGN KEY (`category_uid`) REFERENCES `book_category` (`category_uid`);

ALTER TABLE `book` ADD CONSTRAINT `FK_book_category_TO_book_2`
FOREIGN KEY (`category_uid2`) REFERENCES `book_category` (`category_uid`);

ALTER TABLE `book` ADD CONSTRAINT `FK_book_category_TO_book_3`
FOREIGN KEY (`category_uid3`) REFERENCES `book_category` (`category_uid`);

ALTER TABLE `course_recommend` ADD CONSTRAINT `FK_course_TO_course_recommend_1`
FOREIGN KEY (`course_uid`) REFERENCES `course` (`course_uid`);

ALTER TABLE `course_recommend` ADD CONSTRAINT `FK_book_TO_course_recommend_1`
FOREIGN KEY (`book_uid`) REFERENCES `book` (`book_uid`);

select * from user;
DELETE FROM user;

select * from course;
DELETE FROM course;

SELECT * FROM `book_category`;
DELETE FROM book_category;

INSERT INTO user (user_id, user_password, user_name, course_uid, category_uid, user_status, user_score) VALUES (?, ?, ?, ?, ?, ?, ?);

INSERT INTO course (course_uid, course_name, course_open, course_graduate_date)
VALUES 
(null, '다크소울 만들기', 1, '2025-12-30'),
(null, '몬스터 헌터 만들기', 1, '2026-02-23'),
(null, '닌자 가이덴 만들기', 1, '2027-03-29');

INSERT INTO `book_category` (category_name)
VALUES
('JAVA'),
('C#'),
('C++');


