package presentation.view;

import java.util.Scanner;
import business.dto.User;

/**
 * 메인뷰에서 회원가입 부분을 출력해주는 클래스
 * @author 황태윤
 */
public class UserRegView {

    private Scanner sc = new Scanner(System.in);

    /**
     * 회원가입 화면 출력
     */
    public void printUserRegView() {
        System.out.println("회원가입 화면");
        System.out.println("회원가입을 위해 아래 정보를 입력해주세요.");
    }

    /**
     * 회원가입 성공 메시지 출력
     */
    public void printUserRegSuccess() {
        System.out.println("회원가입이 성공적으로 완료되었습니다.");
    }

    /**
     * 회원가입 취소 메시지 출력
     */
    public void printUserRegCancel() {
        System.out.println("회원가입이 취소되었습니다.");
    }

    /**
     * 회원가입 정보 입력 요청 메시지 출력
     */
    public void printUserRegInputRequest() {
        System.out.println("아이디와 비밀번호를 입력해주세요.");
    }

    /**
     * 회원가입 정보 입력 성공 메시지 출력
     */
    public void printUserRegInputSuccess() {
        System.out.println("입력이 완료되었습니다.");
    }

    /**
     * 회원가입 정보 입력 확인 메시지 출력
     */
    public void printUserRegInputConfirm() {
        System.out.println("입력한 정보가 맞습니까? (Y/N)");
    }

    /**
     * 회원가입 정보 입력 확인 에러 메시지 출력
     */
    public void printUserRegInputConfirmError() {
        System.out.println("입력이 잘못되었습니다. 다시 입력해주세요.");
    }
    
    /**
     * 콘솔에서 회원가입 정보를 입력받아 확인하는 메서드
     * @return 모든 입력이 올바르고 최종 확인이 되면 User 객체, 그렇지 않으면 null
     */
    public User inputUserRegInfo() {
        while (true) { // 반복 루프 시작
            System.out.print("회원가입을 취소하고 메인화면으로 돌아가시겠습니까? (Y/N): ");
            String backToMain = sc.nextLine();

            if ("Y".equalsIgnoreCase(backToMain)) {
                System.out.println("메인화면으로 돌아갑니다.");
                return null; // 메인화면으로 돌아감
            } else if (!"N".equalsIgnoreCase(backToMain)) {
                System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
                continue; // 잘못된 입력 시 다시 질문
            }

            System.out.print("아이디 입력: ");
            String userId = sc.nextLine();

            System.out.print("비밀번호 입력: ");
            String password = sc.nextLine();

            System.out.print("이름 입력: ");
            String userName = sc.nextLine();

            // 간단한 유효성 검사: 빈 문자열 체크
            if (userId.trim().isEmpty() || password.trim().isEmpty() || userName.trim().isEmpty()) {
                System.out.println("입력된 정보가 부족합니다. 다시 입력해주세요.");
                continue; // 입력이 부족하면 다시 입력받음
            }

            // 입력한 정보 출력
            System.out.println("입력하신 회원정보는 다음과 같습니다:");
            System.out.println("아이디: " + userId);
            System.out.println("비밀번호: " + password);
            System.out.println("이름: " + userName);
            System.out.println("선택한 과정 ID: " + courseUid);
            
            while (true) { // 반복 루프 추가

            // 정보 확인
            System.out.print("이 정보로 회원가입 하시겠습니까? (Y/N): ");
            String confirm = sc.nextLine();

                if ("Y".equalsIgnoreCase(confirm)) {
                    return new User(userId, password, courseUid, 0, 0, 5, userName);
                } else if ("N".equalsIgnoreCase(confirm)) {
                    System.out.println("다시 입력 화면으로 돌아갑니다.");
                    break; // 루프 종료
                } else {
                    System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
                }
            }
        }
    }
}
