package com.bitacademy.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.service.UserService;
import com.bitacademy.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	// 이게 GET방식, 링크는 무조건 겟이래, 주소가 바뀌니까?
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	// return "/WEB-INF/views/user/join.jsp"; 앞뒤를 viewReserve로 생략해줌
	
	// html에서 포스트 방식으로 보냈으니까 여기로옴
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {
		// insert는 BD에 사용하는것이 좋음
		userService.addUser(vo);		
		return "redirect:/user/joinsucces"; // 매핑에서는 클래스 매핑으로 user를 줄엿지만 
											// 리다이렉트에서는 그대로 써야함
	}
	
	@RequestMapping("/joinsucces")// 얘는 어차피 하나니까 get post를 구분할 필요가 없음
	public String joinsucces() {
		return "user/joinsucces";
	}
}
