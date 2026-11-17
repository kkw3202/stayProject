package model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import DBPKG.Util;

public class AdminInqDAO {

    public static final int STATUS_WAITING = 0;
    public static final int STATUS_ANSWERED = 1;
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_DELETED = 1;
    public static final int STEP_QUESTION = 0;
    public static final int STEP_REPLY = 1;

    // 1. 문의 DTO
    public static class Inq {
        private int inqCode;
        private String inqTitle;
        private Timestamp createdDatetime;
        private int userCode;

        // ★ 추가 : 회원명
        private String userName;

        private String inqContent;
        private int inqRef;
        private int reStep;
        private int chkStatus;
        private int deleted;

        public int getInqCode() { return inqCode; }
        public String getInqTitle() { return inqTitle; }
        public Timestamp getCreatedDatetime() { return createdDatetime; }
        public int getUserCode() { return userCode; }

        // ★ 추가 : JSP에서 ${inq.userName} 사용
        public String getUserName() { return userName; }

        public String getInqContent() { return inqContent; }
        public int getInqRef() { return inqRef; }
        public int getReStep() { return reStep; }
        public int getChkStatus() { return chkStatus; }
        public int getDeleted() { return deleted; }
    }

    // 2. ResultSet을 DTO로 변환
    private Inq mapRow(ResultSet rs) throws SQLException {

        Inq inq = new Inq();

        inq.inqCode = rs.getInt("inq_code");
        inq.inqTitle = rs.getString("inq_title");
        inq.createdDatetime = rs.getTimestamp("inq_created_datetime");
        inq.userCode = rs.getInt("user_code");

        // ★ 추가 : JOIN으로 조회한 회원명 저장
        inq.userName = rs.getString("user_name");

        inq.inqContent = rs.getString("inq_content");
        inq.inqRef = rs.getInt("inq_ref");
        inq.reStep = rs.getInt("inq_re_step");
        inq.chkStatus = rs.getInt("inq_chk_status");
        inq.deleted = rs.getInt("inq_chk_del");

        return inq;
    }

    // ★ 수정 3. 검색 조건 생성
    // 회원코드 검색을 회원명 검색으로 변경
    private String searchCondition(String searchType, String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return "";
        }

        if ("content".equals(searchType)) {
            return " AND i.inq_content LIKE ? ";
        }

        // ★ 수정 : 회원명 검색
        if ("userName".equals(searchType)) {
            return " AND u.user_name LIKE ? ";
        }

        return " AND i.inq_title LIKE ? ";
    }

    // 4. 검색어 바인딩
    private int bindKeyword(PreparedStatement pstmt,
                            int index,
                            String keyword) throws SQLException {

        if (keyword != null && !keyword.trim().isEmpty()) {
            pstmt.setString(index++, "%" + keyword.trim() + "%");
        }

        return index;
    }

    // ★ 수정 5. 원본 문의 수
    public int count(String searchType,
                     String keyword,
                     Integer status,
                     Integer deleted) {

        String sql =
                "SELECT COUNT(*) "
              + "FROM inquiry i "
              + "LEFT JOIN user u ON i.user_code = u.user_code "
              + "WHERE i.inq_re_step=0 "
              + searchCondition(searchType, keyword);

        if (status != null) {
            sql += " AND i.inq_chk_status=? ";
        }

        if (deleted != null) {
            sql += " AND i.inq_chk_del=? ";
        }

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int idx = bindKeyword(pstmt, 1, keyword);

            if (status != null) {
                pstmt.setInt(idx++, status);
            }

            if (deleted != null) {
                pstmt.setInt(idx++, deleted);
            }

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

    // ★ 수정 6. 원본 문의 목록 및 페이징
    public List<Inq> list(String searchType,
                          String keyword,
                          Integer status,
                          Integer deleted,
                          int page,
                          int pageSize) {

        List<Inq> result = new ArrayList<>();

        // ★ 수정 : 회원명 조회를 위한 LEFT JOIN
        String sql =
                "SELECT i.*, u.user_name "
              + "FROM inquiry i "
              + "LEFT JOIN user u ON i.user_code = u.user_code "
              + "WHERE i.inq_re_step=0 "
              + searchCondition(searchType, keyword);

        if (status != null) {
            sql += " AND i.inq_chk_status=? ";
        }

        if (deleted != null) {
            sql += " AND i.inq_chk_del=? ";
        }

        sql += " ORDER BY i.inq_created_datetime DESC, "
             + "i.inq_code DESC LIMIT ? OFFSET ? ";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int idx = bindKeyword(pstmt, 1, keyword);

            if (status != null) {
                pstmt.setInt(idx++, status);
            }

            if (deleted != null) {
                pstmt.setInt(idx++, deleted);
            }

            pstmt.setInt(idx++, pageSize);
            pstmt.setInt(idx, (page - 1) * pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ★ 수정 7. 문의 또는 답변 단건 조회
    // mapRow()에서 회원명을 읽으므로 JOIN 추가
    public Inq find(int inqCode) {

        String sql =
                "SELECT i.*, u.user_name "
              + "FROM inquiry i "
              + "LEFT JOIN user u ON i.user_code = u.user_code "
              + "WHERE i.inq_code=?";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, inqCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ★ 수정 8. 원본 문의의 관리자 답변 조회
    // mapRow()에서 회원명을 읽으므로 JOIN 추가
    public Inq findReply(int inqRef) {

        String sql =
                "SELECT i.*, u.user_name "
              + "FROM inquiry i "
              + "LEFT JOIN user u ON i.user_code = u.user_code "
              + "WHERE i.inq_ref=? "
              + "AND i.inq_re_step=1 "
              + "AND i.inq_chk_del=0 "
              + "ORDER BY i.inq_code DESC LIMIT 1";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, inqRef);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 9. 관리자 답변 등록 + 원본 문의 답변완료 처리
    // 기존 기능 유지
    public boolean insertReply(int inqCode,
                               String replyTitle,
                               String replyContent) {

        String selectSql =
                "SELECT user_code, inq_ref, inq_chk_status "
              + "FROM inquiry "
              + "WHERE inq_code=? "
              + "AND inq_re_step=0 "
              + "AND inq_chk_del=0 FOR UPDATE";

        String checkSql =
                "SELECT COUNT(*) FROM inquiry "
              + "WHERE inq_ref=? "
              + "AND inq_re_step=1 "
              + "AND inq_chk_del=0";

        String insertSql =
                "INSERT INTO inquiry "
              + "(inq_title, user_code, inq_content, "
              + "inq_ref, inq_re_step, inq_chk_status, inq_chk_del) "
              + "VALUES (?, ?, ?, ?, 1, 1, 0)";

        String updateSql =
                "UPDATE inquiry SET inq_chk_status=1 "
              + "WHERE inq_code=? "
              + "AND inq_re_step=0 "
              + "AND inq_chk_del=0";

        try (Connection conn = Util.getConnection()) {

            conn.setAutoCommit(false);

            try {
                int userCode;
                int inqRef;
                int chkStatus;

                try (PreparedStatement pstmt =
                        conn.prepareStatement(selectSql)) {

                    pstmt.setInt(1, inqCode);

                    try (ResultSet rs = pstmt.executeQuery()) {

                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        userCode = rs.getInt("user_code");
                        inqRef = rs.getInt("inq_ref");
                        chkStatus = rs.getInt("inq_chk_status");
                    }
                }

                if (chkStatus != STATUS_WAITING) {
                    conn.rollback();
                    return false;
                }

                try (PreparedStatement pstmt =
                        conn.prepareStatement(checkSql)) {

                    pstmt.setInt(1, inqRef);

                    try (ResultSet rs = pstmt.executeQuery()) {

                        if (rs.next() && rs.getInt(1) > 0) {
                            conn.rollback();
                            return false;
                        }
                    }
                }

                try (PreparedStatement pstmt =
                        conn.prepareStatement(insertSql)) {

                    pstmt.setString(1, replyTitle);
                    pstmt.setInt(2, userCode);
                    pstmt.setString(3, replyContent);
                    pstmt.setInt(4, inqRef);

                    if (pstmt.executeUpdate() != 1) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement pstmt =
                        conn.prepareStatement(updateSql)) {

                    pstmt.setInt(1, inqCode);

                    if (pstmt.executeUpdate() != 1) {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                return true;

            } catch (Exception e) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                e.printStackTrace();
                return false;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 10. 원본 문의와 연결된 답변을 함께 소프트 삭제
    // 기존 기능 유지
    public boolean softDeleteInq(int inqCode) {

        String selectSql =
                "SELECT inq_ref FROM inquiry "
              + "WHERE inq_code=? "
              + "AND inq_re_step=0 "
              + "AND inq_chk_del=0 FOR UPDATE";

        String deleteSql =
                "UPDATE inquiry SET inq_chk_del=1 "
              + "WHERE inq_ref=? "
              + "AND inq_re_step IN (0, 1) "
              + "AND inq_chk_del=0";

        try (Connection conn = Util.getConnection()) {

            conn.setAutoCommit(false);

            try {
                int inqRef;

                try (PreparedStatement pstmt =
                        conn.prepareStatement(selectSql)) {

                    pstmt.setInt(1, inqCode);

                    try (ResultSet rs = pstmt.executeQuery()) {

                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        inqRef = rs.getInt("inq_ref");
                    }
                }

                try (PreparedStatement pstmt =
                        conn.prepareStatement(deleteSql)) {

                    pstmt.setInt(1, inqRef);

                    if (pstmt.executeUpdate() < 1) {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                return true;

            } catch (Exception e) {

                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                e.printStackTrace();
                return false;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 11. 정상 문의의 답변 상태별 건수
    // 기존 기능 유지
    public int countByStatus(int status) {

        String sql =
                "SELECT COUNT(*) FROM inquiry "
              + "WHERE inq_re_step=0 "
              + "AND inq_chk_del=0 "
              + "AND inq_chk_status=?";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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
