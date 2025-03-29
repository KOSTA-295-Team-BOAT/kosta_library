package presentation.view;


/**
 * 메인뷰에서 로그인 부분을 출력해주는 클래스
 * @author 
 */
public class LoginView {
    public String[] inputLoginInfo() {
    	System.out.println("===== 로그인 화면 =====");
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID를 입력하세요: ");
        String id = scanner.nextLine();
        System.out.print("패스워드를 입력하세요: ");
        String password = scanner.nextLine();
        return new String[]{id, password};
    }

    public void printLoginSuccess() {
        System.out.println("로그인 성공! 메인 화면으로 이동합니다.");
    }

    public void printLoginFail() {
        System.out.println("로그인 실패! ID 또는 비밀번호를 확인해주세요.");
    }
	public void printLoginView() {
		// TODO Auto-generated method stub
		
	}

	public boolean inputLoginInfo() {
		// TODO Auto-generated method stub
		return false;
	}
    
}
