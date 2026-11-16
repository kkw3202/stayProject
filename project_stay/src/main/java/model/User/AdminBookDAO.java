
package model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DBPKG.Util;

public class AdminBookDAO {

    // 실제 DB 테이블명에 맞게 수정
    private static final String TABLE_NAME = "booking";

    /*
     * 예약 상태값
     * 1 : 예약취소
     * 2 : 예약만료
     * 3 : 예약중 (기본값)
     *
     * [임시 정책 - 추후 회의 결과에 따라 변경]
     * 4 : 관리자 소프트 삭제
     *
     * 실제 DELETE문은 사용하지 않음.
     */
    public static final int STATUS_CANCEL = 1;
    public static final int STATUS_EXPIRED = 2;
    public static final int STATUS_RESERVED = 3;
    public static final int STATUS_ADMIN_DELETED = 4;

    // 예약 정보를 담는 DTO
    public static class Book {

        private int bookCode;
        private String bookName;
        private Integer userCode;
        private Integer guestCode;
        private Date checkinDate;
        private Date checkoutDate;
        private Date bookDate;
        private int roomCode;
        private String bookNumber;
        private Date cancelDate;
        private int isMember;
        private int status;

        public int getBookCode() {
            return bookCode;
        }

        public String getBookName() {
            return bookName;
        }

        public Integer getUserCode() {
            return userCode;
        }

        public Integer getGuestCode() {
            return guestCode;
        }

        public Date getCheckinDate() {
            return checkinDate;
        }

        public Date getCheckoutDate() {
            return checkoutDate;
        }

        public Date getBookDate() {
            return bookDate;
        }

        public int getRoomCode() {
            return roomCode;
        }

        public String getBookNumber() {
            return bookNumber;
        }

        public Date getCancelDate() {
            return cancelDate;
        }

        public int getIsMember() {
            return isMember;
        }

        public int getStatus() {
            return status;
        }
    }

    // ResultSet의 한 행을 Book 객체로 변환
    private Book mapBook(ResultSet rs) throws SQLException {

        Book b = new Book();

        b.bookCode = rs.getInt("book_code");
        b.bookName = rs.getString("book_name");

        int userCode = rs.getInt("user_code");
        b.userCode = rs.wasNull() ? null : userCode;

        int guestCode = rs.getInt("guest_code");
        b.guestCode = rs.wasNull() ? null : guestCode;

        b.checkinDate = rs.getDate("book_checkin_date");
        b.checkoutDate = rs.getDate("book_checkout_date");
        b.bookDate = rs.getDate("book_date");

        b.roomCode = rs.getInt("room_code");
        b.bookNumber = rs.getString("book_number");
        b.cancelDate = rs.getDate("book_cancel_date");

        b.isMember = rs.getInt("book_is_member");
        b.status = rs.getInt("book_chk_status");

        return b;
    }

    // 검색할 컬럼을 안전하게 선택
    private String searchColumn(String type) {

        if ("name".equals(type)) {
            return "book_name";
        }

        if ("number".equals(type)) {
            return "book_number";
        }

        return "book_name";
    }

    // 검색 조건 생성
    private String searchWhere(String type, String keyword,
                               Integer status) {

        StringBuilder where =
                new StringBuilder(" WHERE 1=1 ");

        // 관리자 소프트 삭제된 예약은 기본 목록에서 제외
        // [임시 정책: 상태값 4]
        if (status == null) {
            where.append(" AND book_chk_status <> 4 ");
        } else {
            where.append(" AND book_chk_status = ? ");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            where.append(" AND ")
                 .append(searchColumn(type))
                 .append(" LIKE ? ");
        }

        return where.toString();
    }

    // 검색 조건의 ? 값 설정
    private void bindSearch(PreparedStatement pstmt,
                            String keyword, Integer status)
                            throws SQLException {

        int index = 1;

        if (status != null) {
            pstmt.setInt(index++, status);
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            pstmt.setString(index++, "%" + keyword.trim() + "%");
        }
    }

    // 전체 예약 건수 및 검색 결과 건수
    public int count(String type, String keyword, Integer status) {

        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME
                   + searchWhere(type, keyword, status);

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            bindSearch(pstmt, keyword, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 예약 목록 조회 및 페이징
    public List<Book> list(String type, String keyword,
                           Integer status, int page, int pageSize) {

        List<Book> result = new ArrayList<>();

        page = Math.max(page, 1);
        pageSize = Math.max(pageSize, 1);

        String sql = "SELECT * FROM " + TABLE_NAME
                   + searchWhere(type, keyword, status)
                   + " ORDER BY book_code DESC "
                   + " LIMIT ? OFFSET ?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            bindSearch(pstmt, keyword, status);

            int index = 1;

            if (status != null) {
                index++;
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                index++;
            }

            pstmt.setInt(index++, pageSize);
            pstmt.setInt(index, (page - 1) * pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    result.add(mapBook(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 예약 상세 조회
    public Book find(int bookCode) {

        String sql = "SELECT * FROM " + TABLE_NAME
                   + " WHERE book_code=?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, bookCode);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return mapBook(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * 예약 정보 수정
     *
     * 예약자명, 체크인·체크아웃 날짜를 수정한다.
     * 예약번호, 회원코드, 비회원코드는 변경하지 않는다.
     *
     * 소프트 삭제된 예약은 수정 불가.
     */
    public boolean update(int bookCode, String bookName,
                          Date checkinDate, Date checkoutDate) {

        if (bookName == null || bookName.trim().isEmpty()
                || checkinDate == null || checkoutDate == null
                || !checkoutDate.after(checkinDate)) {
            return false;
        }

        String sql = "UPDATE " + TABLE_NAME
                   + " SET book_name=?, "
                   + " book_checkin_date=?, "
                   + " book_checkout_date=? "
                   + " WHERE book_code=? "
                   + " AND book_chk_status <> 4";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, bookName.trim());
            pstmt.setDate(2, checkinDate);
            pstmt.setDate(3, checkoutDate);
            pstmt.setInt(4, bookCode);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * 관리자 예약 소프트 삭제
     *
     * [임시 정책 - 추후 회의 결과에 따라 변경]
     * book_chk_status = 4
     *
     * 실제 데이터를 삭제하지 않고 상태값만 변경한다.
     * book_cancel_date는 변경하지 않는다.
     *
     * 4번 상태의 예약은 중복 삭제 불가.
     */
    public boolean softDeleteBook(int bookCode) {

        String sql = "UPDATE " + TABLE_NAME
                   + " SET book_chk_status=4 "
                   + " WHERE book_code=? "
                   + " AND book_chk_status<>4";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, bookCode);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 예약 상태별 건수
    public int countByStatus(int status) {

        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME
                   + " WHERE book_chk_status=?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, status);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
