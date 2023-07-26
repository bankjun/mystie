package com.bitacademy.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.mysite.vo.UserVo;

@Controller
public class MainController {
	// View Resolver
	@RequestMapping({"/", "/main"})
	public String index() {
		return "main/index";
	}
	// MessageConverter 예시 1(StringHttpMessageConverter - 기본설정값)
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() { 
		return "<h1>Hello world</h1>";
	}
	// MessageConverter 예시 2(message converter 직접 설정하기위한 예시, 한글설정)
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02(String name) { 
		return "<h1>안녕" + name + "</h1>";
	}
	
	// MessageConverter 예시 3 (기본설정으로는 객체를 출력할 수 없다.)
	@ResponseBody
	@RequestMapping("/msg03")
	public UserVo message03() {
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		
		return vo;
	}
}
