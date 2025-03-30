package util;

/**
 * 간단한 세션 관리 클래스
 * 현재 로그인한 사용자의 ID를 저장하고 반환합니다.
 */
public class SessionManager {
    private static String currentUserId;

    // 현재 로그인한 사용자 ID를 설정
    public static void setCurrentUserId(String userId) {
        currentUserId = userId;
    }

    // 현재 로그인한 사용자 ID를 반환
    public static String getCurrentUserId() {
        return currentUserId;
    }

    // 세션 초기화
    public static void clearSession() {
        currentUserId = null;
    }
}
