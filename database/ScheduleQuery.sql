-- 스케줄 쿼리 등록전 체크
SHOW variables LIKE 'event%'; -- event_scheduler 상태 ON인지 확인
set global event_scheduler = on; -- (권한 필요) event_scheduler 상태 on으로 변경 -- AWS RDS는 이 쿼리로 설정을 못 바꾼다. RDS 메뉴의 파라미터 그룹에서 바꿔줘야 한다.
select * from information_schema.events; -- 등록된 이벤트 목록 확인

SELECT NOW() AS '현재 시각', -- 타임존 확인용 쿼리... aws 리전 따라 자동세팅되니까 aws쓰는동안은 신경안써도 된다. 초기값 세팅할때만 확인해도됨
       @@global.time_zone AS '전역 타임존',
       @@session.time_zone AS '세션 타임존',
       @@system_time_zone AS '시스템 타임존';

-------------------------------------------------------------------------------------------------------------------------------------------------
-- 프로시저 선언 및 스케줄 등록

DELIMITER $$
CREATE PROCEDURE SET_STATUS_PROCEDURE()
BEGIN
    -- 연체중인 도서 상태 변경
    UPDATE book
    SET book_status = 1
    WHERE book.book_uid IN (
        SELECT book_uid FROM rent_detail WHERE rent_return_due < NOW()
    );

    -- 수료 회원 상태 변경
    UPDATE user
    SET user_status = 1
    WHERE course_uid IN (
        SELECT course_uid FROM course WHERE course_graduate_date < NOW()
    );

    -- 예약 만료 상태 변경
    UPDATE book_reservation
    SET reservation_status = 1
    WHERE reservation_status = 0 AND reservation_due < NOW();

	-- 필요에 따라 추가
    
END $$
DELIMITER ;
-- drop procedure if exists SET_STATUS_PROCEDURE; -- 프로시저를 수정하려면 삭제하고 다시 만들어야 한다. 삭제용 쿼리 백업

create event if not exists MIDNIGHT_SET_STATUS -- 자정에 실행하는 이벤트 스케줄 (스케줄은 alter가 된다)
on schedule every 1 day
starts '2025-03-31 00:00:00'
on completion preserve enable
do call SET_STATUS_PROCEDURE;

----------------------------------------------------------------------------------------------------------------------------------------------------


