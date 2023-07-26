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
		boardService.updateHit(no);
		model.addAttribute("viewvo", boardService.getBoardview(no));
		model.addAttribute("boardno", no);
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
	public String wirte(@AuthUser UserVo authUser, BoardVo vo) { // POST로 vo로 받으면 알아서 형식 맞춰서 들어옴
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
	@RequestMapping("/update/{no}")
	public String update(Model model,@PathVariable("no")Long no) {
		model.addAttribute("boardVo", boardService.getBoardview(no));
		
		return"/board/update";
	}
	// UPDATE
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(BoardVo vo) {
		boardService.updateBoard(vo);
		return"redirect:/board/view/"+vo.getNo();
	}
	// REPLY FORM
	@Auth
	@RequestMapping("/reply/{no}")
	public String reqly(@PathVariable("no")Long no, Model model) {
		model.addAttribute("boardno", no);
		return "/board/reply";
	}
	// REPLY
	@Auth
	@RequestMapping(value="/reply", method=RequestMethod.POST)
	public String reply(@AuthUser UserVo authUser, BoardVo vo) {// no,title,content
		BoardVo replyVo = boardService.getBoardview(vo.getNo());
		
		replyVo.setWriterNo(authUser.getNo()); // 글쓴이 = 현재로그인한애
		replyVo.setTitle(vo.getTitle());	   // 답글제목
		replyVo.setContent(vo.getContent());   // 답글내용
		
		boardService.addReplyContent(replyVo);
		
		return "redirect:/board";
	}
}