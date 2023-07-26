package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.GuestbookRepository;
import com.bitacademy.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	// 사용자는 방명록 리스트를 본다.
	public List<GuestbookVo> getMessageList(){
		return guestbookRepository.findAll();
	}
	// 사용자는 방명록에 글을 삭제한다.
	public void deleteMessage(Long no, String password) {
		guestbookRepository.deleteByNoAndPassword(no, password);
	}
	// 사용자는 방명록에 글을 남긴다.
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
	
}
