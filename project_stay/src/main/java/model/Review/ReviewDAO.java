package model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.Util;
import model.Inquiry.InquiryDTO;
import model.place.Accommodation.AccommodationDTO;

public class ReviewDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1. 리뷰게시판, 해당 숙소에 해당하는 전체 리뷰 불러오는 메소드
	public ArrayList<ReviewDTO> getReviewAllBoard(int startRow, int pageSize){
		ArrayList<ReviewDTO> r = new ArrayList<ReviewDTO>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select r.rev_code, a.acc_code, u.user_code, r.rev_rating, r.rev_content, "
					+ "r.rev_created_datetime, r.rev_chk_del, r.rev_like, u.user_name, u.user_email, a.acc_name from Reviews r "
					+ "inner join User u "
					+ "on r.user_code = u.user_code "
					+ "inner join Accommodation a "
					+ "on r.acc_code = a.acc_code "
					+ "where rev_chk_del=0 "
					+ "order by rev_code desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO rdto = new ReviewDTO();
				rdto.setRev_code(rs.getInt(1));
				rdto.setAcc_code(rs.getInt(2));
				rdto.setUser_code(rs.getInt(3));
				rdto.setRev_rating(rs.getInt(4));
				rdto.setRev_content(rs.getString(5));
				rdto.setRev_created_datetime(rs.getString(6));
				rdto.setRev_chk_del(rs.getInt(7));
				rdto.setRev_like(rs.getInt(8));
				rdto.setUser_name(rs.getString(9));
				rdto.setUser_email(rs.getString(10));
				rdto.setAcc_name(rs.getString(11));
				
				// ArrayList에 데이터 추가
				r.add(rdto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		
		return r;
	}
	
	// 2. 콤보박스 숙소목록 가져오기 (AccommodationDTO 사용)
	public ArrayList<AccommodationDTO> getAccList(){
		ArrayList<AccommodationDTO> accList = new ArrayList<>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select acc_name, acc_code from Accommodation order by acc_code asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AccommodationDTO adto = new AccommodationDTO();
				adto.setAcc_name(rs.getString(1));
				adto.setAcc_code(rs.getInt(2));
				
				// ArrayList에 데이터 추가
				accList.add(adto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return accList;
	}
	
	// 전체 리뷰개수 세기 (rev_chk_del=1인거 제외)
	public int countTotalReview() {
		int totalCount = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select count(*) acc_code from Reviews where rev_chk_del=0";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				totalCount = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		
		return totalCount;
	}
	
	// 해당 숙소의 전체 리뷰개수 세기 (rev_chk_del =1 인거 (삭제된거) 빼고)
	public int countAccReview(int acc_code) {
		int count = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select count(*), acc_code from Reviews where rev_chk_del=0 and acc_code= ?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setInt(1, acc_code);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		
		return count;
	}
	
	// 콤보박스에서 선택한 숙소에 해당하는 리뷰만 가져오는 메소드
	public ArrayList<ReviewDTO> getReviewAccBoard(int startRow, int pageSize, int acc_code){
		ArrayList<ReviewDTO> r = new ArrayList<>();
		
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select r.rev_code, a.acc_code, u.user_code, r.rev_rating, r.rev_content, "
					+ "r.rev_created_datetime, r.rev_chk_del, r.rev_like, u.user_name, u.user_email, a.acc_name from Reviews r "
					+ "inner join User u "
					+ "on r.user_code = u.user_code "
					+ "inner join Accommodation a "
					+ "on r.acc_code = a.acc_code "
					+ "where rev_chk_del=0 and a.acc_code=? "
					+ "order by rev_code desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, acc_code);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO rdto = new ReviewDTO();
				rdto.setRev_code(rs.getInt(1));
				rdto.setAcc_code(rs.getInt(2));
				rdto.setUser_code(rs.getInt(3));
				rdto.setRev_rating(rs.getInt(4));
				rdto.setRev_content(rs.getString(5));
				rdto.setRev_created_datetime(rs.getString(6));
				rdto.setRev_chk_del(rs.getInt(7));
				rdto.setRev_like(rs.getInt(8));
				rdto.setUser_name(rs.getString(9));
				rdto.setUser_email(rs.getString(10));
				rdto.setAcc_name(rs.getString(11));
				
				// ArrayList에 데이터 추가
				r.add(rdto);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		
		return r;
	}
	
	// 좋아요 계산하는 메소드
	public void updateReviewLike(int rev_code) {
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "update Reviews set rev_like = rev_like+1 where rev_code = ?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setInt(1, rev_code);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	// 리뷰 삭제처리하는 메소드 (소프트삭제)
	public void deleteReview(int rev_code) {
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "update Reviews set rev_chk_del = 1 where rev_code = ?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setInt(1, rev_code);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	// 회원명을 찾는 메소드
	public String getUser_name(String user_email) {
		String user_name = "";
		try {
			con = Util.getConnection();
			
			String sql = "select u.user_name from User u inner join Reviews r "
					+ "on u.user_code = r.user_code "
					+ "where u.user_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user_name = rs.getString(1);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return user_name;
	}
	
	// 해당 숙소 이름 가져오는 메소드
	public String getAccName(int acc_code) {
		String acc_name = "";
		try {
			con = Util.getConnection();
			String sql = "select acc_name from Accommodation where acc_code = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, acc_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				acc_name = rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return acc_name;
	}
	
	// 리뷰게시판 글 수정하는 함수
	public void updateReview(int rev_code, String rev_title, String rev_content) {
		try {
			con = Util.getConnection();
			String sql = "update Reviews set rev_title=?, rev_content=? where rev_code=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, rev_title);
			pstmt.setString(2, rev_content);
			pstmt.setInt(3, rev_code);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	// 내가쓴 리뷰만 보기
	// 8. 내가쓴 글 게시글만 보기
	public ArrayList<ReviewDTO> getMyreviewBoard(int startRow, int pageSize, String user_name){
		ArrayList<ReviewDTO> r = new ArrayList<>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select r.rev_code, a.acc_code, u.user_code, r.rev_rating, r.rev_content, "
					+ "r.rev_created_datetime, r.rev_chk_del, r.rev_like, u.user_name, u.user_email, a.acc_name from Reviews r "
					+ "inner join User u "
					+ "on r.user_code = u.user_code "
					+ "inner join Accommodation a "
					+ "	on r.acc_code = a.acc_code "
					+ "where rev_chk_del=0 and u.user_name = ? "
					+ "order by rev_code desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO rdto = new ReviewDTO();
				rdto.setRev_code(rs.getInt(1));
				rdto.setAcc_code(rs.getInt(2));
				rdto.setUser_code(rs.getInt(3));
				rdto.setRev_rating(rs.getInt(4));
				rdto.setRev_content(rs.getString(5));
				rdto.setRev_created_datetime(rs.getString(6));
				rdto.setRev_chk_del(rs.getInt(7));
				rdto.setRev_like(rs.getInt(8));
				rdto.setUser_name(rs.getString(9));
				rdto.setUser_email(rs.getString(10));
				rdto.setAcc_name(rs.getString(11));
				
				// ArrayList에 데이터 추가
				r.add(rdto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return r;
	}
	
	// 내가쓴 게시글의 숫자 계산하는 메소드
	public int getMyreviewCount(String user_name) {
		int count = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select count(*), u.user_name from Reviews r inner join User u "
					+ "on r.user_code = u.user_code where rev_chk_del=0 and user_name= ?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setString(1, user_name);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return count;
	}
	// 유저코드 가져오는 메소드
	public int getUserCode(String user_email) {
		int user_code = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select user_code from User where user_email =?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				user_code = rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return user_code;
	}
	
	// 리뷰 수정하는 메소드
	public void updateReview(int rev_code, int rev_rating, String rev_content) {
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "update Reviews set rev_rating=?, rev_content =?  where rev_code = ?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setInt(1, rev_code);
			pstmt.setInt(2, rev_rating);
			pstmt.setString(3, rev_content);
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	// 해당 글만 가져오는 메소드
	public ReviewDTO getReviewOneBoard(int rev_code) {
		ReviewDTO rdto = new ReviewDTO();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select * from reviews where rev_code=?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setInt(1, rev_code);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				rdto.setRev_code(rs.getInt(1));
				rdto.setAcc_code(rs.getInt(2));
				rdto.setUser_code(rs.getInt(3));
				rdto.setRev_rating(rs.getInt(4));
				rdto.setRev_content(rs.getString(5));
				rdto.setRev_created_datetime(rs.getString(6));
				rdto.setRev_chk_del(rs.getInt(7));
				rdto.setRev_like(rs.getInt(8));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
		return rdto;
	}
}
