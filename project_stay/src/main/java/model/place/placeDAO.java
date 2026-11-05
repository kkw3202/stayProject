package model.place;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

import DBPKG.Util;
import model.common.search_accDTO;
import model.common.search_roomDTO;
import model.place.Room.RoomDAO;

public class placeDAO {
	// 도시, 숙소 타입, 숙소명 필터링 메소드
	public ArrayList<search_accDTO> do_searching(String city_name, String acc_type, String acc_name, String book_checkin_date, String book_checkout_date) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();
			
			String sql = "select a.acc_img, a.acc_type, a.acc_name, c.city_name, "
					+ "from Accommodation a "
					+ "inner join City c on c.city_code = a.city_code "
					+ "where ";
			if(city_name != null) sql += "c.city_name=? ";
			if(acc_type != null) sql += "a.acc_type=? ";
			if(acc_name != null) sql += "a.acc_name=? ";
			if(book_checkin_date != null && book_checkout_date != null) {
				RoomDAO rdao = new RoomDAO();
				rdao.count_booking_oneRoom(book_checkin_date, book_checkout_date, 0)
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, city_name);
			pstmt.setString(2, acc_type);
			pstmt.setString(3, acc_name);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				search_accDTO acdto = new search_accDTO();
				// 날 별 빈방 구해서 같이 담아야함. 쿼리문 추가도 잊지 말것!
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
		
		
	}
}
