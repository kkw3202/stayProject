package model.WishList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.Util;
import model.Handler.User_change;


public class WishListDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 유저 한명의 찜목록을 전부 가져오는 메소드
	public ArrayList<Integer> getWishList(String user_email){
		
		User_change uc = new User_change();
		int user_code = uc.email_to_code(user_email);
		
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
	
	// mypage에 그릴 내 찜 목록 메소드
	public ArrayList<WishListDTO> myWishList(String user_email, int startRow, int pageSize){
		ArrayList<WishListDTO> wlist = new ArrayList<WishListDTO>();
		try {
			con = Util.getConnection();
			// sql문
			String sql = "select w.wish_code, w.user_code, w.acc_code, u.user_email, a.acc_name, a.acc_type, "
					+ "a.acc_address, a.acc_facility, a.acc_checkinTime, a.acc_checkoutTime, a.acc_chk_del, a.acc_img "
					+ "from wishlist w inner join user u on w.user_code = u.user_code "
					+ "inner join accommodation a on w.acc_code = a.acc_code "
					+ "where u.user_email = ? "
					+ "and a.acc_chk_del = 0 "
					+ "order by w.wish_code desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			// ? 에 대입
			pstmt.setString(1, user_email);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				WishListDTO wdto = new WishListDTO();
				wdto.setWish_code(rs.getInt(1));
				wdto.setUser_code(rs.getInt(2));
				wdto.setAcc_code(rs.getInt(3));
				wdto.setUser_email(rs.getString(4));
				wdto.setAcc_name(rs.getString(5));
				wdto.setAcc_type(rs.getString(6));
				wdto.setAcc_address(rs.getString(7));
				wdto.setAcc_facility(rs.getString(8));
				wdto.setAcc_checkinTime(rs.getString(9));
				wdto.setAcc_checkoutTime(rs.getString(10));
				wdto.setAcc_chk_del(rs.getInt(11));
				wdto.setAcc_img(rs.getString(12));
				
				wlist.add(wdto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
				if(con!=null) {con.close();}
			}catch(Exception e) {e.printStackTrace();}
		}
		return wlist;
	}
	
	// mypage에 그릴 내 찜 목록 전체 개수 세는 메소드
	public int countMyWishlist(String user_email) {
		int count = 0;
		try {
			con = Util.getConnection();
			// sql문
			String sql = "select count(*) from wishlist w inner join user u "
					+ "on w.user_code = u.user_code "
					+ "inner join accommodation a on w.acc_code = a.acc_code "
					+ "where u.user_email = ? and a.acc_chk_del = 0 ";
			pstmt = con.prepareStatement(sql);
			// ? 에 대입
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
				if(con!=null) {con.close();}
			}catch(Exception e) {e.printStackTrace();}
		}	
		return count;
	}
	
	// 내 찜 목록 삭제하기 (소프트 삭제 아님)
	public void deleteMywish(int wish_code) {
		try {
			con = Util.getConnection();
			// sql문
			String sql = "delete from wishlist where wish_code = ?";
			pstmt = con.prepareStatement(sql);
			// ? 에 대입
			pstmt.setInt(1, wish_code);
			// 삭제실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
				if(con!=null) {con.close();}
			}catch(Exception e) {e.printStackTrace();}
		}	
	}
}
