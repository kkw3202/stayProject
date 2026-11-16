package control.admin.adminAcc;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminAccDAO;
import model.User.AdminAccDAO.Acc;
import model.User.AdminRoomDAO;

@WebServlet("/AdminRoomInsertCon.do")
public class AdminRoomInsertCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST 방식만 허용합니다.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 관리자 로그인 및 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }

        // 2. CSRF 토큰 검증
        String savedToken = (String) session.getAttribute("adminRoomInsertCsrfToken");
        String receivedToken = request.getParameter("csrfToken");

        if (savedToken == null || receivedToken == null
                || !Objects.equals(savedToken, receivedToken)) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 객실 등록 요청입니다.");
            return;
        }

        // 3. 숙소코드 확인
        int accCode;

        try {
            accCode = Integer.parseInt(request.getParameter("acc_code"));

            if (accCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 숙소 코드입니다.");
            return;
        }

        // 4. 숙소정보 및 등록 가능 여부 확인
        AdminAccDAO accDao = new AdminAccDAO();
        Acc acc = accDao.find(accCode);

        if (acc == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "숙소 정보를 찾을 수 없습니다.");
            return;
        }

        if (acc.getDeleted() != AdminAccDAO.STATUS_ACTIVE) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "삭제 처리된 숙소에는 객실을 등록할 수 없습니다.");
            return;
        }

        // 5. 객실 등록 입력값 가져오기
        String roomName = request.getParameter("room_name");
        String roomPriceParam = request.getParameter("room_price");
        String roomCountParam = request.getParameter("room_count");
        String roomCapacityParam = request.getParameter("room_capacity");
        String roomInfo = request.getParameter("room_info");
        String roomAmenity = request.getParameter("room_amenity");

        String roomImg1 = request.getParameter("room_img1");
        String roomImg2 = request.getParameter("room_img2");
        String roomImg3 = request.getParameter("room_img3");
        String roomImg4 = request.getParameter("room_img4");

        roomName = roomName == null ? "" : roomName.trim();
        roomInfo = roomInfo == null ? "" : roomInfo.trim();
        roomAmenity = roomAmenity == null ? "" : roomAmenity.trim();

        roomImg1 = roomImg1 == null ? "" : roomImg1.trim();
        roomImg2 = roomImg2 == null ? "" : roomImg2.trim();
        roomImg3 = roomImg3 == null ? "" : roomImg3.trim();
        roomImg4 = roomImg4 == null ? "" : roomImg4.trim();

        // 6. 숫자 입력값 검증
        int roomPrice;
        int roomCount;
        int roomCapacity;

        try {
            roomPrice = Integer.parseInt(roomPriceParam);
            roomCount = Integer.parseInt(roomCountParam);
            roomCapacity = Integer.parseInt(roomCapacityParam);

        } catch (NumberFormatException e) {
            session.setAttribute("roomMsg", "가격, 객실 수, 수용인원을 올바르게 입력해 주세요.");

            response.sendRedirect(request.getContextPath()
                    + "/AdminRoomInsertProc.do?acc_code=" + accCode);
            return;
        }

        // 7. 입력값 범위 및 길이 검증
        // room_count, room_capacity는 MySQL signed TINYINT 기준
        boolean invalid =
                roomName.isEmpty()
                || roomName.length() > 30
                || roomPrice < 0
                || roomCount < 0 || roomCount > 127
                || roomCapacity < 1 || roomCapacity > 127
                || roomInfo.length() > 50
                || roomAmenity.length() > 50
                || roomImg1.isEmpty() || roomImg1.length() > 40
                || roomImg2.isEmpty() || roomImg2.length() > 40
                || roomImg3.isEmpty() || roomImg3.length() > 40
                || roomImg4.isEmpty() || roomImg4.length() > 40;

        if (invalid) {
            session.setAttribute("roomMsg", "입력값을 확인해 주세요. 필수항목, 숫자 범위 또는 글자 수가 올바르지 않습니다.");

            response.sendRedirect(request.getContextPath()
                    + "/AdminRoomInsertProc.do?acc_code=" + accCode);
            return;
        }

        // 8. 객실 신규 등록
        AdminRoomDAO roomDao = new AdminRoomDAO();

        int roomCode = roomDao.insert(
                accCode,
                roomName,
                roomPrice,
                roomCount,
                roomCapacity,
                roomInfo,
                roomAmenity,
                roomImg1,
                roomImg2,
                roomImg3,
                roomImg4
        );

        // 9. 사용한 CSRF 토큰 제거
        session.removeAttribute("adminRoomInsertCsrfToken");

        // 10. 등록 결과에 따른 화면 이동
        if (roomCode > 0) {

            session.setAttribute("roomMsg", "신규 객실이 정상적으로 등록되었습니다.");

            response.sendRedirect(request.getContextPath()
                    + "/AdminRoomInfoProc.do?room_code=" + roomCode);

        } else {

            session.setAttribute("roomMsg", "객실 등록에 실패했습니다.");

            response.sendRedirect(request.getContextPath()
                    + "/AdminRoomInsertProc.do?acc_code=" + accCode);
        }
    }
}
