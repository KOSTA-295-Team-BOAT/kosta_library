package repository.dao;

import business.dto.User;
import java.util.List;

/**
 * 이용자 정보와 관련된 DAO 기능을 정의하는 인터페이스
 * @author  황태윤
 * @since 2025.3.27
 */
public interface UserDao {
    // TODO : 메소드명 수정 부탁해요.
    // 회원 추가
    void addUser(User user);

    // 회원 ID로 조회
    User getUserById(String userId);

    // 모든 회원 조회
    List<User> getAllUsers();

    // 회원 정보 업데이트
    void updateUser(User user);

    // 회원 삭제
    void deleteUser(String userId);
}
