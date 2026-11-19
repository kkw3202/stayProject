package model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import DBPKG.Util;

public class MyBookingDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// ȸ���� ���� ����Ʈ�� ��ȸ�ϴ� �޼ҵ�
	public ArrayList<MyBookingDTO> getMyBookingList(int startRow, int pageSize, String user_email){
		ArrayList<MyBookingDTO> mblist = new ArrayList<>();
		try {
			con = Util.getConnection();
			// sql��
			String sql = "select b.book_code, b.book_number, b.book_name, b.user_code, b.room_code, "
					+ "b.book_checkin_date, b.book_checkout_date, b.book_date, b.book_cancel_date, "
					+ "b.book_chk_status, r.room_name, r.room_price, "
					+ "a.acc_code, a.acc_name, a.acc_type, a.acc_address, a.acc_checkinTime, a.acc_checkoutTime, "
					+ "a.acc_img, rev.rev_code, rev.rev_created_datetime, rev.rev_chk_del "
					+ "from booking b inner join user u "
					+ "on b.user_code = u.user_code "
					+ "inner join room r "
					+ "on b.room_code = r.room_code "
					+ "inner join accommodation a "
					+ "on r.acc_code = a.acc_code "
					+ "left join reviews rev "
					+ "on a.acc_code = rev.acc_code and u.user_code = rev.user_code and rev.rev_chk_del = 0 "
					+ "where u.user_email = ? "
					+ "order by b.book_code desc "
					+ "limit ?,?";
			pstmt = con.prepareStatement(sql);
			// ?�� ����
			pstmt.setString(1, user_email);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
			// rs ����
			rs = pstmt.executeQuery();
			while(rs.next()){
				// mbdto ����
				MyBookingDTO mbdto = new MyBookingDTO();
				mbdto.setBook_code(rs.getInt(1));
				mbdto.setBook_number(rs.getString(2));
				mbdto.setBook_name(rs.getString(3));
				mbdto.setUser_code(rs.getInt(4));
				mbdto.setRoom_code(rs.getInt(5));
				mbdto.setBook_checkin_date(rs.getString(6));
				mbdto.setBook_checkout_date(rs.getString(7));
				mbdto.setBook_date(rs.getString(8));
				mbdto.setBook_cancel_date(rs.getString(9));
				mbdto.setBook_chk_status(rs.getInt(10));
				mbdto.setRoom_name(rs.getString(11));
				mbdto.setRoom_price(rs.getInt(12));
				mbdto.setAcc_code(rs.getInt(13));
				mbdto.setAcc_name(rs.getString(14));
				mbdto.setAcc_type(rs.getString(15));
				mbdto.setAcc_address(rs.getString(16));
				mbdto.setAcc_checkinTime(rs.getString(17));
				mbdto.setAcc_checkoutTime(rs.getString(18));
				mbdto.setAcc_img(rs.getString(19));
				mbdto.setRev_code(rs.getInt(20));
				mbdto.setRev_created_datetime(rs.getString(21));
				mbdto.setRev_chk_del(rs.getInt(22));
				
				//�迭�� �߰�
				mblist.add(mbdto);
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
		return mblist;
	}
	
	// �� �����Ѱ� �� ���� ���� �޼ҵ�
	public int countAllMyBooking(String user_email) {
		int count = 0;
		try{
			// db ����
			con = Util.getConnection();
			// sql���ۼ�
			String sql = "select count(*) from booking b "
					+ "inner join user u "
					+ "on b.user_code = u.user_code "
					+ "where u.user_email= ?";
			// pstmt ����
			pstmt = con.prepareStatement(sql);
			// ?�� ����
			pstmt.setString(1, user_email);
			// rs ����
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
	
	// �� �����ȣ ��ȸ
	public String getBookNumber(String user_email) {
		String book_number = "";
		try{
			// db ����
			con = Util.getConnection();
			// sql���ۼ�
			String sql = "select b.book_number from user u inner join booking b on u.user_code = b.user_code "
					+ "while u.user_email=?";
			// pstmt ����
			pstmt = con.prepareStatement(sql);
			// ?�� ����
			pstmt.setString(1, user_email);
			// rs ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				book_number = rs.getString(1);
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
		return book_number;
	}
	
	// 내 예약한거 취소하기 (소프트삭제처리)
	public void deleteMyBooking(String book_number) {
		try{
			// db 연결
			con = Util.getConnection();
			// sql문작성
			String sql = "update booking set book_chk_status = 1, book_cancel_date = current_date() where book_number=?";
			// pstmt 선언
			pstmt = con.prepareStatement(sql);
			// ?에 대입
			pstmt.setString(1, book_number);
			// 업데이트 실행
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

	
	// �� ���� ����Ѱ� ���� ���ϴ� �Լ�
	public int countCancelBooking(String user_email) {
		int count = 0;
		try{
			// db ����
			con = Util.getConnection();
			// sql���ۼ�
			String sql = "select count(*) from booking b "
					+ "inner join user u "
					+ "on b.user_code = u.user_code "
					+ "where u.user_email=? and "
					+ "b.book_chk_status=1";
			// pstmt ����
			pstmt = con.prepareStatement(sql);
			// ?�� ����
			pstmt.setString(1, user_email);
			rs=pstmt.executeQuery();
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
	
	// �� ���� ����� ���� ���ϴ� �Լ�
	public int countOverBooking(String user_email) {
		int count = 0;
		try{
			// db ����
			con = Util.getConnection();
			// sql���ۼ�
			String sql = "select count(*) from booking b "
					+ "inner join user u "
					+ "on b.user_code = u.user_code "
					+ "where u.user_email=? and "
					+ "b.book_chk_status=2";
			// pstmt ����
			pstmt = con.prepareStatement(sql);
			// ?�� ����
			pstmt.setString(1, user_email);
			rs=pstmt.executeQuery();
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
	
	// �� ���� ������ ���� ���ϴ� �Լ�
	public int countReservedBooking(String user_email) {
		int count = 0;
		try{
			// db ����
			con = Util.getConnection();
			// sql���ۼ�
			String sql = "select count(*) from booking b "
					+ "inner join user u "
					+ "on b.user_code = u.user_code "
					+ "where u.user_email=? and "
					+ "b.book_chk_status=3";
			// pstmt ����
			pstmt = con.prepareStatement(sql);
			// ?�� ����
			pstmt.setString(1, user_email);
			rs=pstmt.executeQuery();
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
	
	// ���º� ���� ���� ���ϴ� �Լ� (status: 1=���, 2=����, 3=������)
	public int countBookingByStatus(String user_email, int status) {
		int count = 0;
		try {
			con = Util.getConnection();
			String sql = "select count(*) from booking b "
					+ "inner join user u on b.user_code = u.user_code "
					+ "where u.user_email = ? and b.book_chk_status = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			pstmt.setInt(2, status);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
			} catch (Exception e) { e.printStackTrace(); }
		}
		return count;
	}

	// ���º� ���� ��� ����¡ ��ȸ �Լ� (status: 1=���, 2=����, 3=������)
	public ArrayList<MyBookingDTO> getMyBookingListByStatus(int startRow, int pageSize, String user_email, int status) {
		ArrayList<MyBookingDTO> mblist = new ArrayList<>();
		try {
			con = Util.getConnection();
			String sql = "select b.book_code, b.book_number, b.book_name, b.user_code, b.room_code, "
					+ "b.book_checkin_date, b.book_checkout_date, b.book_date, b.book_cancel_date, "
					+ "b.book_chk_status, r.room_name, r.room_price, "
					+ "a.acc_code, a.acc_name, a.acc_type, a.acc_address, a.acc_checkinTime, a.acc_checkoutTime, "
					+ "a.acc_img, rev.rev_code, rev.rev_created_datetime, rev.rev_chk_del "
					+ "from booking b inner join user u "
					+ "on b.user_code = u.user_code "
					+ "inner join room r "
					+ "on b.room_code = r.room_code "
					+ "inner join accommodation a "
					+ "on r.acc_code = a.acc_code "
					+ "left join reviews rev "
					+ "on a.acc_code = rev.acc_code and u.user_code = rev.user_code and rev.rev_chk_del = 0 "
					+ "where u.user_email = ? and b.book_chk_status = ? "
					+ "order by b.book_code desc "
					+ "limit ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_email);
			pstmt.setInt(2, status);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, pageSize);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MyBookingDTO mbdto = new MyBookingDTO();
				mbdto.setBook_code(rs.getInt(1));
				mbdto.setBook_number(rs.getString(2));
				mbdto.setBook_name(rs.getString(3));
				mbdto.setUser_code(rs.getInt(4));
				mbdto.setRoom_code(rs.getInt(5));
				mbdto.setBook_checkin_date(rs.getString(6));
				mbdto.setBook_checkout_date(rs.getString(7));
				mbdto.setBook_date(rs.getString(8));
				mbdto.setBook_cancel_date(rs.getString(9));
				mbdto.setBook_chk_status(rs.getInt(10));
				mbdto.setRoom_name(rs.getString(11));
				mbdto.setRoom_price(rs.getInt(12));
				mbdto.setAcc_code(rs.getInt(13));
				mbdto.setAcc_name(rs.getString(14));
				mbdto.setAcc_type(rs.getString(15));
				mbdto.setAcc_address(rs.getString(16));
				mbdto.setAcc_checkinTime(rs.getString(17));
				mbdto.setAcc_checkoutTime(rs.getString(18));
				mbdto.setAcc_img(rs.getString(19));
				mbdto.setRev_code(rs.getInt(20));
				mbdto.setRev_created_datetime(rs.getString(21));
				mbdto.setRev_chk_del(rs.getInt(22));

				mblist.add(mbdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (pstmt != null) { pstmt.close(); }
				if (con != null) { con.close(); }
			} catch (Exception e) { e.printStackTrace(); }
		}
		return mblist;
	}
	
	
}
