package board.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import oracle.jdbc.proxy.annotation.Pre;
import resources.JdbcConnectionUtil;

public class BoardDao implements IBoardDao {
	private static BoardDao instance;
	private JdbcConnectionUtil util;
	private Properties prop = new Properties();
	InputStream input;

	private BoardDao() {
		util = JdbcConnectionUtil.getInstance();
		getPropertyValue();
	}
	
	private void getPropertyValue() {
		String propName = "/resources/boardQuery.properties";
		input = getClass().getResourceAsStream(propName);
		if (input != null) {
			try {
				prop.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static BoardDao getInstance() {
		synchronized (BoardDao.class) {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}

	@Override
	public int insert(BoardVo vo) {
		String sql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			conn = util.getConnection();
			if(vo.getRef()==0) {
				sql = prop.getProperty("insert");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getWriter());
				pstmt.setString(2, vo.getTitle());
				pstmt.setString(3, vo.getContent());
				pstmt.setInt(4, vo.getDepth());
				pstmt.setString(5, vo.getPassword());
			} else {
				sql = prop.getProperty("addInsert");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getWriter());
				pstmt.setString(2, vo.getTitle());
				pstmt.setString(3, vo.getContent());
				pstmt.setInt(4, vo.getDepth());
				pstmt.setInt(5, vo.getRef());
				pstmt.setString(6, vo.getPassword());
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}

	@Override
	public void update(BoardVo vo) {
		String sql = prop.getProperty("update");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getNum());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt);
			util.close(conn);
		}
	}

	@Override
	public void delete(int num) {
		String sql = prop.getProperty("delete");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt);
			util.close(conn);
		}
	}

	@Override
	public List<BoardVo> selectAll(int start, int end) {
		String sql = prop.getProperty("selectAll");
		List<BoardVo> li = new ArrayList<BoardVo>(10);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVo tmp = new BoardVo(
						rs.getInt("NUM"), 
						rs.getString("WRITER"), 
						rs.getString("TITLE"),
						rs.getString("CONTENT"), 
						rs.getInt("READCOUNT"), 
						rs.getTimestamp("REGDATE"), 
						rs.getTimestamp("MODDATE"), 
						rs.getInt("DEPTH"),
						rs.getInt("REF"),
						rs.getString("PASSWORD"));
				li.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return li;
	}

	@Override
	public List<BoardVo> search(String type, String str, int start, int end) {
		System.out.println(type);
		String sql = prop.getProperty("search" + type);
		System.out.println(sql);
		List<BoardVo> li = new ArrayList<BoardVo>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + str + "%");
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				BoardVo tmp = new BoardVo(
						rs.getInt("NUM"), 
						rs.getString("WRITER"), 
						rs.getString("TITLE"),
						rs.getString("CONTENT"), 
						rs.getInt("READCOUNT"), 
						rs.getTimestamp("REGDATE"), 
						rs.getTimestamp("MODDATE"), 
						rs.getInt("DEPTH"),
						rs.getInt("REF"),
						rs.getString("PASSWORD"));
				li.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return li;
	}

	@Override
	public BoardVo detail(int num) {
		BoardVo vo = null;
		String sql = prop.getProperty("detail");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new BoardVo(
						rs.getInt("NUM"), 
						rs.getString("WRITER"), 
						rs.getString("TITLE"),
						rs.getString("CONTENT"), 
						rs.getInt("READCOUNT"), 
						rs.getTimestamp("REGDATE"), 
						rs.getTimestamp("MODDATE"), 
						rs.getInt("DEPTH"),
						rs.getInt("REF"),
						rs.getString("PASSWORD"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		
		return vo;
	}

	@Override
	public List<BoardVo> detailAnswer(int num) {
		List<BoardVo> li = null;
		String sql = prop.getProperty("detailAnswer");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			li = new ArrayList<BoardVo>();
			while (rs.next()) {
				BoardVo tmp = new BoardVo(
						rs.getInt("NUM"), 
						rs.getString("WRITER"), 
						rs.getString("TITLE"),
						rs.getString("CONTENT"), 
						rs.getInt("READCOUNT"), 
						rs.getTimestamp("REGDATE"), 
						rs.getTimestamp("MODDATE"), 
						rs.getInt("DEPTH"),
						rs.getInt("REF"),
						rs.getString("PASSWORD"));
				li.add(tmp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return li;
	}
	@Override
	public void hitIt(int num) {
		String sql = prop.getProperty("hitIt");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt);
			util.close(conn);
		}
	}


	@Override
	public Map<Object, Object> boardRef() {
		String sql = prop.getProperty("boardRef");
		Map<Object, Object> result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			result = new HashMap<Object, Object>();
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result.put(rs.getInt(1), rs.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}

	@Override
	public int boardCount() {
		int result = 0;
		String sql = prop.getProperty("boardCount");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}
	
	@Override
	public int searchBoardCount(String type, String str) {
		int result = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(prop.getProperty("searchBoardCount"));
		sql.append(type);
		sql.append(" LIKE ?");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, "%" + str + "%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}


}
