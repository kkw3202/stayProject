package model.Inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.Util;

public class InquiryDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 1. 1:1 게시판 전체리스트에 정보 넣는 메소드
	public ArrayList<InquiryDTO> getInquiryAllBoard(int startRow, int pageSize){
		ArrayList<InquiryDTO> i = new ArrayList<>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
					+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del "
					+ "from Inquiry i "
					+ "inner join User u "
					+ "on i.user_code = u.user_code "
					+ "where inq_chk_del=0 "
					+ "order by inq_ref desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				InquiryDTO idto = new InquiryDTO();
				idto.setInq_code(rs.getInt(1));
				idto.setInq_title(rs.getString(2));
				idto.setUser_name(rs.getString(3));
				idto.setInq_created_datetime(rs.getString(4));
				idto.setInq_chk_status(rs.getInt(5));
				idto.setInq_ref(rs.getInt(6));
				idto.setInq_re_step(rs.getInt(7));
				idto.setInq_content(rs.getString(8));
				idto.setInq_chk_del(rs.getInt(9));
				
				// ArrayList에 데이터 추가
				i.add(idto);
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
		return i;
	}
	
	// 2. 1:1게시판 전체 개수를 리턴하는 메소드
	public int getInquiryAllCount() {
		int count = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select count(*) "
					+ "from Inquiry i "
					+ "inner join User u "
					+ "on i.user_code = u.user_code "
					+ "where inq_chk_del=0 ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
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
	
	// 3. 1:1게시판 제목 눌렀을때, 해당 게시글 불러오는 메소드
	public InquiryDTO getInquiryOneBoard(int inq_code) {
		InquiryDTO idto = new InquiryDTO();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
					+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del "
					+ "from Inquiry i "
					+ "inner join User u "
					+ "on i.user_code = u.user_code "
					+ "where inq_code=?";
			pstmt = con.prepareStatement(sql);
			// ?에 대입하기
			pstmt.setInt(1, inq_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				idto.setInq_code(rs.getInt(1));
				idto.setInq_title(rs.getString(2));
				idto.setUser_name(rs.getString(3));
				idto.setInq_created_datetime(rs.getString(4));
				idto.setInq_chk_status(rs.getInt(5));
				idto.setInq_ref(rs.getInt(6));
				idto.setInq_re_step(rs.getInt(7));
				idto.setInq_content(rs.getString(8));
				idto.setInq_chk_del(rs.getInt(9));
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
	
		return idto;
	}
	
	// 4. 1:1게시판에서 검색 키워드로 출력하는 메소드
		public ArrayList<InquiryDTO> getInquirySearchBoard(int startRow, int pageSize, String searchType, String keyWord){
			ArrayList<InquiryDTO> i = new ArrayList<>();
			try {
				con = Util.getConnection();
				// 쿼리문
				String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
						+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del "
						+ "from Inquiry i "
						+ "inner join User u "
						+ "on i.user_code = u.user_code "
						+ "where inq_chk_del=0  "
						+ "and " + searchType + " LIKE '%" + keyWord + "%'"
						+ "order by inq_ref desc limit ?,?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, pageSize);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					InquiryDTO idto = new InquiryDTO();
					idto.setInq_code(rs.getInt(1));
					idto.setInq_title(rs.getString(2));
					idto.setUser_name(rs.getString(3));
					idto.setInq_created_datetime(rs.getString(4));
					idto.setInq_chk_status(rs.getInt(5));
					idto.setInq_ref(rs.getInt(6));
					idto.setInq_re_step(rs.getInt(7));
					idto.setInq_content(rs.getString(8));
					idto.setInq_chk_del(rs.getInt(9));
					
					// ArrayList에 데이터 추가
					i.add(idto);
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
			return i;
		}
		
		// 4. 1:1게시판에서 검색 키워드로 출력할때, 총 개수 구하는 메소드
		public int getInquirySearchCount() {
			int count = 0;
			try {
				con = Util.getConnection();
				// 쿼리문
				String sql = "select count(*) from Inquiry";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
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
		
		
		
}
