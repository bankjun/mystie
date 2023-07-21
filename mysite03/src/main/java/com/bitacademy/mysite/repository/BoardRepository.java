package com.bitacademy.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
	// view
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
	
/////////////////////////////////////////////////////////////////////////////////


	@SuppressWarnings("resource")
	public boolean replyContent(BoardVo boardVo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			// update
			String updatesql = "update board set o_no = o_no+1 "
						  + "    where g_no = ? "
					      + "      and o_no >= ?";
			pstmt = conn.prepareStatement(updatesql);
			
			pstmt.setLong(1, boardVo.getGroupNo());
			pstmt.setLong(2, boardVo.getOrderNo());			
			pstmt.executeUpdate();
			
			// insert 1
			String sql = "insert into board "
					+ "        values (null, ?, ?, 0, now(), ?, ?, ?, ? )";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setLong(3, boardVo.getGroupNo());
			pstmt.setLong(4, boardVo.getOrderNo());
			pstmt.setLong(5, (boardVo.getDepth()+1l));
			pstmt.setLong(6, boardVo.getWriterNo());
			
			int count = pstmt.executeUpdate();
			
			result = count == 1;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: "+ e);
		} catch (SQLException e) {
			System.out.println("Error: "+ e);
		} finally {// 7. 자원정리
			try {
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
		return result;
	}

	public boolean updateByBoardNoAndWriterNo(BoardVo updateVo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			String sql = "update board set title = ? , content = ? "
					+ "    where no= ? "
					+ "      and user_no = ?  ";
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL 실행
			pstmt.setString(1,  updateVo.getTitle());
			pstmt.setString(2, updateVo.getContent());
			pstmt.setLong(3, updateVo.getNo());
			pstmt.setLong(4, updateVo.getWriterNo());
			
			int count = pstmt.executeUpdate();
			
			// 5. 결과처리
			result = count == 1;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: "+ e);
		} catch (SQLException e) {
			System.out.println("Error: "+ e);
		} finally {// 6. 자원정리
			try {
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
	return result;		
	}

	public boolean hitUpdate(Long boardNo) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			String sql = "update board set hit=(hit+1)"
					+ "    where no= ? ";
			pstmt = conn.prepareStatement(sql);
			
			// 4. SQL 실행			
			pstmt.setLong(1, boardNo);
			
			int count = pstmt.executeUpdate();
			
			// 5. 결과처리
			result = count == 1;
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: "+ e);
		} catch (SQLException e) {
			System.out.println("Error: "+ e);
		} finally {// 6. 자원정리
			try {
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
		return result;
	}
}
