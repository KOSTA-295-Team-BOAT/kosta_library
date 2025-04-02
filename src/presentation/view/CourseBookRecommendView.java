package presentation.view;

import java.util.List;
import java.util.Scanner;
import business.dto.Book;
import business.service.book.CourseRecommendService;
import util.ClearScreen;

public class CourseBookRecommendView {
    private final Scanner scanner = new Scanner(System.in);
    //컨트롤러 거치도록 리팩토링 해야 함
    private final CourseRecommendService recommendService = new CourseRecommendService();
    private final int PAGE_SIZE = 5;

    public void display(String userId) {
    	displayMenu();
        List<Book> allBooks = recommendService.getRecommendedBooksByCourse(userId);
        if (allBooks.isEmpty()) {
            displayEmptyList();
            return;
        }

        int currentPage = 0;
        int totalPages = (allBooks.size() - 1) / PAGE_SIZE;

        while (true) {
            displayBookList(allBooks, currentPage);
            System.out.println("\n현재 페이지: " + (currentPage + 1) + "/" + (totalPages + 1));
            System.out.println("p: 이전 페이지 | n: 다음 페이지 | 번호: 도서 선택 | x: 종료");
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "p":
                    if (currentPage > 0) currentPage--;
                    break;
                case "n":
                    if (currentPage < totalPages) currentPage++;
                    break;
                case "x":
                    return;
                default:
                    try {
                        int selection = Integer.parseInt(input) - 1;
                        int bookIndex = currentPage * PAGE_SIZE + selection;
                        if (bookIndex >= 0 && bookIndex < allBooks.size()) {
                            new BookDetailView().display(allBooks.get(bookIndex));
                        }
                    } catch (NumberFormatException e) {
                    	CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
                    }
            }
        }
        
        
    }

    private void displayEmptyList() {
    	System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
    	System.out.println("추천할 도서가 없습니다.");
    	System.out.println();
    	System.out.println("--------------------------------------------------------------------------------");

    }
    private void displayBookList(List<Book> books, int page) {
//        System.out.println("\n=== 추천 도서 목록 ===");
    	int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, books.size());
    	System.out.println("--------------------------------------------------------------------------------");
        for (int i = start; i < end; i++) {
            Book book = books.get(i);
            System.out.printf("%d. %s (상태: %s)\n", 
                (i - start + 1), 
                book.getBookName(),
                book.getBookStatus() == 0 ? "대여가능" : "대여중"
            );
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
    void displayMenu() {
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
    	System.out.println("|                       등록 과정 기반 도서 추천                               |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("|                                                                              |");
    	System.out.println("--------------------------------------------------------------------------------");
    	System.out.println();
    }
}
