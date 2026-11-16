package model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBPKG.Util;

public class AdminAccDAO {

    private static final String TABLE_NAME = "accommodation";

    /*
     * 숙소 삭제 정책
     * acc_chk_del = 0 : 정상
     * acc_chk_del = 1 : 관리자 소프트 삭제
     *
     * 숙소 삭제 시 room 테이블은 변경하지 않는다.
     */
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_DELETED = 1;

    // 1. 숙소 정보 DTO
    public static class Acc {

        private int accCode;
        private int cityCode;
        private String accType;
        private String accName;
        private String accAddress;
        private String accFacility;
        private Time checkinTime;
        private Time checkoutTime;
        private int deleted;
        private String accImg;

        public int getAccCode() {
            return accCode;
        }

        public int getCityCode() {
            return cityCode;
        }

        public String getAccType() {
            return accType;
        }

        public String getAccName() {
            return accName;
        }

        public String getAccAddress() {
            return accAddress;
        }

        public String getAccFacility() {
            return accFacility;
        }

        public Time getCheckinTime() {
            return checkinTime;
        }

        public Time getCheckoutTime() {
            return checkoutTime;
        }

        public int getDeleted() {
            return deleted;
        }

        public String getAccImg() {
            return accImg;
        }
    }

    // 2. ResultSet을 DTO로 변환
    private Acc mapAcc(ResultSet rs) throws Exception {

        Acc acc = new Acc();

        acc.accCode = rs.getInt("acc_code");
        acc.cityCode = rs.getInt("city_code");
        acc.accType = rs.getString("acc_type");
        acc.accName = rs.getString("acc_name");
        acc.accAddress = rs.getString("acc_address");
        acc.accFacility = rs.getString("acc_facility");
        acc.checkinTime = rs.getTime("acc_checkinTime");
        acc.checkoutTime = rs.getTime("acc_checkoutTime");
        acc.deleted = rs.getInt("acc_chk_del");
        acc.accImg = rs.getString("acc_img");

        return acc;
    }

    // 3. 검색 조건 생성
    private String searchWhere(String searchType, String keyword, Integer status) {

        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");

        if (status == null) {
            sql.append(" AND acc_chk_del=0 ");
        } else {
            sql.append(" AND acc_chk_del=? ");
        }

        if (keyword != null && !keyword.trim().isEmpty()) {

            if ("name".equals(searchType)) {
                sql.append(" AND acc_name LIKE ? ");
            } else if ("type".equals(searchType)) {
                sql.append(" AND acc_type LIKE ? ");
            } else if ("address".equals(searchType)) {
                sql.append(" AND acc_address LIKE ? ");
            }
        }

        return sql.toString();
    }

    // 4. 검색 파라미터 설정
    private int bindSearch(PreparedStatement pstmt, String searchType,
                           String keyword, Integer status) throws Exception {

        int index = 1;

        if (status != null) {
            pstmt.setInt(index++, status);
        }

        if (keyword != null && !keyword.trim().isEmpty()
                && ("name".equals(searchType) || "type".equals(searchType)
                || "address".equals(searchType))) {

            pstmt.setString(index++, "%" + keyword.trim() + "%");
        }

        return index;
    }

    // 5. 숙소 검색 결과 건수
    public int count(String searchType, String keyword, Integer status) {

        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME
                   + searchWhere(searchType, keyword, status);

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            bindSearch(pstmt, searchType, keyword, status);

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

    // 6. 숙소 목록 조회 및 페이징
    public List<Acc> list(String searchType, String keyword,
                          Integer status, int page, int pageSize) {

        List<Acc> result = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME
                   + searchWhere(searchType, keyword, status)
                   + " ORDER BY acc_code DESC LIMIT ? OFFSET ?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            int index = bindSearch(pstmt, searchType, keyword, status);

            pstmt.setInt(index++, pageSize);
            pstmt.setInt(index, (page - 1) * pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapAcc(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 7. 숙소 상세 조회
    public Acc find(int accCode) {

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE acc_code=?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, accCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapAcc(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 8. 숙소 등록
    public int insert(int cityCode, String accType, String accName,
                      String accAddress, String accFacility,
                      Time checkinTime, Time checkoutTime, String accImg) {

        String sql = "INSERT INTO " + TABLE_NAME
                   + " (city_code, acc_type, acc_name, acc_address, "
                   + " acc_facility, acc_checkinTime, acc_checkoutTime, acc_img) "
                   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, cityCode);
            pstmt.setString(2, accType);
            pstmt.setString(3, accName);
            pstmt.setString(4, accAddress);
            pstmt.setString(5, accFacility);
            pstmt.setTime(6, checkinTime);
            pstmt.setTime(7, checkoutTime);
            pstmt.setString(8, accImg);

            if (pstmt.executeUpdate() == 1) {

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // 9. 숙소 정보 수정
    public boolean update(int accCode, int cityCode, String accType,
                          String accName, String accAddress, String accFacility,
                          Time checkinTime, Time checkoutTime, String accImg) {

        String sql = "UPDATE " + TABLE_NAME
                   + " SET city_code=?, acc_type=?, acc_name=?, acc_address=?, "
                   + " acc_facility=?, acc_checkinTime=?, acc_checkoutTime=?, acc_img=? "
                   + " WHERE acc_code=? AND acc_chk_del=0";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, cityCode);
            pstmt.setString(2, accType);
            pstmt.setString(3, accName);
            pstmt.setString(4, accAddress);
            pstmt.setString(5, accFacility);
            pstmt.setTime(6, checkinTime);
            pstmt.setTime(7, checkoutTime);
            pstmt.setString(8, accImg);
            pstmt.setInt(9, accCode);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 10. 숙소 소프트 삭제
    public boolean softDeleteAcc(int accCode) {

        /*
         * 숙소만 삭제 처리한다.
         * 연결된 객실의 room_chk_del 값은 변경하지 않는다.
         */
        String sql = "UPDATE " + TABLE_NAME
                   + " SET acc_chk_del=1 "
                   + " WHERE acc_code=? AND acc_chk_del=0";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, accCode);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 11. 숙소 상태별 건수
    public int countByStatus(int status) {

        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME
                   + " WHERE acc_chk_del=?";

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

    // 12. 객실 등록 시 선택 가능한 정상 숙소 목록
    public List<Acc> activeList() {

        List<Acc> result = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME
                   + " WHERE acc_chk_del=0 ORDER BY acc_name ASC";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                result.add(mapAcc(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
