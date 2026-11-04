package model.place;

import java.sql.*;
import java.text.SimpleDateFormat;

import com.mysql.cj.protocol.Resultset;

import DBPKG.Util;

public class placeDAO {
	public void do_searching(String city_name, String acc_type, String room_name, String book_checkin_date, String book_checkout_date) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();
			
			String sql = "select * from Accomodation a "
					+ "inner join Room r on a.acc_code = r.acc_code "
					+ "inner join City c on c.city_code. = a.city_code "
					+ "";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(con != null) con.close();
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();				
			}catch(Exception e) {e.printStackTrace();}
		}
		
		
	}
}
