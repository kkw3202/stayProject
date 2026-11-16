package model.User;

import java.sql.*;
import java.util.*;
import DBPKG.Util;

public class AdminCustomerDAO {

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

    private String column(String type) {
        if ("name".equals(type)) return "user_name";
        if ("phone".equals(type)) return "user_phone";
        return "user_email";
    }

    public int count(String type, String keyword) throws Exception {
        String sql = "SELECT COUNT(*) FROM user "
                + "WHERE user_code<>1 AND "
                + column(type) + " LIKE ?";

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public List<Member> list(String type, String keyword,
                             int page, int pageSize) throws Exception {

        String sql = "SELECT user_code,user_name,user_phone,"
                + "user_email,user_birth,user_gender,"
                + "user_chk_del,user_passkey "
                + "FROM user WHERE user_code<>1 AND "
                + column(type)
                + " LIKE ? ORDER BY user_code DESC LIMIT ? OFFSET ?";

        List<Member> list = new ArrayList<>();

        try (Connection c = Util.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }

        return list;
    }

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