package model.place;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

import DBPKG.Util;
import model.Handler.User_change;
import model.Handler.dateHandler.DateHandlerDAO;
import model.common.search_accDTO;
import model.common.search_roomDTO;
import model.place.Room.RoomDAO;

public class placeDAO {
	// 검색결과에 필요한 기본적인 결과를 가져오는 메소드
	public ArrayList<search_accDTO> do_searching(String city_name, String acc_type, String acc_name, String book_checkin_date, String book_checkout_date) throws ParseException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<search_accDTO> result = new ArrayList<search_accDTO>();
		DateHandlerDAO dhdao = new DateHandlerDAO();
		ArrayList<String> dates = new ArrayList<String>();
		if(book_checkin_date != null && book_checkout_date != null) {
			dates = dhdao.getAllDay(book_checkin_date, book_checkout_date);
		}
		
		try {
			con = Util.getConnection();
			
			int cnt_notNull =0; // 
			int indexNum_chk = 1; // setString 에 쓸 ? 의 인덱스 넘버 카운트
			int cnt_dayQM = 0; // 날 구하는 서브서브쿼리의 ? 갯수 카운팅
			
			if(city_name != null && !city_name.isEmpty()) cnt_notNull++;
			if(acc_type != null && !acc_type.isEmpty()) cnt_notNull++;
			if(acc_name != null && !acc_name.isEmpty()) cnt_notNull++;
			
			String sql = "select a.acc_img, a.acc_type, a.acc_name, c.city_name, r.avg_ratings, orsq.acc_code, orsq.min_price, r.cnt_reviews "
					+ "from Accommodation a "
					+ "inner join City c on c.city_code = a.city_code "
					+ "left join (select acc_code, avg(rev_rating) avg_ratings, count(*) as cnt_reviews from reviews group by acc_code) r on r.acc_code = a.acc_code ";

				sql += "inner join "
						+ "(select acc_code, min(room_price) min_price " 
						+ "from room r " 
						+ "where ";	
				for (int i = 0; i < dates.size(); i++) {
					sql += "room_count > (select count(*) from booking b where b.room_code = r.room_code and book_checkin_date <= ? and book_checkout_date > ?) ";
					if(i+1 !=  dates.size()) sql += "and ";
					else sql += " ";
					cnt_dayQM += 2;
				}
				sql += "group by acc_code) AS orsq ON orsq.acc_code = a.acc_code ";
			System.out.println(sql);
			if(cnt_notNull != 0) {
				sql += "where ";
				if(city_name != null && !city_name.isEmpty()) {
					sql += "c.city_name=? ";
					cnt_notNull--;
					if(cnt_notNull != 0) sql += "and ";
				}
				if(acc_type != null && !acc_type.isEmpty()) {
					sql += "a.acc_type=? ";
					cnt_notNull--;
					if(cnt_notNull != 0) sql += "and ";
				}
				if(acc_name != null && !acc_name.isEmpty()) {
					sql += "a.acc_name like '%" + acc_name + "%' ";
				}
			}
			
			
			pstmt = con.prepareStatement(sql);
			if(book_checkin_date != null && book_checkout_date != null) {
				for (int i = 0; i < dates.size(); i++) {
				    pstmt.setString(indexNum_chk, dates.get(i));
				    indexNum_chk++;
				    pstmt.setString(indexNum_chk, dates.get(i));
				    indexNum_chk++;
				}
			}
			
			if(city_name != null && !city_name.isEmpty()) {
				pstmt.setString(indexNum_chk, city_name);
				indexNum_chk++;
			}
			if(acc_type != null && !acc_type.isEmpty()) pstmt.setString(indexNum_chk, acc_type);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				search_accDTO acdto = new search_accDTO();
				acdto.setAcc_img(rs.getString(1));
				acdto.setAcc_type(rs.getString(2));
				acdto.setAcc_name(rs.getString(3));
				acdto.setCity_name(rs.getString(4));
				acdto.setAvg_rating((rs.getDouble(5)));;
				acdto.setAcc_code(rs.getInt(6));
				acdto.setRoom_price(rs.getInt(7));
				acdto.setCnt_reviews(rs.getInt(8));
				result.add(acdto);
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
