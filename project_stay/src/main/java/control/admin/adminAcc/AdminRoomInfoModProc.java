package control.admin.adminAcc;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminRoomDAO;
import model.User.AdminRoomDAO.Room;

@WebServlet("/AdminRoomInfoModProc.do")
public class AdminRoomInfoModProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
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

        // 2. 객실코드 확인
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

        // 3. 객실 상세정보 조회
        AdminRoomDAO dao = new AdminRoomDAO();
        Room room = dao.find(roomCode);

        if (room == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "객실 정보를 찾을 수 없습니다.");
            return;
        }

        // 4. 객실 및 소속 숙소의 관리상태 확인
        // 삭제된 객실이나 삭제된 숙소의 객실은 수정할 수 없음
        if (room.getDeleted() != AdminRoomDAO.STATUS_ACTIVE
                || room.getAccDeleted() != 0) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "삭제 처리된 객실 또는 숙소의 정보는 수정할 수 없습니다."
            );
            return;
        }

        // 5. 객실 수정용 CSRF 토큰 발급
        String csrfToken = UUID.randomUUID().toString();

        session.setAttribute("adminRoomCsrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);

        // 6. 수정 화면에 기존 객실정보 전달
        request.setAttribute("room", room);

        // 7. 수정 결과 메시지 전달
        String roomMsg = (String) session.getAttribute("roomMsg");

        if (roomMsg != null) {
            request.setAttribute("roomMsg", roomMsg);
            session.removeAttribute("roomMsg");
        }

        // 8. 객실 수정 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminAcc/adminRoomModForm.jsp"
        );

        rd.forward(request, response);
    }
}
