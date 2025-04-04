package presentation.view;

import java.util.Scanner;
import java.util.List;
import business.dto.User;
import repository.dao.CourseDao;
import repository.dao.CourseDaoImpl;
import util.ClearScreen;
import business.dto.Course;

/**
 * 메인뷰에서 회원가입 부분을 출력해주는 클래스
 * @author 황태윤
 */
public class UserRegView {

    private Scanner sc = new Scanner(System.in);
    private final CourseDao courseDao = new CourseDaoImpl(); // CourseDao 추가

    /**
     * 회원가입 화면 출력
     */
    public void printUserRegView() {
        printMenu();
        System.out.println("회원가입을 위해 아래 정보를 입력해주세요.");
        System.out.println();
    	System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
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



            System.out.print("회원 ID > ");
            String userId = sc.nextLine();

            System.out.print("비밀번호 > ");
            String password = sc.nextLine();

            System.out.print("회원 이름 > ");
            String userName = sc.nextLine();

            System.out.println();
            // DB에서 과정 목록 불러오기 및 출력
        	System.out.println("--------------------------------------------------------------------------------");
        	System.out.println();
            System.out.println("수강하실 과정을 선택해 주세요");
            System.out.println();
        	System.out.println("--------------------------------------------------------------------------------");

            java.util.List<Course> courses = courseDao.getAllCourses();
            if (courses.isEmpty()) {
            	System.out.println();
                System.out.println("등록된 과정이 없습니다. 관리자에게 문의하세요.");
                System.out.println();
                return null;
            }
            System.out.println();
            for (Course course : courses) {
                System.out.println("[ID: " + course.getCourseUid() + "] " +
                                   "과정명: " + course.getCourseName() + " / " +
                                   "신청 가능 여부: " + (course.getCourse_open() == 1 ? "가능" : "불가능"));
            }
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");
        	System.out.println();
            System.out.print("과정 ID 입력 > ");
            String courseUid = sc.nextLine(); // courseUid 입력 추가

            // 간단한 유효성 검사: 빈 문자열 체크
            if (userId.trim().isEmpty() || password.trim().isEmpty() || userName.trim().isEmpty() || courseUid.trim().isEmpty()) {
                System.out.println("입력된 정보가 부족합니다. 처음부터 다시 시도해 주세요.");
                return null; // 입력이 부족하면 다시 입력받음
            }

            // 입력한 정보 출력
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("입력하신 회원정보는 다음과 같습니다:");
            System.out.println("회원 ID: " + userId);
            System.out.println("비밀번호: " + password);
            System.out.println("이름: " + userName);
            System.out.println("선택한 과정 ID: " + courseUid);
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
            while (true) { // 반복 루프 추가

            // 정보 확인
            System.out.print("이 정보로 회원가입 하시겠습니까? (Y/N): ");
            String confirm = sc.nextLine();

                if ("Y".equalsIgnoreCase(confirm)) {
                    return new User(userId, password, Integer.parseInt(courseUid), 0, 5, userName, null);
                } else if ("N".equalsIgnoreCase(confirm)) {
                    System.out.println("회원가입을 중단합니다.");
                    return null; // 메인으로 나감
                } else {
                	CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
                	System.out.println("Y 또는 N을 입력해주세요.");
                }
            }
        }
    }
    
//  System.out.print("회원가입을 취소하고 메인화면으로 돌아가시겠습니까? (Y/N): ");
//  String backToMain = sc.nextLine();
//
//  if ("Y".equalsIgnoreCase(backToMain)) {
//      System.out.println("메인화면으로 돌아갑니다.");
//      return null; // 메인화면으로 돌아감
//  } else if (!"N".equalsIgnoreCase(backToMain)) {
//  	CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
//  	System.out.println("Y 또는 N을 입력해주세요.");
//      continue; // 잘못된 입력 시 다시 질문
//  }

    void printMenu() {
    	System.out.println("");
    	ClearScreen.clear();
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                             [ B.O.A.T ]                                      |");
    	System.out.println("|                   Book of All Time : KOSTA Book System                       |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|            시스템에 로그인합니다. ID와 패스워드를 입력해주세요.              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
        System.out.println("                                                                                ");
    }

}
