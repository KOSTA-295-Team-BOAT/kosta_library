package exception;

/**
 * insert, update, delete 쿼리에서 예외 발생 시
 * Wrapping 용도 (SQLException의 세부내용을 유저에게 전달하지 않기 위함)
 */

public class DmlException extends Exception {
	DmlException(){
		
	}
	DmlException(String message){
		super(message);
	}
}
