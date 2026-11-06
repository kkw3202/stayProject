package model.WishList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.Util;
import model.Handler.User_change;


public class WishListDAO {
	// 유저 한명의 찜목록을 전부 가져오는 메소드
	public ArrayList<Integer> getWishList(String user_email){
		
		User_change uc = new User_change();
		int user_code = uc.email_to_code(user_email);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Integer> result = new  ArrayList<Integer>();

		
		try {
			con = Util.getConnection();

			String sql = "select acc_code from wishList where user_code=? order by acc_code asc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, user_code);
			
			while(rs.next()) {
				result.add(rs.getInt("acc_code"));
			}
			
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
