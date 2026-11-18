package model.User;

import java.sql.*;
import java.util.*;
import DBPKG.*;



public class UserDAO {
   
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String sql = "";
	
	
//-----------------------------------------
// 	2026. 11. 4. 
	// 이메일을 확인하는 메서드
	public String getEmail(String email) {
		String chkmail="";
		
		try {
			con = Util.getConnection();
			// 신규회원용 이메일 중복 체크 추가
			String sql = "SELECT user_email FROM user "
			           + "WHERE user_email=? AND user_chk_del=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				chkmail = rs.getString("user_email");
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
		return chkmail;
	}

	// password를 확인하는 메서드
	public String getPass(String user_email) {
		String chkpass="";
		
		try {
			con = Util.getConnection();
			
			String sql = "select user_password from user where user_email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				chkpass = rs.getString("user_password");
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
		return chkpass;
	}
	
	// 새 회원의 데이터를 DB에 저장하는 메서드
	public void insertUser(UserDTO udto) {
		try {
			con = Util.getConnection();
			
			String sql = "insert into user values (null,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, udto.getUser_name());
			pstmt.setString(2, udto.getUser_phone());
			pstmt.setString(3, udto.getUser_email());
			pstmt.setString(4, udto.getUser_birth());
			pstmt.setInt(5, udto.getUser_gender());
			pstmt.setString(6, udto.getUser_password());
			pstmt.setInt(7, udto.getUser_chk_del());
			pstmt.setString(8, udto.getUser_passkey());
			
			int result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("인서트 성공");
			}
					
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			}catch(Exception e) { e.printStackTrace();}
		}
	}
	
	// 한 회원의 데이터를 조회하는 메서드
		public UserDTO getUserInfo(String user_email) {
			UserDTO udto = new UserDTO();

			try {
				con = Util.getConnection();
				
				String sql = "select * from user where user_email=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, user_email);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					udto.setUser_code(rs.getInt(1));
					udto.setUser_name(rs.getString(2));
					udto.setUser_phone(rs.getString(3));
					udto.setUser_email(rs.getString(4));
					udto.setUser_birth(rs.getString(5));
					udto.setUser_gender(rs.getInt(6));
					udto.setUser_password(rs.getString(7));
					udto.setUser_chk_del(rs.getInt(8));
					udto.setUser_passkey(rs.getString(9));
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
			return udto;
		}

		// 관리자 로그인 메서드
	public boolean loginAdminCheck(String email, String password) {
		// email 주소가 admin@naver.com이면 관리자 로그인으로 처리
		// 탈퇴회원 로그인 방지 추가
		
		try {
			con = Util.getConnection();
			
			String sql = "SELECT user_password FROM user "
		               + "WHERE user_email=? AND user_chk_del=0";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				if(rs.getString(1).equals(password)) {
					return true;
				}
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
		
		return false;
	}
	
		// 회원 로그인 메서드
		public boolean loginCheck(String email, String password) {
			// 탈퇴회원 로그인 방지 추가
			
			try {
				con = Util.getConnection();
				
				String sql = "SELECT user_password FROM user "
				           + "WHERE user_email=? AND user_chk_del=0";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, email);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()){
					if(rs.getString(1).equals(password)){
						return true;
					}
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
				
				return false;
			}

		// 회원 정보를 업데이트하는 메서드
	public void updateUserInfo(UserDTO udto) {
		
		try {
			con = Util.getConnection();
			
			String sql = "update user set user_name=?, user_phone=?, user_birth=?, user_gender=? where email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, udto.getUser_name());
			pstmt.setString(2, udto.getUser_phone());
			pstmt.setString(3, udto.getUser_birth());
			pstmt.setInt(4, udto.getUser_gender());
			pstmt.setString(5, udto.getUser_email());
			
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

	// 내 정보 정보를 업데이트하는 메서드 (비 비밀번호 변경)
	// 회원 정보를 업데이트하는 메서드 (비밀번호 변경)
	public void updateUserMod(String user_name, String user_phone, int user_gender, String user_birth, String new_password, String passkey, String user_email) {
		try {
			con = Util.getConnection();
			
			String sql = "update user set user_name=?, user_phone=?, user_gender=?, user_birth=?, user_password=?, user_passkey=? "
					+ "where user_email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_name);
			pstmt.setString(2, user_phone);
			pstmt.setInt(3, user_gender);
			pstmt.setString(4, user_birth);
			pstmt.setString(5, new_password);
			pstmt.setString(6, passkey);
			pstmt.setString(7, user_email);
			
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
	

	// 업데이트로 회원 탈퇴하기 (업데이트 방식)
	public void deleteMember(String user_email) {
		try {
			con = Util.getConnection();
			String sql = "update User set user_chk_del=1 where user_email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
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
	
	//-----------------------------------------------------------
	//2026. 11. 12
	// 비밀번호 찾기 - 회원정보 일치 여부 확인
	 public int passChangeCheck(String email, String phone, String passkey) {

	        String sql = "SELECT user_code FROM user "
	                + "WHERE user_email=? AND user_phone=? "
	                + "AND user_passkey=? AND user_chk_del=0";

	        try (Connection con = Util.getConnection();
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            pstmt.setString(1, email);
	            pstmt.setString(2, phone);
	            pstmt.setString(3, passkey);

	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("user_code");
	                }
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return 0;
	    }


	    // 비밀번호 찾기 - 새로운 비밀번호 설정
	    public boolean passChangeUpdate(int userCode, String newPassword) {

	        String sql = "UPDATE user SET user_password=? "
	                + "WHERE user_code=? AND user_chk_del=0";

	        try (Connection con = Util.getConnection();
	             PreparedStatement pstmt = con.prepareStatement(sql)) {

	            pstmt.setString(1, newPassword);
	            pstmt.setInt(2, userCode);

	            return pstmt.executeUpdate() == 1;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return false;
	    }
	
	
}