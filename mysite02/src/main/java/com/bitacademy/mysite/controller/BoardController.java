package com.bitacademy.mysite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bitacademy.mysite.dao.BoardDao;
import com.bitacademy.mysite.vo.BoardVo;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String actionName = request.getParameter("a");
		if("writeform".equals(actionName)) {
			
			request.getRequestDispatcher("/WEB-INF/views/board/writeform.jsp")
			.forward(request, response);
		} else if("write".equals(actionName)) {
			
		} else if("view".equals(actionName)) {
			String boardNo = request.getParameter("no");
			BoardVo viewVo = new BoardDao().findByBoardNo(Long.parseLong(boardNo)); // 여기에 글제목,내용, 글쓴유저 번호
			
			request.setAttribute("viewvo", viewVo);
			
			request.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
		} else if("updateform".equals(actionName)) {
			
			
			request.getRequestDispatcher("/WEB-INF/views/board/updateform.jsp")
			.forward(request, response);
		} else if("update".equals(actionName)) {
			
		} else if("deleteform".equals(actionName)) {
			
			request.getRequestDispatcher("/WEB-INF/views/board/deleteform.jsp")
			.forward(request, response);
		} else {
			// default action
			BoardDao dao = new BoardDao();
			List<BoardVo> list = new ArrayList<>();
			
			list = dao.findAll();
			
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
