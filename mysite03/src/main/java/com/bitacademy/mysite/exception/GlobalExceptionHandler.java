package com.bitacademy.mysite.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 이걸 알아보는건 컨테이너임, 그래서 컨테이너가 알아볼 수 있도록 설정해줘야함
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception ex) {
		// 1. 로깅
		ex.printStackTrace();
		
		// 2. 사과
		return "error/exception";
		
	}
}
