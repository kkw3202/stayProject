package model.Handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import DBPKG.Util;

public class User_change {
	// 회원 이메일(아이디)로 유저코드를 반환하는 메서드
	public int email_to_code(String user_email) {
		
		if(user_email == null && user_email.isEmpty()) {
			System.out.println("에러 발생! 이 메세지가 보인다면 로그인을 해야 작동하는 기능에 정상작동시키지 않은 상태입니다.");
			return -1;
		}
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = -1;

		
		try {
			con = Util.getConnection();
			
			// 정확하고 찾아야함!
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