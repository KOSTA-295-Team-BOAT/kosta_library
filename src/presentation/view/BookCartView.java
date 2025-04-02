package presentation.view;

import presentation.controller.BookCartController;
import business.dto.Book;

import java.util.List;
import java.util.Scanner;

public class BookCartView {

    private final BookCartController cartController = new BookCartController();
    private final Scanner scanner = new Scanner(System.in);

    public void display() {
        cartController.refreshRentalCount();

        while (true) {
            System.out.println("\n=== 📚 내 북카트 ===");
            cartController.viewCart();
            System.out.println("[1] 도서 제거  [2] 전체 비우기  [3] 대여 확정  [4] 뒤로 가기");
            System.out.print("선택: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> removeBookFromCart();
                case "2" -> cartController.clearCart();
                case "3" -> {
                    cartController.confirmRental();
                    return;
                }
                case "4" -> {
                    System.out.println("북카트에서 나갑니다.");
                    return;
                }
                default -> CommonMessageView.wrongInput(); //잘못된 입력입니다 메시지
            }
        }
    }

    private void removeBookFromCart() {
        List<Book> cart = cartController.getCart();
        if (cart.isEmpty()) {
            System.out.println("북카트가 비어 있습니다.");
            return;
        }

        System.out.print("제거할 도서 번호를 입력하세요: ");
        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < cart.size()) {
                Book book = cart.get(index);
                cartController.removeFromCart(book);
            } else {
                System.out.println("잘못된 번호입니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력해주세요.");
        }
    }
}