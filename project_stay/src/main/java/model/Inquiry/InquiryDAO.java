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
	
	// 1. 1:1 게시판 정보 넣는 메소드
	public ArrayList<InquiryDTO> getInquiryAllBoard(int startRow, int pageSize){
		ArrayList<InquiryDTO> i = new ArrayList<>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, i.inq_chk_status "
					+ "from Inquiry i "
					+ "inner join User u "
					+ "on i.user_code = u.user_code limit ?,?";
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
