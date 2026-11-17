
package model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBPKG.Util;

public class AdminBookDAO {

    private static final String TABLE_NAME = "booking";

    public static final int STATUS_CANCEL = 1;
    public static final int STATUS_EXPIRED = 2;
    public static final int STATUS_RESERVED = 3;
    public static final int STATUS_ADMIN_DELETED = 4;

    // 1. 예약 정보 DTO
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

        // ★ 추가 : 예약 상세정보 표시용 필드
        private String accName;
        private String accAddress;
        private String roomName;
        private long bookPrice;

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

        // ★ 추가 : 숙소명
        public String getAccName() {
            return accName;
        }

        // ★ 추가 : 숙소주소
        public String getAccAddress() {
            return accAddress;
        }

        // ★ 추가 : 객실명
        public String getRoomName() {
            return roomName;
        }

        // ★ 추가 : 예약금액
        public long getBookPrice() {
            return bookPrice;
        }
    }

    // 2. ResultSet → Book 객체 변환
    private Book mapBook(ResultSet rs) throws SQLException {

        Book book = new Book();

        book.bookCode = rs.getInt("book_code");
        book.bookName = rs.getString("book_name");

        int userCode = rs.getInt("user_code");
        book.userCode = rs.wasNull() ? null : userCode;

        int guestCode = rs.getInt("guest_code");
        book.guestCode = rs.wasNull() ? null : guestCode;

        book.checkinDate = rs.getDate("book_checkin_date");
        book.checkoutDate = rs.getDate("book_checkout_date");
        book.bookDate = rs.getDate("book_date");
        book.roomCode = rs.getInt("room_code");
        book.bookNumber = rs.getString("book_number");
        book.cancelDate = rs.getDate("book_cancel_date");
        book.isMember = rs.getInt("book_is_member");
        book.status = rs.getInt("book_chk_status");

        return book;
    }

    // 3. 검색 컬럼 선택
    private String searchColumn(String type) {

        if ("number".equals(type)) {
            return "book_number";
        }

        return "book_name";
    }

    // 4. 검색 조건 구성
    // ★ 유지 : 회원/비회원 필터 기능
    private String searchWhere(
            String type,
            String keyword,
            Integer status,
            Integer isMember) {

        StringBuilder where =
                new StringBuilder(" WHERE 1=1 ");

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

        if (isMember != null) {
            where.append(" AND book_is_member = ? ");
        }

        return where.toString();
    }

    // 5. 검색 조건 바인딩
    private void bindSearch(
            PreparedStatement pstmt,
            String keyword,
            Integer status,
            Integer isMember) throws SQLException {

        int index = 1;

        if (status != null) {
            pstmt.setInt(index++, status);
        }

        if (keyword != null && !keyword.trim().isEmpty()) {
            pstmt.setString(
                    index++,
                    "%" + keyword.trim() + "%"
            );
        }

        if (isMember != null) {
            pstmt.setInt(index++, isMember);
        }
    }

    // 6. 검색 결과 건수
    public int count(
            String type,
            String keyword,
            Integer status,
            Integer isMember) {

        String sql =
                "SELECT COUNT(*) FROM " + TABLE_NAME
                + searchWhere(type, keyword, status, isMember);

        try (
            Connection con = Util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            bindSearch(pstmt, keyword, status, isMember);

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

    // 7. 예약 목록 조회
    public List<Book> list(
            String type,
            String keyword,
            Integer status,
            Integer isMember,
            int page,
            int pageSize) {

        List<Book> result = new ArrayList<>();

        page = Math.max(page, 1);
        pageSize = Math.max(pageSize, 1);

        String sql =
                "SELECT * FROM " + TABLE_NAME
                + searchWhere(type, keyword, status, isMember)
                + " ORDER BY book_code DESC LIMIT ? OFFSET ?";

        try (
            Connection con = Util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            bindSearch(pstmt, keyword, status, isMember);

            int index = 1;

            if (status != null) {
                index++;
            }

            if (keyword != null && !keyword.trim().isEmpty()) {
                index++;
            }

            if (isMember != null) {
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

    // 8. 예약 상세조회
    // ★ 수정 : room, accommodation 테이블 JOIN
    public Book find(int bookCode) {

        String sql =
                "SELECT b.*, "
                + "a.acc_name, "
                + "a.acc_address, "
                + "r.room_name, "
                + "(DATEDIFF("
                + "b.book_checkout_date, "
                + "b.book_checkin_date"
                + ") * r.room_price) AS book_price "
                + "FROM booking b "
                + "LEFT JOIN room r "
                + "ON b.room_code = r.room_code "
                + "LEFT JOIN accommodation a "
                + "ON r.acc_code = a.acc_code "
                + "WHERE b.book_code = ?";

        try (
            Connection con = Util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, bookCode);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    Book book = mapBook(rs);

                    // ★ 추가 : 숙소 및 객실 정보 저장
                    book.accName = rs.getString("acc_name");
                    book.accAddress = rs.getString("acc_address");
                    book.roomName = rs.getString("room_name");
                    book.bookPrice = rs.getLong("book_price");

                    return book;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 9. 예약 수정
    public boolean update(
            int bookCode,
            String bookName,
            Date checkinDate,
            Date checkoutDate) {

        if (bookCode <= 0
                || bookName == null
                || bookName.trim().isEmpty()
                || checkinDate == null
                || checkoutDate == null
                || !checkoutDate.after(checkinDate)) {
            return false;
        }

        String sql =
                "UPDATE booking "
                + "SET book_name = ?, "
                + "book_checkin_date = ?, "
                + "book_checkout_date = ? "
                + "WHERE book_code = ? "
                + "AND book_chk_status <> 4";

        try (
            Connection con = Util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setString(1, bookName.trim());
            pstmt.setDate(2, checkinDate);
            pstmt.setDate(3, checkoutDate);
            pstmt.setInt(4, bookCode);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 10. 예약 소프트 삭제
    public boolean softDeleteBook(int bookCode) {

        String sql =
                "UPDATE booking "
                + "SET book_chk_status = 4 "
                + "WHERE book_code = ? "
                + "AND book_chk_status <> 4";

        try (
            Connection con = Util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            pstmt.setInt(1, bookCode);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 11. 예약 상태별 건수
    public int countByStatus(int status) {

        String sql =
                "SELECT COUNT(*) "
                + "FROM booking "
                + "WHERE book_chk_status = ?";

        try (
            Connection con = Util.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

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
