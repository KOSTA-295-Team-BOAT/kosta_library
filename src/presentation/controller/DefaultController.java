package presentation.controller;

import business.dto.*; 

/**
 * 기본 컨트롤러 클래스 (공통 영역)
 */
public class DefaultController {
	
	BookController bookController = new BookController();
	UserController rentController = new UserController(null);
	
}
