package com.bitacademy.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.mysite.repository.BoardRepository;
import com.bitacademy.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	// main
	public List<BoardVo> getBoardList() {
		return boardRepository.findAll();
	}
	// view, update, reply
	public BoardVo getBoardview(Long no) {
		return boardRepository.findByBoardNo(no);
	}
	// write
	public void addBoardContent(BoardVo vo) {
		boardRepository.writeContent(vo);
	}
	// delete
	
	public void deleteBoard(Long no, String password) {
		boardRepository.deleteByNoAndPassword(no, password);
	}
	// update
	public void updateBoard(BoardVo vo) {
		boardRepository.updateByBoardNo(vo);
	}
	// reply
	public void addReplyContent(BoardVo replyVo) {
		boardRepository.replyContent(replyVo);
	}
	// hit
	public void updateHit(Long no) {
		boardRepository.updateHit(no);
	}
	
}
