use library;

--------------------------------------------------------------------------------
-- 조회용 쿼리
select * from book;
select * from book_category;
select * from book_rent;
select * from book_reservation;
select * from course;
select * from course_recommend;
select * from rent_detail;
select * from user;

--------------------------------------------------------------------------------
-- truncate 체크 일시적으로 끄기
SET FOREIGN_KEY_CHECKS = 0;
-- truncate 체크 다시 켜기
-- SET FOREIGN_KEY_CHECKS = 1;

-- 카테고리 샘플 데이터 입력
truncate table book_category;
insert into book_category (category_name) value ('JAVA'); -- 첫 데이터 입력
insert into book_category (category_name) value ('웹 프로그래밍');
insert into book_category (category_name) value ('임베디드 프로그래밍');
insert into book_category (category_name) value ('디자인 패턴');
insert into book_category (category_name) value ('JavaScript');
insert into book_category (category_name) value ('리액트');
insert into book_category (category_name) value ('스프링 프레임워크');
insert into book_category (category_name) value ('SQL');
insert into book_category (category_name) value ('코딩 테스트');
insert into book_category (category_name) value ('C언어');
insert into book_category (category_name) value ('프론트엔드');
insert into book_category (category_name) value ('백엔드');

--------------------------------------------------------------------------------
-- 테이블 데이터타입 수정
alter table book modify book_name varchar(255); -- 책 이름으로 엄청 긴 게 많아서 50자가 모자랄 수도 있다 넉넉하게 확장

--------------------------------------------------------------------------------
-- 도서 샘플 정보 입력
truncate table book;
insert into book 
(book_name, book_author, book_publisher, category_uid, category_uid2, category_uid3, book_store_date, book_status) value 
('java의 정석', '남궁성', '도우출판',1,null,null,now(),0),
('이것이 자바다', '남궁성', '도우출판',1,null,null,now(),0),
('자바 코드의 품질을 높이는 100가지 방법', '타기르 발레예프', '한빛미디어',1,null,null,now(),0),
('코딩은 처음이라 with 자바', '유동환', '영진닷컴',1,null,null,now(),0),
('패턴으로 익히고 설계로 완성하는 리액트', '준타오 추', '한빛미디어',6,2,11,now(),0),
('모던 리액트 Deep Dive', '김용찬', '위키북스',6,2,11,now(),0),
('리액트의 정석', '이창현', '길벗캠퍼스',6,2,11,now(),0),
('HTML5 웹 프로그래밍 입문 - 4판', '윤인성', '한빛아카데미',2,11,null,now(),0),
('Do it! HTML5 + CSS3 웹 표준의 정석 - 전면 개정 2판', '고경희', '이지스퍼블리싱',2,11,null,now(),0),
('HTML5 + CSS3 + JavaScript로 배우는 웹프로그래밍 기초', '천인국', '인피니티북스',2,5,11,now(),0),
(' Head First JavaScript ', '마이클 모리슨', '한빛미디어',5,2,null,now(),0),
('초보자를 위한 JavaScript 200제 ', '남궁성', '도우출판',5,null,null,now(),0),
('임베디드 리눅스 프로그래밍 완전정복 3/e ', '프랭크 바르케스', '에이콘출판',3,null,null,now(),0),
('라즈베리파이로 시작하는 핸드메이드 IoT : 직접 체험하며 배우는 임베디드 IoT 프로그래밍', '임근식', '비제이퍼블릭',3,null,null,now(),0),
('규칙으로 배우는 임베디드 시스템 : 회로 설계 및 PCB 설계 규칙 - 개정 2판', '장선웅', '북랩',3,null,null,now(),0),
('규칙으로 배우는 임베디드 시스템 : 기초 DSP와 제어 시스템', '장선웅', '북랩',3,null,null,now(),0),
('혼자 공부하는 C 언어 - 1:1 과외하듯 배우는 프로그래밍 자습서, 개정판', '서현우', '한빛미디어',10,null,null,now(),0),
('윤성우의 열혈 C 프로그래밍 - 개정판', '윤성우', '오렌지미디어',10,null,null,now(),0),
('난 정말 C Programming을 공부한적이 없다구요!', '윤성우', '오렌지미디어',10,null,null,now(),0),
('클린 코드 Clean Code - 애자일 소프트웨어 장인 정신', '남궁성', '도우출판',4,null,null,now(),0),
('헤드 퍼스트 디자인 패턴 - 14가지 GoF 필살 패턴! 유지 관리가 편리한 객체지향 소프트웨어를 만드는 법, 개정판 ', '에릭 프리먼', '한빛미디어', 4, null, null, now(), 0),
('GOF의 디자인 패턴 - 재사용성을 지닌 객체지향 소프트웨어의 핵심 요소, 개정판 ', '에릭 감마', '프로텍미디어', 4, null, null, now(), 0),
('자바 객체지향 디자인 패턴 - UML과 GoF 디자인 패턴 핵심 10가지로 배우는', '채흥석,정인상', '한빛미디어', 4, 1, null, now(), 0),
('스프링 프레임워크 첫걸음 - 기초 이론부터 웹 애플리케이션 제작까지, 그림과 실습으로 배우는 스프링 프레임워크 완벽 입문서, 개정판', '키노시타 마사아키', '위키북스', 7, 2, 12, now(), 0),
('짧고 굵게 배우는 JSP 웹 프로그래밍과 스프링 프레임워크 ', '황희정', '한빛아카데미', 7, 2, 12, now(), 0),
('배워서 바로 쓰는 스프링 프레임워크 - AOP, 보안, 메시징부터 스프링 웹 MVC까지 실용적인 예제로 한눈에 알아보는 스프링 가이드 ', '애시시 사린,제이 샤르마', '한빛미디어', 7, 2, 12, now(), 0),
('Vue 3와 스프링 부트로 시작하는 웹 개발 철저 입문 - 스프링 부트와 Vue 3를 활용한 실습 중심의 풀스택 웹 애플리케이션 개발', '', '', 2, 7, null, now(), 0),
('혼자 공부하는 SQL - 1:1 과외하듯 배우는 데이터베이스 자습서', '우재남', '한빛미디어', 8, null, null, now(), 0),
('칼퇴족 김 대리는 알고 나만 모르는 SQL 기초편 - 개정판 ', '김지훈', '책밥', 8, null, null, now(), 0),
('2025 시대에듀 유선배 SQL개발자(SQLD) 과외노트', '정미나', '시대에듀', 8, null, null, now(), 0),
('SQL 첫걸음 - 하루 30분 36강으로 배우는 완전 초보의 SQL 따라잡기 ', '아사이 아츠시', '한빛미디어', 8, null, null, now(), 0),
('이것이 취업을 위한 코딩 테스트다 with 파이썬', '나동빈', '한빛미디어', 9, null, null, now(), 0),
('알고리즘 문제 해결 전략 1권', '구종만', '인사이트', 9, null, null, now(), 0),
('알고리즘 문제 해결 전략 2권', '구종만', '인사이트', 9, null, null, now(), 0),
('코딩 테스트 합격자 되기 : 자바스크립트 편', '이선협,박경록', '골든래빗(주)', 9, 5, null, now(), 0),
('취업과 이직을 위한 프로그래머스 코딩 테스트 문제 풀이 전략 : 자바 편', '김현이', '길벗', 9, 1, null, now(), 0),
('Do it! 알고리즘 코딩 테스트 - 자바 편', '김종관', '한빛미디어', 9, 1, null, now(), 0),
('Node.js 백엔드 개발자 되기', '박승규', '골든래빗(주)', 2, 12, null, now(), 0),
('백엔드 입문자를 위한 모던 자바스크립트&Node.js', '이창현', '길벗캠퍼스', 2, 12, null, now(), 0);
 
-- 샘플 과정 추가
truncate table course;
insert into course (course_name, course_open, course_graduate_date) value 
('Java 기반 풀스택 개발자 양성과정 1기', 0, '2025-01-29 00:00:00'), -- 과거 날짜인 과정
('임베디드 개발자 양성과정', 1, '2025-09-29 00:00:00'),
('Java 기반 풀스택 개발자 양성과정 2기', 1, '2025-09-29 00:00:00'),
('스프링 백엔드 단기 완성 과정 1기', 1, '2025-04-29 00:00:00'),
('리액트 완전 고수되기 과정 1기', 0, '2025-03-15 00:00:00'), -- 과거 날짜인 과정
('코딩테스트 완전정복 취업 뽀개기 과정 1기', 0, '2025-03-15 00:00:00'), -- 과거 날짜인 과정
('리액트 완전 고수되기 과정 2기', 1, '2025-06-15 00:00:00'),
('코딩테스트 완전정복 취업 뽀개기 과정 2기', 1, '2025-06-15 00:00:00');

--------------------------------------------------------------------------------

-- 과정별 추천도서 데이터입력

select * from course_recommend;
truncate table course_recommend;
-- Java 기반 풀스택 개발자 양성과정 1기 (course_uid = 1)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(1, 1),  -- java의 정석
(1, 2),  -- 이것이 자바다
(1, 3),  -- 자바 코드의 품질을 높이는 100가지 방법
(1, 23), -- 스프링 프레임워크 첫걸음
(1, 24); -- JSP + 스프링

-- 임베디드 개발자 양성과정 (course_uid = 2)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(2, 13), -- 임베디드 리눅스 프로그래밍 완전정복
(2, 14), -- 라즈베리파이로 시작하는 핸드메이드 IoT
(2, 15), -- 회로 설계 규칙
(2, 16), -- 기초 DSP와 제어 시스템
(2, 17); -- 혼자 공부하는 C 언어

-- Java 기반 풀스택 개발자 양성과정 2기 (course_uid = 3)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(3, 1),  -- java의 정석
(3, 2),  -- 이것이 자바다
(3, 3),  -- 자바 코드의 품질을 높이는 100가지 방법
(3, 23), -- 스프링 프레임워크 첫걸음
(3, 24); -- JSP + 스프링

-- 스프링 백엔드 단기 완성 과정 1기 (course_uid = 4)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(4, 23), -- 스프링 프레임워크 첫걸음
(4, 24), -- JSP + 스프링
(4, 25), -- 실용적인 스프링 가이드
(4, 3),  -- 자바 코드의 품질을 높이는 100가지 방법
(4, 22); -- 디자인 패턴 핵심 10가지

-- 리액트 완전 고수되기 과정 1기 (course_uid = 5)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(5, 4),  -- 코딩은 처음이라 with 자바
(5, 5),  -- 패턴으로 익히는 리액트
(5, 6),  -- 모던 리액트 Deep Dive
(5, 7),  -- 리액트의 정석
(5, 11); -- 초보자를 위한 JavaScript 200제

-- 코딩테스트 완전정복 취업 뽀개기 과정 1기 (course_uid = 6)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(6, 27), -- 이것이 취업을 위한 코딩 테스트다
(6, 28), -- 알고리즘 문제 해결 전략 1권
(6, 29), -- 알고리즘 문제 해결 전략 2권
(6, 30), -- 자바스크립트 편
(6, 31), -- 자바 편 문제 풀이 전략
(6, 32); -- Do it! 알고리즘 코딩 테스트

-- 리액트 완전 고수되기 과정 2기 (course_uid = 7)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(7, 5),  -- 패턴으로 익히는 리액트
(7, 6),  -- 모던 리액트 Deep Dive
(7, 7),  -- 리액트의 정석
(7, 9),  -- HTML5 + CSS3 웹 표준의 정석
(7, 10); -- JS + HTML5 웹프로그래밍 기초

-- 코딩테스트 완전정복 취업 뽀개기 과정 2기 (course_uid = 8)
INSERT INTO course_recommend (course_uid, book_uid) VALUES
(8, 27), -- 이것이 취업을 위한 코딩 테스트다
(8, 28), -- 알고리즘 문제 해결 전략 1권
(8, 29), -- 알고리즘 문제 해결 전략 2권
(8, 30), -- 자바스크립트 편
(8, 31), -- 자바 편 문제 풀이 전략
(8, 32); -- Do it! 알고리즘 코딩 테스트
--------------------------------------------------------------------------------
-- truncate 체크 다시 켜기
SET FOREIGN_KEY_CHECKS = 1;
select * from course;
select * from course_recommend;



