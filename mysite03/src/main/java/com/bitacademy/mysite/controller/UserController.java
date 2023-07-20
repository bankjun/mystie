package com.bitacademy.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.security.Auth;
import com.bitacademy.mysite.security.AuthUser;
import com.bitacademy.mysite.service.UserService;
import com.bitacademy.mysite.vo.UserVo;

@Controller
@RequestMapping("/user") // 클래스에 이걸 매핑 해놔서 메소드에서는 /user를 생략해도됨, return 에서는 제대로 써야함
public class UserController {
	@Autowired
	private UserService userService;

	/////////////////////////////////////////////////////////////////////////////////
	///////////////////////////// JOIN////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	// 이게 GET방식, 링크는 무조건 겟이래, 주소가 바뀌니까?
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	// return "/WEB-INF/views/user/join.jsp"; 앞뒤를 viewReserve로 생략해줌

	// html에서 포스트 방식으로 보냈으니까 여기로옴
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserVo vo) {
		// insert는 BD에 사용하는것이 좋음
		userService.addUser(vo);
		return "redirect:/user/joinsucces"; // Mapping에서는 클래스 Mapping으로 user를 줄엿지만
	} // 리다이렉트에서는 그대로 써야함

	@RequestMapping("/joinsucces") // 얘는 어차피 하나니까 get post를 구분할 필요가 없음
	public String joinsucces() {
		return "user/joinsucces";
	}

	/////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		// 아규먼트 리저브(argument reserve)
		Long no = authUser.getNo();
		UserVo userVo = userService.getUser(no);
		model.addAttribute("userVo", userVo);
		
		return "user/update";
	}


	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo vo) {
		vo.setNo(authUser.getNo());
		userService.updateUser(vo);

		authUser.setName(vo.getName()); // 세션에 이름 적용

		return "redirect:/user/update";
	}
}
/////////////////////////////////////////////////////////////////////////////////////
///////////////////////SECURITY로 인해 사라진 메소드 및 코드들////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
/* 로그인 -> LoginIntercepter.java로 이동
 * @RequestMapping(value="/login", method=RequestMethod.POST) 
 * public String login(HttpSession session, UserVo vo, Model model) { 
 * /////////////////// 접근제어 ///////////////////////////////// 
 * UserVo authUser = userService.getUser(vo);
 * if(authUser == null) { 
 * 		model.addAttribute("result", "fail"); 
 * 		return "user/login"; 
 * } 
 * ////////////////////////////////////////////////////////////
 * 
 *      로그인처리
 * session.setAttribute("authUser", authUser); 
 * return "redirect:/"; 
 * }
 */

/* 로그아웃
 * @RequestMapping("/logout") public String logout(HttpSession session) {
 * /////////////////// 접근제어 ///////////////////////////////// UserVo authUser =
 * (UserVo) session.getAttribute("authUser"); if(authUser == null) { return
 * "redirect:/"; } ////////////////////////////////////////////////////////////
 * 
 * 로그아웃처리 session.removeAttribute("authUser"); session.invalidate();
 * 
 * return "redirect:/"; }
 * 
 */

/* 업데이트 */
//@RequestMapping(value="/update", method=RequestMethod.GET)
//public String update(HttpSession session, Model model) {
//	/////////////////// 접근제어 /////////////////////////////////
//	UserVo authUser = (UserVo) session.getAttribute("authUser");
//	if(authUser == null) {
//		return "redirect:/";
//	}
//	////////////////////////////////////////////////////////////
//	Long no = authUser.getNo();
//	UserVo userVo = userService.getUser(no);
//	model.addAttribute("userVo", userVo);
//	
//	return "user/update";
//}
