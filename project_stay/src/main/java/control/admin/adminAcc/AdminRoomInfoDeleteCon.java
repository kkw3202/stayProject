package control.admin.adminAcc;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminRoomDAO;
import model.User.AdminRoomDAO.Room;

@WebServlet("/AdminRoomInfoDeleteCon.do")
public class AdminRoomInfoDeleteCon extends HttpServlet {

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
        String savedToken = (String) session.getAttribute("adminRoomDeleteCsrfToken");
        String receivedToken = request.getParameter("csrfToken");

        if (savedToken == null || receivedToken == null
                || !Objects.equals(savedToken, receivedToken)) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 삭제 요청입니다.");
            return;
        }

        // 3. 객실코드 확인
        int roomCode;

        try {
            roomCode = Integer.parseInt(request.getParameter("room_code"));

            if (roomCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 객실 코드입니다.");
            return;
        }

        // 4. 기존 객실정보 조회
        AdminRoomDAO dao = new AdminRoomDAO();
        Room room = dao.find(roomCode);

        if (room == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "객실 정보를 찾을 수 없습니다.");
            return;
        }

        // 5. 객실 및 소속 숙소의 관리상태 확인
        if (room.getDeleted() != AdminRoomDAO.STATUS_ACTIVE
                || room.getAccDeleted() != 0) {

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "삭제할 수 없는 객실입니다.");
            return;
        }

        // 6. 객실 소프트 삭제 실행
        boolean result = dao.softDeleteRoom(roomCode);

        // 7. 사용한 CSRF 토큰 제거
        session.removeAttribute("adminRoomDeleteCsrfToken");

        // 8. 삭제 결과 메시지 저장
        if (result) {
            session.setAttribute("roomMsg", "객실이 정상적으로 삭제 처리되었습니다.");
        } else {
            session.setAttribute("roomMsg", "객실 삭제 처리에 실패했습니다.");
        }

        // 9. 기존 숙소의 객실 목록으로 이동
        response.sendRedirect(request.getContextPath()
                + "/AdminRoomListProc.do?acc_code=" + room.getAccCode());
    }
}
