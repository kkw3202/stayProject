package model.place.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;

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
			
			pstmt = null;
			rs = null;

			sql = "select count(*) from booking where(";

			for (int i = 0; i < dates.size(); i++) {
				sql += "(book_checkin_date <= '"+dates.get(i)+"' and book_checkout_date > '"+dates.get(i)+"')"; // 1, 2 => 검색할 날짜
				if (i + 1 != dates.size()) sql += "or ";
				else sql += " ";
			}
			sql += ")and room_code="+room_code; // 3 => 내가 검색할 방
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {	
				for (int i=0; i<dates.size(); i++) {
					int can_room_cnt = rs.getInt(1);
					if (can_room_cnt > search_room_count)
						return -1;
					if (can_room_cnt < result)
						result = search_room_count-can_room_cnt;
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

	// 특정 날짜에 예매 가능한 객실 중, 최저가인 객실을 찾고, 
	// 해당 방의 room_code, room_price 와 해당 객실을 가진 숙소인 acc_code를 찾아 담는 메소드
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
			String sql = "select room_code ,acc_code, room_price" 
						+ "from room r " 
						+ "where ";	
			for (int i = 0; i < dates.size(); i++) {
				sql += "room_count > (select count(*) from booking b where b.room_code = r.room_code and book_checkin_date <= ? and book_checkout_date > ?)";
				if(i+1 !=  dates.size()) sql += "and ";
				else sql += " ";
			}
			sql += "order by room_price asc limit 1";
			pstmt = con.prepareStatement(sql);
			
			for (int i = 0; i < dates.size(); i++) {
				pstmt.setString((i*2)+1, book_checkin_date);
				pstmt.setString((i*2)+2, book_checkout_date);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RoomDTO rdto = new RoomDTO();
				rdto.setRoom_code(1);
				rdto.setAcc_code(2);
				rdto.setRoom_price(3);
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
	
	// 1108 추가분
		// 특정 날짜에 예약 가능한 숙소의 모든 방, 그 정보를 담는 메소드
			public ArrayList<RoomDTO> getAllRoom_acc(String book_checkin_date, String book_checkout_date, int acc_code) {
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				ArrayList<RoomDTO> result = new ArrayList<RoomDTO>();
				try {
					con = Util.getConnection();

					String sql = "select * from room where acc_code=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, acc_code);

					rs = pstmt.executeQuery();
					
					RoomDTO img = new RoomDTO(); // 빈 방 없을 때 대비용 변수
					
					int roomCnt=0;
					while(rs.next()) {
						RoomDTO rdto = new RoomDTO();
						if(count_booking_oneRoom(book_checkin_date,book_checkout_date,rs.getInt(1)) > 0) {
							rdto.setRoom_code(rs.getInt(1));
							rdto.setRoom_name(rs.getString(3));
							rdto.setRoom_price(rs.getInt(4));
							rdto.setRoom_capacity(rs.getInt(6));
							rdto.setRoom_info(rs.getString(7));
							rdto.setRoom_amenity(rs.getString(8));
							rdto.setRoom_img1(rs.getString(10));
							rdto.setRoom_img2(rs.getString(11));
							rdto.setRoom_img3(rs.getString(12));
							rdto.setRoom_img4(rs.getString(13));
							rdto.setRoom_count(count_booking_oneRoom(book_checkin_date,book_checkout_date,rs.getInt(1)));
							roomCnt++;
							result.add(rdto);
						}
						if(roomCnt==0) {
							img.setRoom_img1(rs.getString(10));
							img.setRoom_img2(rs.getString(11));
							img.setRoom_img3(rs.getString(12));
							img.setRoom_img4(rs.getString(13));
						}
					}
					// 빈 방이 없어서 배열에 안담기면 첫 방 배열 추가해서 매인 이미지만 띄우는 용도
					if(result.size()==0) {
						result.add(img);
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
