package model.Inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.Util;
import model.User.UserDTO;

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
			String sql = "select count(*) from Inquiry where inq_chk_del=0 ";
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
		
	// 5. 1:1게시판에서 검색 키워드로 출력할때, 총 개수 구하는 메소드
	public int getInquirySearchCount(String searchType, String keyWord) {
		int count = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select count(*) "
					+ "from Inquiry i "
					+ "inner join User u "
					+ "on i.user_code = u.user_code "
					+ "where inq_chk_del=0  "
					+ "and " + searchType + " LIKE '%" + keyWord + "%'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count(*)");
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
	
	// 6. 1:1게시판에서, user_code 가져오는 함수
	public int getInquiryUserCode(String user_email) {
		int user_code=0;
		try {
			con = Util.getConnection();
			// user_code 검색하는 방법
			// UserDTO 인스턴스화
			UserDTO udto = new UserDTO();
			String sql = "select user_code from User where user_email= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user_code = rs.getInt("user_code");
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
	
	// 7. 1:1게시판에서 글쓰기 
	public void insertInquiryBoard(InquiryDTO idto, int user_code) {
		// ref, re_step : 초기값 지정
		int inq_ref=0;
		int inq_re_step=0;
		try {
			con = Util.getConnection();
			// ref의 최대값을 검색해서 +1을 하는 select문 필요
			String sql = "select max(inq_ref) from Inquiry";
			// 실행객체 생성
			pstmt = con.prepareStatement(sql);
			// 실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				// 찾은게 7이면 +1 을 해서 ref 는 8이 된다.
				inq_ref=rs.getInt(1)+1;
			}
			
			
			// 인서트 하는 쿼리문 시작
			String sql02 = "INSERT INTO Inquiry VALUES  (null, ?, current_date(), ?, ?, ?, ?, 0, 0) ";
			// 실행객체 생성
			pstmt = con.prepareStatement(sql02);
			// ?에 대입
			pstmt.setString(1, idto.getInq_title());
			pstmt.setInt(2, user_code);
			pstmt.setString(3, idto.getInq_content());
			pstmt.setInt(4, inq_ref);
			pstmt.setInt(5, inq_re_step);
			
			// 실행쿼리
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
	
	// 8. 내가쓴 글 게시글만 보기
	public ArrayList<InquiryDTO> getInquiryMylistBoard(int startRow, int pageSize, String user_name){
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
					+ "and user_name = ? "
					+ "order by inq_ref desc limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
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
	
	// 9. user_name 가져오는 메소드
	public String getInquiryUserName(String user_email) {
		String user_name="";
		try {
			con = Util.getConnection();
			// user_code 검색하는 방법
			// UserDTO 인스턴스화
			UserDTO udto = new UserDTO();
			String sql = "select user_name from User where user_email= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				user_name = rs.getString("user_name");
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
		return user_name;
	}	
	
	// 10. 해당글의 작성자 이메일 가져오는 함수
	public String getInquiryWriterEmail(int inq_code) {
		String writerEmail = "";
		try {
			con = Util.getConnection();
			// user_code 검색하는 방법
			// UserDTO 인스턴스화
			UserDTO udto = new UserDTO();
			String sql = "select user_email, inq_code from User u inner join Inquiry i "
					+ "on i.user_code = u.user_code "
					+ "where i.inq_code= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, inq_code);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				writerEmail = rs.getString("user_email");
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
		
		return writerEmail;
	}
}
