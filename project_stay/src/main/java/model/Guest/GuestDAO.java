package model.Guest;

import java.sql.*;

import DBPKG.Util;


public class GuestDAO {
	// 새로 생길 비회원 번호 구하는 메소드
	public int get_LastNum() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			con = Util.getConnection();

			String sql = "select max(guest_code) from guest";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	// 새로운 비회원 정보 추가하는 메소드
	public boolean set_newGuset(String book_name, String phone) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		boolean result = false;
		
		try {
			con = Util.getConnection();

			String sql = "insert into guest values(null, ?, ?, 0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, phone);
			pstmt.setString(2, book_name);

			int success = pstmt.executeUpdate();
			
			if(success >= 1) result = true;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
