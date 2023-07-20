package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	@Autowired
	private DataSource dateSource;
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() {// namespace.id
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public boolean insert(GuestbookVo vo) {
		return 1 == sqlSession.insert("guestbook.insert");
	}
	
	public void deleteByNoAndPassword(Long no, String password) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			String sql ="delete from guestbook where no= ? and password= ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			pstmt.setString(2, password);

			rs = pstmt.executeQuery();
			
			// 6. 결과처리할게 있나 -> sql문 실행하면 끝인데22
			System.out.println("delete 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: "+ e);
		} catch (SQLException e) {
			System.out.println("Error: "+ e);
		} finally {// 6. 자원정리
			try {
				
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteByNoAndPassword(GuestbookVo vo) {
		this.deleteByNoAndPassword(vo.getNo(), vo.getPassword());
	}
}
