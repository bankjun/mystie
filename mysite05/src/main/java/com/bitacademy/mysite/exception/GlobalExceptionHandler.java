package com.bitacademy.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice // 이걸 알아보는건 컨테이너임, 그래서 컨테이너가 알아볼 수 있도록 설정해줘야함
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception ex) {
		// 1. 404 Error 처리
		if(ex instanceof NoHandlerFoundException) {
			return "error/404";
		}
		
		// 1. 로깅
		StringWriter errors = new StringWriter();
		
		ex.printStackTrace(new PrintWriter(errors));
		
		logger.error(errors.toString());
		
		// 2. 사과
		model.addAttribute("exception", errors.toString());
		return "error/exception";
		
	}
}
