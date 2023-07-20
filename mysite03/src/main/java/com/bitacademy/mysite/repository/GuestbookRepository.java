package com.bitacademy.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {// namespace.id
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public boolean insert(GuestbookVo vo) {
		return 1 == sqlSession.insert("guestbook.insert");
	}
	
	public void deleteByNoAndPassword(Long no, String password) {
		// 두개의 파라메터를 보낼 수 없음 -> 그래서 객체이용해야함
		// 만약 Vo가 없다면 map을 사용할수 있다.
		Map<String , Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		sqlSession.delete("guestbook.deleteByNoAndPassword", map);
	}
	
	public void deleteByNoAndPassword(GuestbookVo vo) {
		this.deleteByNoAndPassword(vo.getNo(), vo.getPassword());
	}
}
