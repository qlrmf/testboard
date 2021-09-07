package attach.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import resources.JdbcConnectionUtil;

public class AttachDao implements IAttachDao {
	private static AttachDao instance;
	private JdbcConnectionUtil util;
	private Properties prop = new Properties();
	InputStream input;
	
	private AttachDao() {
		util = JdbcConnectionUtil.getInstance();
		getPropertyValue();
	}
	
	private void getPropertyValue() {
		String propName = "/resources/attachQuery.properties";
		input = getClass().getResourceAsStream(propName);
		if(input!=null) {
			try {
				prop.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static AttachDao getInstance() {
		synchronized (AttachDao.class) {
			if(instance==null) {
				instance = new AttachDao();
			}
		}
		return instance;
	}
	
	@Override
	public int insert(AttachVo vo) {
		String sql = "";
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = util.getConnection();
			if(vo.getbNum()==0) {
				sql = prop.getProperty("insert");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getFileName());
				pstmt.setLong(2, vo.getFileSize());
				pstmt.setString(3, vo.getContentType());
			}else {
				sql = prop.getProperty("addInsert");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getFileName());
				pstmt.setInt(2, vo.getbNum());
				pstmt.setLong(3, vo.getFileSize());
				pstmt.setString(4, vo.getContentType());
			}
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt);
			util.close(conn);
		}
		return result;
	}

	@Override
	public void update(AttachVo vo) {
		String sql = prop.getProperty("update");
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getFileName());
			pstmt.setLong(2, vo.getFileSize());
			pstmt.setString(3, vo.getContentType());
			pstmt.setInt(4, vo.getbNum());
			pstmt.executeUpdate();
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(pstmt);
			util.close(conn);
		}
	}

	@Override
	public AttachVo files(int bNum) {
		AttachVo vo = null;
		String sql = prop.getProperty("files");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo= new AttachVo(
						rs.getInt("NUM"), 
						rs.getString("FILENAME"),
						rs.getInt("BNUM"), 
						rs.getLong("FILESIZE"), 
						rs.getString("CONTENTTYPE")
						);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return vo;
	}

	@Override
	public int countFile(int bNum) {
		int result = 0;
		String sql = prop.getProperty("countFile");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bNum);
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
	public Map<Object, Object> countFileList() {
		Map<Object, Object> hsm = new HashMap<Object, Object>();
		String sql = prop.getProperty("countFileList");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = util.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				hsm.put(rs.getInt(1), rs.getInt(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			util.close(rs);
			util.close(pstmt);
			util.close(conn);
		}
		return hsm;
	}

}
