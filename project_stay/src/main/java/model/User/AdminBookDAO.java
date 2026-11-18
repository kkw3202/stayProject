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

<<<<<<< HEAD
    private static final String TABLE_NAME = "booking";

=======
    // Ω«¡¶ DB ≈◊¿Ã∫Ì∏Ìø° ∏¬∞‘ ºˆ¡§
    private static final String TABLE_NAME = "booking";

    /*
     * øπæ‡ ªÛ≈¬∞™
     * 1 : øπæ‡√Îº“
     * 2 : øπæ‡øœ∑·
     * 3 : øπæ‡¡ﬂ (±‚∫ª∞™)
     *
     * [¿”Ω√ ¡§√• - √ﬂ»ƒ »∏¿« ∞·∞˙ø° µ˚∂Û ∫Ø∞Ê]
     * 4 : ∞¸∏Æ¿⁄ º“«¡∆Æ ªË¡¶
     *
     * Ω«¡¶ DELETEπÆ¿∫ ªÁøÎ«œ¡ˆ æ ¿Ω.
     */
>>>>>>> main
    public static final int STATUS_CANCEL = 1;
    public static final int STATUS_EXPIRED = 2;
    public static final int STATUS_RESERVED = 3;
    public static final int STATUS_ADMIN_DELETED = 4;

<<<<<<< HEAD
    // 1. ÏòàÏïΩ Ï†ïÎ≥¥ DTO
=======
    // øπæ‡ ¡§∫∏∏¶ ¥„¥¬ DTO
>>>>>>> main
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

        // ‚òÖ Ï∂îÍ∞Ä : ÏòàÏïΩ ÏÉÅÏÑ∏Ï†ïÎ≥¥ ÌëúÏãúÏö© ÌïÑÎìú
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

        // ‚òÖ Ï∂îÍ∞Ä : ÏàôÏÜåÎ™Ö
        public String getAccName() {
            return accName;
        }

        // ‚òÖ Ï∂îÍ∞Ä : ÏàôÏÜåÏ£ºÏÜå
        public String getAccAddress() {
            return accAddress;
        }

        // ‚òÖ Ï∂îÍ∞Ä : Í∞ùÏã§Î™Ö
        public String getRoomName() {
            return roomName;
        }

        // ‚òÖ Ï∂îÍ∞Ä : ÏòàÏïΩÍ∏àÏï°
        public long getBookPrice() {
            return bookPrice;
        }
    }

<<<<<<< HEAD
    // 2. ResultSet ‚Üí Book Í∞ùÏ≤¥ Î≥ÄÌôò
=======
    // ResultSet¿« «— «‡¿ª Book ∞¥√º∑Œ ∫Ø»Ø
>>>>>>> main
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

<<<<<<< HEAD
    // 3. Í≤ÄÏÉâ Ïª¨Îüº ÏÑ†ÌÉù
=======
    // ∞Àªˆ«“ ƒ√∑≥¿ª æ»¿¸«œ∞‘ º±≈√
>>>>>>> main
    private String searchColumn(String type) {

        if ("number".equals(type)) {
            return "book_number";
        }

        return "book_name";
    }

<<<<<<< HEAD
    // 4. Í≤ÄÏÉâ Ï°∞Í±¥ Íµ¨ÏÑ±
    // ‚òÖ Ïú†ÏßÄ : ÌöåÏõê/ÎπÑÌöåÏõê ÌïÑÌÑ∞ Í∏∞Îä•
    private String searchWhere(
            String type,
            String keyword,
            Integer status,
            Integer isMember) {
=======
    // ∞Àªˆ ¡∂∞« ª˝º∫
    private String searchWhere(String type, String keyword,
                               Integer status) {
>>>>>>> main

        StringBuilder where =
                new StringBuilder(" WHERE 1=1 ");

<<<<<<< HEAD
=======
        // ∞¸∏Æ¿⁄ º“«¡∆Æ ªË¡¶µ» øπæ‡¿∫ ±‚∫ª ∏Ò∑œø°º≠ ¡¶ø‹
        // [¿”Ω√ ¡§√•: ªÛ≈¬∞™ 4]
>>>>>>> main
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

<<<<<<< HEAD
    // 5. Í≤ÄÏÉâ Ï°∞Í±¥ Î∞îÏù∏Îî©
    private void bindSearch(
            PreparedStatement pstmt,
            String keyword,
            Integer status,
            Integer isMember) throws SQLException {
=======
    // ∞Àªˆ ¡∂∞«¿« ? ∞™ º≥¡§
    private void bindSearch(PreparedStatement pstmt,
                            String keyword, Integer status)
                            throws SQLException {
>>>>>>> main

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

<<<<<<< HEAD
    // 6. Í≤ÄÏÉâ Í≤∞Í≥º Í±¥Ïàò
    public int count(
            String type,
            String keyword,
            Integer status,
            Integer isMember) {
=======
    // ¿¸√º øπæ‡ ∞«ºˆ π◊ ∞Àªˆ ∞·∞˙ ∞«ºˆ
    public int count(String type, String keyword, Integer status) {
>>>>>>> main

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

<<<<<<< HEAD
    // 7. ÏòàÏïΩ Î™©Î°ù Ï°∞Ìöå
    public List<Book> list(
            String type,
            String keyword,
            Integer status,
            Integer isMember,
            int page,
            int pageSize) {
=======
    // øπæ‡ ∏Ò∑œ ¡∂»∏ π◊ ∆‰¿Ã¬°
    public List<Book> list(String type, String keyword,
                            Integer status, int page, int pageSize) {
>>>>>>> main

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

<<<<<<< HEAD
    // 8. ÏòàÏïΩ ÏÉÅÏÑ∏Ï°∞Ìöå
    // ‚òÖ ÏàòÏ†ï : room, accommodation ÌÖåÏù¥Î∏î JOIN
=======
    // øπæ‡ ªÛºº ¡∂»∏
>>>>>>> main
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

                    // ‚òÖ Ï∂îÍ∞Ä : ÏàôÏÜå Î∞è Í∞ùÏã§ Ï†ïÎ≥¥ Ï†ÄÏû•
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

<<<<<<< HEAD
    // 9. ÏòàÏïΩ ÏàòÏ†ï
    public boolean update(
            int bookCode,
            String bookName,
            Date checkinDate,
            Date checkoutDate) {
=======
    /*
     * øπæ‡ ¡§∫∏ ºˆ¡§
     *
     * øπæ‡¿⁄∏Ì, √º≈©¿Œ°§√º≈©æ∆øÙ ≥Ø¬•∏¶ ºˆ¡§«—¥Ÿ.
     * øπæ‡π¯»£, »∏ø¯ƒ⁄µÂ, ∫Ò»∏ø¯ƒ⁄µÂ¥¬ ∫Ø∞Ê«œ¡ˆ æ ¥¬¥Ÿ.
     *
     * º“«¡∆Æ ªË¡¶µ» øπæ‡¿∫ ºˆ¡§ ∫“∞°.
     */
    public boolean update(int bookCode, String bookName,
                          Date checkinDate, Date checkoutDate) {
>>>>>>> main

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

<<<<<<< HEAD
    // 10. ÏòàÏïΩ ÏÜåÌîÑÌä∏ ÏÇ≠Ï†ú
=======
    /*
     * ∞¸∏Æ¿⁄ øπæ‡ º“«¡∆Æ ªË¡¶
     *
     * [¿”Ω√ ¡§√• - √ﬂ»ƒ »∏¿« ∞·∞˙ø° µ˚∂Û ∫Ø∞Ê]
     * book_chk_status = 4
     *
     * Ω«¡¶ µ•¿Ã≈Õ∏¶ ªË¡¶«œ¡ˆ æ ∞Ì ªÛ≈¬∞™∏∏ ∫Ø∞Ê«—¥Ÿ.
     * book_cancel_date¥¬ ∫Ø∞Ê«œ¡ˆ æ ¥¬¥Ÿ.
     *
     * 4π¯ ªÛ≈¬¿« øπæ‡¿∫ ¡ﬂ∫π ªË¡¶ ∫“∞°.
     */
>>>>>>> main
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

<<<<<<< HEAD
    // 11. ÏòàÏïΩ ÏÉÅÌÉúÎ≥Ñ Í±¥Ïàò
=======
    // øπæ‡ ªÛ≈¬∫∞ ∞«ºˆ
>>>>>>> main
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