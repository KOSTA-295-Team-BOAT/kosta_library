package exception;

/**
 * Select 결과에 이상이 있을 시
 * Wrapping 용도 (SQLException의 세부내용을 유저에게 전달하지 않기 위함)
 */
public class SearchWrongException extends Exception {

	SearchWrongException(){
		
	}
	
	SearchWrongException(String message){
		super(message);
	}
	
}
