package model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBPKG.Util;

public class AdminRoomDAO {

    // 1. 객실 관리상태
    public static final int STATUS_ACTIVE = 0;
    public static final int STATUS_DELETED = 1;

    // 2. 객실정보 DTO
    public static class Room {

        private int roomCode;
        private int accCode;
        private String accName;
        private int accDeleted;

        private String roomName;
        private int roomPrice;
        private int roomCount;
        private int roomCapacity;
        private String roomInfo;
        private String roomAmenity;
        private int deleted;

        private String roomImg1;
        private String roomImg2;
        private String roomImg3;
        private String roomImg4;

        public int getRoomCode() { return roomCode; }
        public int getAccCode() { return accCode; }
        public String getAccName() { return accName; }
        public int getAccDeleted() { return accDeleted; }

        public String getRoomName() { return roomName; }
        public int getRoomPrice() { return roomPrice; }
        public int getRoomCount() { return roomCount; }
        public int getRoomCapacity() { return roomCapacity; }
        public String getRoomInfo() { return roomInfo; }
        public String getRoomAmenity() { return roomAmenity; }
        public int getDeleted() { return deleted; }

        public String getRoomImg1() { return roomImg1; }
        public String getRoomImg2() { return roomImg2; }
        public String getRoomImg3() { return roomImg3; }
        public String getRoomImg4() { return roomImg4; }
    }

    // 3. ResultSet을 Room DTO로 변환
    private Room mapRoom(ResultSet rs) throws Exception {

        Room room = new Room();

        room.roomCode = rs.getInt("room_code");
        room.accCode = rs.getInt("acc_code");
        room.accName = rs.getString("acc_name");
        room.accDeleted = rs.getInt("acc_chk_del");

        room.roomName = rs.getString("room_name");
        room.roomPrice = rs.getInt("room_price");
        room.roomCount = rs.getInt("room_count");
        room.roomCapacity = rs.getInt("room_capacity");
        room.roomInfo = rs.getString("room_info");
        room.roomAmenity = rs.getString("room_amenity");
        room.deleted = rs.getInt("room_chk_del");

        room.roomImg1 = rs.getString("room_img1");
        room.roomImg2 = rs.getString("room_img2");
        room.roomImg3 = rs.getString("room_img3");
        room.roomImg4 = rs.getString("room_img4");

        return room;
    }

    // 4. 검색 조건 SQL 생성
    private String searchWhere(String searchType, String keyword,
                               Integer status, Integer accCode) {

        StringBuilder sql = new StringBuilder(" WHERE 1=1 ");

        // 특정 숙소의 객실만 조회
        if (accCode != null) {
            sql.append(" AND r.acc_code=? ");
        }

        // 상태 필터
        // null이면 정상 객실만 조회
        if (status == null) {
            sql.append(" AND r.room_chk_del=0 ");
        } else {
            sql.append(" AND r.room_chk_del=? ");
        }

        // 검색 조건
        if (keyword != null && !keyword.trim().isEmpty()) {

            if ("roomName".equals(searchType)) {
                sql.append(" AND r.room_name LIKE ? ");
            } else if ("accName".equals(searchType)) {
                sql.append(" AND a.acc_name LIKE ? ");
            }
        }

        return sql.toString();
    }

    // 5. 검색 파라미터 설정
    private int bindSearch(PreparedStatement pstmt, String searchType,
                           String keyword, Integer status,
                           Integer accCode) throws Exception {

        int index = 1;

        if (accCode != null) {
            pstmt.setInt(index++, accCode);
        }

        if (status != null) {
            pstmt.setInt(index++, status);
        }

        if (keyword != null && !keyword.trim().isEmpty()
                && ("roomName".equals(searchType)
                || "accName".equals(searchType))) {

            pstmt.setString(index++, "%" + keyword.trim() + "%");
        }

        return index;
    }

    // 6. 객실 검색 결과 건수
    public int count(String searchType, String keyword,
                     Integer status, Integer accCode) {

        String sql = "SELECT COUNT(*) "
                   + "FROM room r "
                   + "JOIN accommodation a ON r.acc_code=a.acc_code "
                   + searchWhere(searchType, keyword, status, accCode);

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            bindSearch(pstmt, searchType, keyword, status, accCode);

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

    // 7. 객실 목록 조회 및 페이징
    public List<Room> list(String searchType, String keyword,
                           Integer status, Integer accCode,
                           int page, int pageSize) {

        List<Room> result = new ArrayList<>();

        String sql = "SELECT r.*, a.acc_name, a.acc_chk_del "
                   + "FROM room r "
                   + "JOIN accommodation a ON r.acc_code=a.acc_code "
                   + searchWhere(searchType, keyword, status, accCode)
                   + " ORDER BY r.room_code DESC LIMIT ? OFFSET ?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            int index = bindSearch(pstmt, searchType, keyword, status, accCode);

            pstmt.setInt(index++, pageSize);
            pstmt.setInt(index, (page - 1) * pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRoom(rs));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 8. 객실 상세 조회
    public Room find(int roomCode) {

        String sql = "SELECT r.*, a.acc_name, a.acc_chk_del "
                   + "FROM room r "
                   + "JOIN accommodation a ON r.acc_code=a.acc_code "
                   + "WHERE r.room_code=?";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, roomCode);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRoom(rs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 9. 객실 신규 등록
    public int insert(int accCode, String roomName, int roomPrice,
                      int roomCount, int roomCapacity,
                      String roomInfo, String roomAmenity,
                      String roomImg1, String roomImg2,
                      String roomImg3, String roomImg4) {

        /*
         * 정상 숙소에만 객실 등록 허용
         * INSERT ... SELECT로 숙소 상태를 동시에 확인
         */
        String sql = "INSERT INTO room "
                   + "(acc_code, room_name, room_price, room_count, "
                   + "room_capacity, room_info, room_amenity, "
                   + "room_img1, room_img2, room_img3, room_img4) "
                   + "SELECT acc_code, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? "
                   + "FROM accommodation "
                   + "WHERE acc_code=? AND acc_chk_del=0";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, roomName);
            pstmt.setInt(2, roomPrice);
            pstmt.setInt(3, roomCount);
            pstmt.setInt(4, roomCapacity);
            pstmt.setString(5, roomInfo);
            pstmt.setString(6, roomAmenity);
            pstmt.setString(7, roomImg1);
            pstmt.setString(8, roomImg2);
            pstmt.setString(9, roomImg3);
            pstmt.setString(10, roomImg4);
            pstmt.setInt(11, accCode);

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

    // 10. 객실 정보 수정
    public boolean update(int roomCode, String roomName,
                          int roomPrice, int roomCount, int roomCapacity,
                          String roomInfo, String roomAmenity,
                          String roomImg1, String roomImg2,
                          String roomImg3, String roomImg4) {

        /*
         * 삭제되지 않은 객실만 수정 가능
         * 객실이 소속된 숙소도 정상 상태여야 함
         */
        String sql = "UPDATE room r "
                   + "JOIN accommodation a ON r.acc_code=a.acc_code "
                   + "SET r.room_name=?, r.room_price=?, r.room_count=?, "
                   + "r.room_capacity=?, r.room_info=?, r.room_amenity=?, "
                   + "r.room_img1=?, r.room_img2=?, "
                   + "r.room_img3=?, r.room_img4=? "
                   + "WHERE r.room_code=? "
                   + "AND r.room_chk_del=0 AND a.acc_chk_del=0";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, roomName);
            pstmt.setInt(2, roomPrice);
            pstmt.setInt(3, roomCount);
            pstmt.setInt(4, roomCapacity);
            pstmt.setString(5, roomInfo);
            pstmt.setString(6, roomAmenity);
            pstmt.setString(7, roomImg1);
            pstmt.setString(8, roomImg2);
            pstmt.setString(9, roomImg3);
            pstmt.setString(10, roomImg4);
            pstmt.setInt(11, roomCode);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 11. 객실 소프트 삭제
    public boolean softDeleteRoom(int roomCode) {

        String sql = "UPDATE room "
                   + "SET room_chk_del=1 "
                   + "WHERE room_code=? AND room_chk_del=0";

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, roomCode);

            return pstmt.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 12. 객실 상태별 건수
    public int countByStatus(int status, Integer accCode) {

        String sql = "SELECT COUNT(*) FROM room r "
                   + "WHERE r.room_chk_del=?";

        if (accCode != null) {
            sql += " AND r.acc_code=?";
        }

        try (Connection con = Util.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, status);

            if (accCode != null) {
                pstmt.setInt(2, accCode);
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
}
