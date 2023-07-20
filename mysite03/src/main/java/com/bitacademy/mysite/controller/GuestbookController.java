package com.bitacademy.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.mysite.security.Auth;
import com.bitacademy.mysite.service.GuestbookService;
import com.bitacademy.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook") 
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@Auth
	@RequestMapping({"", "/list"})
	public String list(Model model) {
		model.addAttribute("list", guestbookService.getMessageList());
		return "/guestbook/list";
	}
	
	// list에서 add할때 name, password, content를 post방식으로 보냈기때문에 POST로 받아준다.
	@RequestMapping(value= "/add", method = RequestMethod.POST)
	public String add(GuestbookVo vo) { 
		// post로 받고 각 항목의 'name'들이 Vo의 필드들과 이름이 같아서 vo로 바로 받을 수 있음
		guestbookService.addMessage(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping("/delete/{no}")//GET방식 -> delete.jsp로 보내기
	public String delete(Model model,@PathVariable("no") Long no) {
		model.addAttribute("no", no);
		return "/guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	public String delete(Long no, String password) {
		guestbookService.deleteMessage(no, password);
		return "redirect:/guestbook";
	}
}
