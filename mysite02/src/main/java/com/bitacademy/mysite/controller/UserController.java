package com.bitacademy.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.dao.UserDao;
import com.bitacademy.mysite.vo.UserVo;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		if("joinform".equals(actionName)) {
			request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp")
			.forward(request, response);
		} else if("joinsuccess".equals(actionName)) {
			request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp")
			.forward(request, response);
		} else if("join".equals(actionName)) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			UserDao dao = new UserDao();
			dao.insert(vo);
			
			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
		}else if("loginform".equals(actionName)) {
			request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
			.forward(request, response);
		}else if("login".equals(actionName)) {			
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			UserVo authUser = new UserDao().findByEmailAndPassword(email, password);
			if(authUser == null) {
				// 인증실패 -> 다시 로그인폼으로 보내기
				// 방법 1 리다이렉트
				response.sendRedirect(request.getContextPath() + "/user?a=loginform&result=fail");
				// 방법 2 포워드
//				request.setAttribute("result", "fail");
//				request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
//				.forward(request, response);
				
				return; //이렇게 끝내기
			}
			
			// 인증성공
			System.out.println("로그인처리");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
