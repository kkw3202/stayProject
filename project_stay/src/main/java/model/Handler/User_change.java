package model.Handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import DBPKG.Util;

public class User_change {
	// 유저 이메일 (아이디)를 유저코드로 변경하는 메소드
	public int email_to_code(String user_email) {
		
		if(user_email == null && user_email.isEmpty()) {
			System.out.println("세션 없음! 이 메시지를 보신다면 로그인 해야 작동하는 기능에 예외처리를 하지 않으신겁니다.");
			return -1;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = -1;

		
		try {
			con = Util.getConnection();
			
			// 조인하고 찾아야함!
			String sql = "select user_code from user where user_email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			System.out.println("2");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();				
			}catch(Exception e) {e.printStackTrace();}
		}
		return result;
	
	}
}
