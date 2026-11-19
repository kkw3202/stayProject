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
	
	// 1. 1:1 게시판 전체리스트를 가져오는 메서드
	public ArrayList<InquiryDTO> getInquiryAllBoard(int startRow, int pageSize){
		ArrayList<InquiryDTO> i = new ArrayList<>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
					+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del, u.user_email "
					+ "from inquiry i "
					+ "inner join user u "
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
				idto.setUser_email(rs.getString(10));
				
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
	
	// 2. 1:1게시판 전체 갯수를 구하는 메서드
	public int getInquiryAllCount() {
		int count = 0;
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select count(*) from inquiry where inq_chk_del=0 ";
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
	
	// 3. 1:1게시판 상세 페이지, 해당 게시글 불러오는 메서드
	public InquiryDTO getInquiryOneBoard(int inq_code) {
		InquiryDTO idto = new InquiryDTO();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
					+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del, i.user_code "
					+ "from inquiry i "
					+ "inner join user u "
					+ "on i.user_code = u.user_code "
					+ "where inq_code=?";
			pstmt = con.prepareStatement(sql);
			// ?에 값세팅하기
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
				idto.setUser_code(rs.getInt(10));
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
	
	// 4. 1:1게시판에서 검색 키워드로 검색하는 메서드
	public ArrayList<InquiryDTO> getInquirySearchBoard(int startRow, int pageSize, String searchType, String keyWord){
		ArrayList<InquiryDTO> i = new ArrayList<>();
		try {
			con = Util.getConnection();
			// searchType이 user_name이면 u.user_name으로, 아니면 i.칼럼명으로 설정
			String sType = searchType.equals("user_name") ? "u." + searchType : "i." + searchType;
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
					+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del, u.user_email "
					+ "from inquiry i "
					+ "inner join user u "
					+ "on i.user_code = u.user_code "
					+ "where inq_chk_del=0  "
					+ "and " + sType + " LIKE '%" + keyWord + "%'"
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
				idto.setUser_email(rs.getString(10));
				
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
		
	// 5. 1:1게시판에서 검색 키워드로 검색할 때, 총 갯수 구하는 메서드
	public int getInquirySearchCount(String searchType, String keyWord) {
		int count = 0;
		try {
			con = Util.getConnection();
			// searchType이 user_name이면 u.user_name으로, 아니면 i.칼럼명으로 설정
			String sType = searchType.equals("user_name") ? "u." + searchType : "i." + searchType;
			// 쿼리문
			String sql = "select count(*) "
					+ "from inquiry i "
					+ "inner join user u "
					+ "on i.user_code = u.user_code "
					+ "where inq_chk_del=0  "
					+ "and " + sType + " LIKE '%" + keyWord + "%'";
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
			// user_code 검색하는 구문
			// UserDTO 인스턴스화
			UserDTO udto = new UserDTO();
			String sql = "select user_code from user where user_email= ?";
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
		// ref, re_step : 초기값 설정
		int inq_ref=0;
		int inq_re_step=0;
		try {
			con = Util.getConnection();
			// ref의 최대값을 검색해서 +1을 하는 select문이 필요
			String sql = "select max(inq_ref) from inquiry";
			// 실행객체 생성
			pstmt = con.prepareStatement(sql);
			// 실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				// 찾은값이 7이면 +1 을 해서 ref 값 8이 된다.
				inq_ref=rs.getInt(1)+1;
			}
			
			
			// 인서트 하는 쿼리문 작성
			String sql02 = "INSERT INTO inquiry VALUES  (null, ?, now(), ?, ?, ?, ?, 0, 0) ";
			// 실행객체 생성
			pstmt = con.prepareStatement(sql02);
			// ?에 매핑
			pstmt.setString(1, idto.getInq_title());
			pstmt.setInt(2, user_code);
			pstmt.setString(3, idto.getInq_content());
			pstmt.setInt(4, inq_ref);
			pstmt.setInt(5, inq_re_step);
			
			// 데이터베이스 실행
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
	
	// 8. 내글만 게시글만 출력
	public ArrayList<InquiryDTO> getInquiryMylistBoard(int startRow, int pageSize, String user_name){
		ArrayList<InquiryDTO> i = new ArrayList<>();
		try {
			con = Util.getConnection();
			// 쿼리문
			String sql = "select i.inq_code, i.inq_title, u.user_name, i.inq_created_datetime, "
					+ "i.inq_chk_status, i.inq_ref, i.inq_re_step, i.inq_content, i.inq_chk_del "
					+ "from inquiry i "
					+ "inner join user u "
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
	
	// 9. user_name 가져오는 메서드
	public String getInquiryUserName(String user_email) {
		String user_name="";
		try {
			con = Util.getConnection();
			// user_code 검색하는 구문
			// UserDTO 인스턴스화
			UserDTO udto = new UserDTO();
			String sql = "select user_name from user where user_email= ?";
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
	
	// 10. 작성자 이메일 가져오는 함수
	public String getInquiryWriterEmail(int inq_code) {
		String writerEmail = "";
		try {
			con = Util.getConnection();
			// user_code 검색하는 구문
			// UserDTO 인스턴스화
			UserDTO udto = new UserDTO();
			String sql = "select user_email, inq_code from user u inner join inquiry i "
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
	
	// 11. 작성글 수정, 업데이트하는 함수
	public void updateInquiry(int inq_code, String inq_title, String inq_content) {
		try {
			// DB연동
			con = Util.getConnection();
			// 조회된 데이터 수정하는 쿼리문 작성 업데이트
			String sql = "update inquiry set inq_title=?, inq_content=? where inq_code=?";
			// 쿼리문 실행 객체 생성
			pstmt = con.prepareStatement(sql);
			// ? 값주기
			pstmt.setString(1, inq_title);
			pstmt.setString(2, inq_content);
			pstmt.setInt(3, inq_code);
			// 쿼리 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	// 12. 1:1문의글 삭제하기 (업데이트방식)
	public void deleteBoard(int inq_code) {
		try {
			// DB연동
			con = Util.getConnection();
			// 데이터를 지우지 않고, chkDel을 1로 업데이트
			String sql = "update inquiry set inq_chk_del=1 where inq_code=?";
			// 쿼리 실행
			pstmt = con.prepareStatement(sql);
			// ? 값 주기
			pstmt.setInt(1, inq_code);
			// 쿼리 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			}catch(Exception e) {e.printStackTrace();}
		}
	}
	
	// 13. 관리자 답변달기 메서드
	public void rewriteInquiry(InquiryDTO idto, int inq_code) {
			
		// 원본 ref 가져오기
		int inq_ref = idto.getInq_ref();
		// 1:1 문의 이므로 답변의 re_step은 기본적으로 1로 세팅한다.
		int inq_re_step = 1;
		// 매개변수로 넘어온 번호를 확인하는 코드
		System.out.println("inq_ref -> DB :" + inq_ref);
		System.out.println("inq_re_step -> DB :" + inq_re_step);
		
		try {
			con = Util.getConnection();
			// 답변상태도 1(답변완료) 처리 (질문글 두번째, chk_status = 1)
			String sql = "insert into inquiry values (null, ?, now(), ?, ?, ?, ?, 1, 0)";
			// 실행객체 생성
			pstmt = con.prepareStatement(sql);
			// ?에 매핑
			pstmt.setString(1, idto.getInq_title());
			pstmt.setInt(2, idto.getUser_code());
			pstmt.setString(3, idto.getInq_content());
			pstmt.setInt(4, inq_ref);
			pstmt.setInt(5, inq_re_step);
			// 데이터베이스
			pstmt.executeUpdate();
			
			pstmt.close();
			
			// 원래의 질문글도 답변완료로 처리
			String sql02 = "update inquiry set inq_chk_status = 1 where inq_code = ?";
			pstmt = con.prepareStatement(sql02);
			// ?에 매핑
			pstmt.setInt(1, inq_code);
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
	
	// 14. 
	
}