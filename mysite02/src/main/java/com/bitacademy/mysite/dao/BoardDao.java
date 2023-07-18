package com.bitacademy.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bitacademy.mysite.vo.BoardVo;

public class BoardDao {

	public List<BoardVo> findAll() {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			String sql =" select a.title, b.name, a.hit, a.reg_date, a.depth, a.no, b.no "
					+ "     from board a, user b "
					+ "    where a.user_no = b.no "
					+ " order by a.g_no desc, a.o_no desc";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardVo vo = new BoardVo();
				vo.setTitle(rs.getString(1));
				vo.setWriter(rs.getString(2));
				vo.setHit(rs.getLong(3));
				vo.setRegDate(rs.getString(4));
				vo.setDepth(rs.getLong(5));
				vo.setNo(rs.getLong(6));
				vo.setWriterNo(rs.getLong(7));
				
				result.add(vo);
			}
			System.out.println("select 성공");
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
		return result;
	}

	public BoardVo findByBoardNo(long no) {
		BoardVo result = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

			String sql =" select a.title, a.contents, a.g_no, a.o_no, a.depth, b.no "
					+ "     from board a, user b "
					+ "    where a.user_no = b.no "
					+ "      and a.no = ? ";
			//reply할때 쓰려고 g_no, o_no, depth 추가
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			// 5. 결과처리
			if(rs.next()) {
				
				String title = rs.getString(1);
				String content = rs.getString(2);
				Long gNo = rs.getLong(3);
				Long oNo = rs.getLong(4);
				Long depth = rs.getLong(5);
				Long userNo = rs.getLong(6);
				
				BoardVo vo = new BoardVo();
				vo.setTitle(title);
				vo.setContent(content);
				vo.setGroupNo(gNo);
				vo.setOrderNo(oNo);
				vo.setDepth(depth);
				vo.setWriterNo(userNo);
				
				result = vo;
			}
			System.out.println("select 성공");
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
		return result;
	}

	public boolean writeContent(BoardVo boardVo) {
			boolean result = false;
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				
				String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
				conn = DriverManager.getConnection(url, "webdb", "webdb");
				
				String sql = "insert into board "
						+ "   values (null, ?, ?, 0, now(), "
						+ "           ((select max(a.g_no) from board a)+1), 1, 0, ? )";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setLong(3, boardVo.getWriterNo());
				
				int count = pstmt.executeUpdate(); //여기에 sql을 넣으면 안되고 바인딩이 완성된걸 넘겨줘야함
				
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

	public boolean deleteByNoAndPassword(Long no, String password) {
		boolean result = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			String url = "jdbc:mariadb://192.168.0.150:3306/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
			
			String sql = "delete a "
					+ "     from board a, user b "
					+ "    where a.user_no = b.no "
					+ "      and a.no = ? "
					+ "      and b.password = password(?)";
			pstmt = conn.prepareStatement(sql);
			 
			pstmt.setLong(1, no);
			pstmt.setString(2, password);
			
			// 5. SQL 실행
			int count = pstmt.executeUpdate();
			
			// 6. 결과처리
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
	
	
	
}
