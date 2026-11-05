package model.place.Room;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import DBPKG.Util;
import model.Handler.dateHandler.DateHandlerDAO;

public class RoomDAO {

	// 방(객실) 하나가 특정 날짜에 몇개 예약 가능한지 구하는 메소드
	// 방을 예약할 수 있다면 예약 가능한 값을 반환.
	// 예약 할 수 없다면-1 반환.
	public int count_booking_oneRoom(String book_checkin_date, String book_checkout_date, int room_code)
			throws ParseException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DateHandlerDAO dhdao = new DateHandlerDAO();
		ArrayList<String> dates = dhdao.getAllDay(book_checkin_date, book_checkout_date);

		int result = 999;

		try {
			con = Util.getConnection();
			int search_room_count = 0;

			// 객실 특정
			String sql = "select room_count " + "from room " + "where room_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, room_code);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				search_room_count = rs.getInt(1);
			}

			pstmt.close();
			rs.close();

			sql = "select ";

			for (int i = 0; i < dates.size(); i++) {
				sql += "(select count(*) from booking where book_checkin_date <= ? and book_checkout_date > ?)"; // 1, 2 => 검색할 날짜
				if (i + 1 != dates.size()) sql += ", ";
				else sql += " ";
			}
			sql += "from booking where room_code = ?"; // 3 => 내가 검색할 방

			int index_cnt = 1;
			for (String oneDay : dates) {
				pstmt.setString(index_cnt, oneDay);
				index_cnt++;
				pstmt.setString(index_cnt, oneDay);
			}
			pstmt.setInt(index_cnt, room_code);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				for (String oneDay : dates) {
					int can_room_cnt = rs.getInt(oneDay);
					if (can_room_cnt > search_room_count)
						return -1;
					if (can_room_cnt < result)
						result = can_room_cnt;
				}
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

	// 특정 날짜에 예매 가능한 room_code 와 해당 객실을 가진 숙소인 acc_code를 찾아 담는 메소드
	public ArrayList<RoomDTO> get_booking_date_room(String book_checkin_date, String book_checkout_date)
			throws ParseException {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DateHandlerDAO dhdao = new DateHandlerDAO();
		ArrayList<String> dates = dhdao.getAllDay(book_checkin_date, book_checkout_date);

		ArrayList<RoomDTO> result = new ArrayList<RoomDTO>();

		try {
			con = Util.getConnection();

			// 객실 특정
			String sql = "select room_code ,acc_code" 
						+ "from room " 
						+ "where ";	
			for (int i = 0; i < dates.size(); i++) {
				sql += "room_count > (select count(*) from booking where book_checkin_date <= ? and book_checkout_date > ?)";
				if(i+1 !=  dates.size()) sql += "and ";
			}
				// ============================== 제작중!===========================
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
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
