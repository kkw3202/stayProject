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
        private String inqContent;
        private int inqRef;
        private int reStep;
        private int chkStatus;
        private int deleted;

        public int getInqCode() { return inqCode; }
        public String getInqTitle() { return inqTitle; }
        public Timestamp getCreatedDatetime() { return createdDatetime; }
        public int getUserCode() { return userCode; }
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
        inq.inqContent = rs.getString("inq_content");
        inq.inqRef = rs.getInt("inq_ref");
        inq.reStep = rs.getInt("inq_re_step");
        inq.chkStatus = rs.getInt("inq_chk_status");
        inq.deleted = rs.getInt("inq_chk_del");
        return inq;
    }

    // 3. 검색 조건 생성
    private String searchCondition(String searchType, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return "";
        if ("content".equals(searchType)) return " AND inq_content LIKE ? ";
        if ("userCode".equals(searchType)) return " AND CAST(user_code AS CHAR) LIKE ? ";
        return " AND inq_title LIKE ? ";
    }

    // 4. 검색어 바인딩
    private int bindKeyword(PreparedStatement pstmt, int index, String keyword) throws SQLException {
        if (keyword != null && !keyword.trim().isEmpty()) {
            pstmt.setString(index++, "%" + keyword.trim() + "%");
        }
        return index;
    }

    // 5. 원본 문의 수
    public int count(String searchType, String keyword, Integer status, Integer deleted) {
        String sql = "SELECT COUNT(*) FROM inquiry WHERE inq_re_step=0 "
                + searchCondition(searchType, keyword);
        if (status != null) sql += " AND inq_chk_status=? ";
        if (deleted != null) sql += " AND inq_chk_del=? ";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int idx = bindKeyword(pstmt, 1, keyword);
            if (status != null) pstmt.setInt(idx++, status);
            if (deleted != null) pstmt.setInt(idx++, deleted);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 6. 원본 문의 목록 및 페이징
    public List<Inq> list(String searchType, String keyword, Integer status,
                          Integer deleted, int page, int pageSize) {
        List<Inq> result = new ArrayList<>();
        String sql = "SELECT * FROM inquiry WHERE inq_re_step=0 "
                + searchCondition(searchType, keyword);
        if (status != null) sql += " AND inq_chk_status=? ";
        if (deleted != null) sql += " AND inq_chk_del=? ";
        sql += " ORDER BY inq_created_datetime DESC, inq_code DESC LIMIT ? OFFSET ? ";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int idx = bindKeyword(pstmt, 1, keyword);
            if (status != null) pstmt.setInt(idx++, status);
            if (deleted != null) pstmt.setInt(idx++, deleted);
            pstmt.setInt(idx++, pageSize);
            pstmt.setInt(idx, (page - 1) * pageSize);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) result.add(mapRow(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 7. 문의 또는 답변 단건 조회
    public Inq find(int inqCode) {
        String sql = "SELECT * FROM inquiry WHERE inq_code=?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, inqCode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 8. 원본 문의의 관리자 답변 조회
    public Inq findReply(int inqRef) {
        String sql = "SELECT * FROM inquiry WHERE inq_ref=? AND inq_re_step=1 "
                + "AND inq_chk_del=0 ORDER BY inq_code DESC LIMIT 1";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, inqRef);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 9. 관리자 답변 등록 + 원본 문의 답변완료 처리
    public boolean insertReply(int inqCode, String replyTitle, String replyContent) {
        String selectSql = "SELECT user_code, inq_ref, inq_chk_status FROM inquiry "
                + "WHERE inq_code=? AND inq_re_step=0 AND inq_chk_del=0 FOR UPDATE";
        String checkSql = "SELECT COUNT(*) FROM inquiry WHERE inq_ref=? "
                + "AND inq_re_step=1 AND inq_chk_del=0";
        String insertSql = "INSERT INTO inquiry (inq_title, user_code, inq_content, "
                + "inq_ref, inq_re_step, inq_chk_status, inq_chk_del) "
                + "VALUES (?, ?, ?, ?, 1, 1, 0)";
        String updateSql = "UPDATE inquiry SET inq_chk_status=1 "
                + "WHERE inq_code=? AND inq_re_step=0 AND inq_chk_del=0";

        try (Connection conn = Util.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int userCode;
                int inqRef;
                int chkStatus;
                try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
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
                try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                    pstmt.setInt(1, inqRef);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            conn.rollback();
                            return false;
                        }
                    }
                }
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, replyTitle);
                    pstmt.setInt(2, userCode);
                    pstmt.setString(3, replyContent);
                    pstmt.setInt(4, inqRef);
                    if (pstmt.executeUpdate() != 1) {
                        conn.rollback();
                        return false;
                    }
                }
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
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
    public boolean softDeleteInq(int inqCode) {
        String selectSql = "SELECT inq_ref FROM inquiry WHERE inq_code=? "
                + "AND inq_re_step=0 AND inq_chk_del=0 FOR UPDATE";
        String deleteSql = "UPDATE inquiry SET inq_chk_del=1 WHERE inq_ref=? "
                + "AND inq_re_step IN (0, 1) AND inq_chk_del=0";

        try (Connection conn = Util.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int inqRef;
                try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                    pstmt.setInt(1, inqCode);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }
                        inqRef = rs.getInt("inq_ref");
                    }
                }
                try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
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
    public int countByStatus(int status) {
        String sql = "SELECT COUNT(*) FROM inquiry WHERE inq_re_step=0 "
                + "AND inq_chk_del=0 AND inq_chk_status=?";
        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
