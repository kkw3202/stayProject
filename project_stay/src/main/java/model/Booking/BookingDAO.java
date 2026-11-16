package model.Booking;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;

import DBPKG.Util;
import model.Guest.GuestDAO;
import model.common.BookResultDTO;


public class BookingDAO {
	// 예약 집어넣기 리턴 하는 값은 예약번호.
	public String doBooking(int user_code, String book_checkin_date, String book_checkout_date, int room_code, String book_name, String phone) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Random ran = new Random();
		
		String book_number = ""; // 현재 상태에서는 12자리 난수만 나옴. 예약번호 나오는건 연구해 봐야할듯..?
		
		String isUser = user_code!=0 ? "?, " : "null, ";
		String isGuest = user_code==0 ? "?, " : "null, ";
		int isUserChk = user_code!=0 ? 1 : 0;
		
		// if 회원이면 세션 따와서 코드.
		// 비회원이면 가장 마지막 번호 가져온 후 +1
		int customer_code;
		if(isUserChk == 1) customer_code = user_code;
		else {
			GuestDAO gdao = new GuestDAO();
			
			boolean success = gdao.set_newGuset(book_name, phone);
			if(success) {
				customer_code = gdao.get_LastNum();
			}else {
				Exception e = new Exception();
				e.printStackTrace();
				return "false";
			}
			
		}
		
		try {
			con = Util.getConnection();
			boolean no_same_chk = true;
			
			String sql = "select count(*) from booking where book_number=?";
			while(no_same_chk) {
				book_number = "";
				for(int i = 0; i<12; i++) {
					int ran_int = ran.nextInt(10);
					book_number += ran_int;
				}
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, book_number);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					if(rs.getInt(1)==0)no_same_chk=false;
				}
				pstmt.close();
				pstmt = null;
			}
			
			

			sql = "insert into booking values(null, ?, "+ isUser + isGuest +"?,?,current_timestamp(),?,?,null,?,3)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book_name);
			pstmt.setInt(2, customer_code);
			pstmt.setString(3, book_checkin_date);
			pstmt.setString(4, book_checkout_date);
			pstmt.setInt(5, room_code);
			pstmt.setString(6, book_number);
			pstmt.setInt(7, isUserChk);

			pstmt.executeUpdate();
			
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
		return book_number;
	}
	
	// 예약번호로 예약을 조회하는 메소드
	public BookResultDTO get_one_BookResult(String book_number) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BookResultDTO result = new BookResultDTO();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sd2 = new SimpleDateFormat("yyyyMMdd");
		
		
		try {
			con = Util.getConnection();

			String sql = "select b.book_name, b.book_checkin_date, b.book_checkout_date, "
					+ "a.acc_name, r.room_name, r.room_price "
					+ "from booking b "
					+ "inner join room r on b.room_code = r.room_code "
					+ "inner join accommodation a on r.acc_code = a.acc_code "
					+ "where b.book_number=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, book_number);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				result.setBook_name(rs.getString(1));
				
				String rs_in_date =  sd2.format(sd.parse(rs.getString(2)));
				String rs_out_date = sd2.format(sd.parse(rs.getString(3)));
				
				result.setBook_checkin_date(rs_in_date);
				result.setBook_checkout_date(rs_out_date);
				
				
				result.setAcc_name(rs.getString(4));
				result.setRoom_name(rs.getString(5));
				result.setRoom_price(rs.getInt(6));
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
}
