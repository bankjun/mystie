package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.BoardVo;
@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	//게시판 메인
	public List<BoardVo> findAll() {
		return sqlSession.selectList("board.findAll");
	}
	// view, update, reply
	public BoardVo findByBoardNo(Long no) {
		return sqlSession.selectOne("board.findByBoardNo", no);
	}
	// write
	public void writeContent(BoardVo vo) {
		sqlSession.insert("board.writeContent", vo);
	}
	// delete
	public void deleteByNoAndPassword(Long no, String password) {
		Map <String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		sqlSession.delete("board.deleteByNoAndPassword", map);
	}
	// update
	public void updateByBoardNo(BoardVo vo) {
		sqlSession.update("board.updateByBoardNo", vo);
	}
	// reply
	public void replyContent(BoardVo replyVo) {
		sqlSession.update("board.replyContent", replyVo);
		sqlSession.insert("board.addReplyContent", replyVo);
	}
	// hit
	public void updateHit(Long no) {
		sqlSession.update("board.updateHit", no);
	}
}