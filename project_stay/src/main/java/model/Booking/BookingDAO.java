package model.Booking;

import java.sql.*;
import java.util.Random;

import DBPKG.Util;


public class BookingDAO {
	// 예약 집어넣기 
	public void doBooking(int user_code, String book_checkin_date, String book_checkout_date, int room_code, String book_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Random ran = new Random();
		
		String isUser = user_code!=-1 ? "?, " : "null, ";
		String isGuest = user_code==-1 ? "?, " : "null, ";
		int isUserChk = user_code!=-1 ? 1 : 0;
		
		String book_number = ""; // 현재 상태에서는 12자리 난수만 나옴. 예약번호 나오는건 연구해 봐야할듯..?
		for(int i = 0; i<12; i++) {
			int ran_int = ran.nextInt(10);
			book_number += ran_int;
		}
		
		// if 회원이면 세션 따와서 코드.
		// 비회원이면 가장 마지막 번호 가져온 후 +1
		int customer_code;
		
		if(isUserChk == 1) customer_code = user_code;
		else customer_code = 0; // 번호 가져오는 메소드 만들어서 붙이기
		
		try {
			con = Util.getConnection();

			String sql = "insert into booking values(null, ?, "+ isUser + isGuest +"?,?,null,?,?,null,?,3)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book_name);
			pstmt.setInt(2, customer_code);
			pstmt.setString(3, book_checkin_date);
			pstmt.setString(4, book_checkout_date);
			pstmt.setInt(5, room_code);
			pstmt.setString(6, book_number);
			pstmt.setInt(7, isUserChk);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("예약 실패!");
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
	}
}
