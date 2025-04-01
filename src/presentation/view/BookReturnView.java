package presentation.view;

import java.util.List;
import java.util.Scanner;

import business.dto.RentDetail;
import presentation.controller.BookController;
import util.SessionManager;

/**
 * 도서 반납 화면
 */
public class BookReturnView {


    BookController controller = new BookController();
    Scanner sc = new Scanner(System.in);
    
	public void display() {
		System.out.println("도서 반납 화면");
		Scanner sc = new Scanner(System.in);
		System.out.println("---------------------------------------------------------------------------------");
        List<RentDetail> rentDetailList = controller.getRentDetailByUserId(SessionManager.getCurrentUserId());
        if (!rentDetailList.isEmpty()) {
        	int i=1;
        	for(RentDetail detail : rentDetailList) {
        		System.out.println("["+(i++)+"]" + " | " + detail.getRentReturnDue()+" 까지 + " + controller.getBookById(detail.getBookUid()).getBookName());
        	}
        }
        System.out.println("---------------------------------------------------------------------------------");    
        System.out.println("반납할 도서 번호를 선택하세요");
        
		
		
	}
	
	
}
