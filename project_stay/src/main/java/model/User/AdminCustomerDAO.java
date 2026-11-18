package model.User;

import java.sql.*;
import java.util.*;
import DBPKG.Util;

public class AdminCustomerDAO {

    // 1. 회원 정보 DTO
    public static class Member {
        private int code, gender, deleted;
        private String name, phone, email, birth, passkey;

        public int getCode() { return code; }
        public int getGender() { return gender; }
        public int getDeleted() { return deleted; }
        public String getName() { return name; }
        public String getPhone() { return phone; }
        public String getEmail() { return email; }
        public String getBirth() { return birth; }
        public String getPasskey() { return passkey; }
    }

    // 2. ResultSet → Member 객체 변환
    private Member map(ResultSet rs) throws SQLException {
        Member m = new Member();

        m.code = rs.getInt("user_code");
        m.name = rs.getString("user_name");
        m.phone = rs.getString("user_phone");
        m.email = rs.getString("user_email");
        m.birth = rs.getString("user_birth");
        m.gender = rs.getInt("user_gender");
        m.deleted = rs.getInt("user_chk_del");
        m.passkey = rs.getString("user_passkey");

        return m;
    }

    // 3. 검색 컬럼 선택
    private String column(String type) {
        if ("name".equals(type)) return "user_name";
        if ("phone".equals(type)) return "user_phone";
        return "user_email";
    }

    // ★ 추가 4. 회원탈퇴여부 SQL 조건 생성
    // null : 전체회원
    // 0    : 정상회원
    // 1    : 탈퇴회원
    private String deletedWhere(Integer deleted) {

        if (deleted != null) {
            return " AND user_chk_del=? ";
        }

        return "";
    }

    // ★ 수정 5. 회원 검색 결과 건수
    // 기존 검색조건 + 회원탈퇴여부 조건
    public int count(String type, String keyword,
                     Integer deleted) throws Exception {

        String sql = "SELECT COUNT(*) FROM user "
                + "WHERE user_code<>1 AND "
                + column(type) + " LIKE ?"
                + deletedWhere(deleted);

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            // ★ 추가 : 탈퇴여부 조건 바인딩
            if (deleted != null) {
                ps.setInt(2, deleted);
            }

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    // ★ 수정 6. 회원 목록 조회
    // 기존 검색조건 + 회원탈퇴여부 조건
    public List<Member> list(String type, String keyword,
                             Integer deleted,
                             int page, int pageSize) throws Exception {

        String sql = "SELECT user_code,user_name,user_phone,"
                + "user_email,user_birth,user_gender,"
                + "user_chk_del,user_passkey "
                + "FROM user WHERE user_code<>1 AND "
                + column(type)
                + " LIKE ?"
                + deletedWhere(deleted)
                + " ORDER BY user_code DESC LIMIT ? OFFSET ?";

        List<Member> list = new ArrayList<>();

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            // ★ 수정 : 조건 유무에 따라 파라미터 위치 조정
            int index = 2;

            if (deleted != null) {
                ps.setInt(index++, deleted);
            }

            ps.setInt(index++, pageSize);
            ps.setInt(index, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }

        return list;
    }

    // 7. 회원 상세조회 (기존 유지)
    public Member find(int code) throws Exception {

        String sql = "SELECT user_code,user_name,user_phone,"
                + "user_email,user_birth,user_gender,"
                + "user_chk_del,user_passkey "
                + "FROM user WHERE user_code=? AND user_code<>1";

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, code);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    // 8. 회원정보 수정 (기존 유지)
    public boolean update(int code, String name, String phone,
                          String birth, int gender, String passkey)
                          throws Exception {

        String sql = "UPDATE user SET user_name=?,user_phone=?,"
                + "user_birth=?,user_gender=?,user_passkey=? "
                + "WHERE user_code=? AND user_code<>1 "
                + "AND user_chk_del=0";

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, birth);
            ps.setInt(4, gender);
            ps.setString(5, passkey);
            ps.setInt(6, code);

            return ps.executeUpdate() == 1;
        }
    }

    // 9. 회원탈퇴 처리 (기존 유지)
    public boolean delete(int code) throws Exception {

        String sql = "UPDATE user SET user_chk_del=1 "
                + "WHERE user_code=? AND user_code<>1 "
                + "AND user_chk_del=0";

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, code);
            return ps.executeUpdate() == 1;
        }
    }
}
