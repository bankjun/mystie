package com.bitacademy.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;

import com.bitacademy.mysite.security.Auth;
import com.bitacademy.mysite.security.AuthUser;
import com.bitacademy.mysite.service.BoardService;
import com.bitacademy.mysite.vo.BoardVo;
import com.bitacademy.mysite.vo.UserVo;
@Controller
@RequestMapping("/board")
public class BoardController{
	@Autowired
	private BoardService boardService;
	// MAIN
	@RequestMapping({"", "/list"})
	public String list(Model model) {
		
		model.addAttribute("list", boardService.getBoardList());
		return "/board/list";
	}		
	// VIEW
	@RequestMapping("/view/{no}")
	public String view(@PathVariable("no")Long no, Model model) {
		model.addAttribute("viewvo", boardService.getBoardview(no));
		return "/board/view";
	}
	// WRITE FORM
	@RequestMapping("/write")
	public String write() {
		return "/board/write";
	}
	// WRITE
	@Auth
	@RequestMapping(value="/write", method = RequestMethod.POST)
	public String wirte(@AuthUser UserVo authUser, BoardVo vo) {
		vo.setWriterNo(authUser.getNo());
		vo.setWriter(authUser.getName());
		boardService.addBoardContent(vo);
		return "redirect:/board";
	}
	// DELETE FORM
	@Auth
	@RequestMapping(value="/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no")Long no, Model model) {
		model.addAttribute("boardno", no);
		return "/board/delete";
	}
	// DELETE
	@Auth
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(Long no, String password) {
		boardService.deleteBoard(no, password);
		return "redirect:/board";
	}
	// UPDATE FORM
	@Auth
	@RequestMapping("/update")
	public String update() {
		return"/board/update";
	}
	// UPDATE
	@Auth
	@RequestMapping(value="/update/{no}", method=RequestMethod.POST)
	public String update(@PathVariable("no")Long no, String title, String content) {
		
		return"redirect:/board/view{"+no+"}";
	}
}