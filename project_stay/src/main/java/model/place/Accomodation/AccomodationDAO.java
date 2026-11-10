package model.place.Accomodation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import DBPKG.Util;
import model.place.placeDTO;

public class AccomodationDAO {
	// 1108 추가분
	// 숙소 하나의 상세정보 메소드
	public placeDTO getOne_accInfo(int acc_code) {
		placeDTO pdto = new placeDTO();
		pdto.setAcc_code(acc_code);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = Util.getConnection();
			
			String sql = "select a.city_code, acc_type, acc_name, acc_address, acc_facility, acc_checkinTime, acc_checkoutTime, "
					+ "acc_img, city_name, avg(r.rev_rating) "
					+ "from accommodation a "
					+ "inner join city c on c.city_code = a.city_code "
					+ "left join reviews r on r.acc_code = a.acc_code "
					+ "where a.acc_code=? "
					+ "group by a.acc_code";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, acc_code);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pdto.setCity_code(rs.getInt(1));
				pdto.setAcc_type(rs.getString(2));
				pdto.setAcc_name(rs.getString(3));
				pdto.setAcc_address(rs.getString(4));
				pdto.setAcc_facility(rs.getString(5));
				pdto.setAcc_checkinTime(rs.getString(6));
				pdto.setAcc_checkoutTime(rs.getString(7));
				pdto.setAcc_img(rs.getString(8));
				pdto.setCity_name(rs.getString(9));
				pdto.setAvg_rating(rs.getDouble(10));
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
		return pdto;
		
	}
}
