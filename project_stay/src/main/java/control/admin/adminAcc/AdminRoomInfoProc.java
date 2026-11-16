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

@WebServlet("/AdminRoomInfoProc.do")
public class AdminRoomInfoProc extends HttpServlet {

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

        // 4. JSP에서 사용할 객실정보 전달
        request.setAttribute("room", room);

        // 5. 객실 삭제용 CSRF 토큰 발급
        // 정상 객실이면서 소속 숙소도 정상인 경우만 삭제 허용
        if (room.getDeleted() == AdminRoomDAO.STATUS_ACTIVE
                && room.getAccDeleted() == 0) {

            String deleteCsrfToken = UUID.randomUUID().toString();

            session.setAttribute("adminRoomDeleteCsrfToken", deleteCsrfToken);
            request.setAttribute("deleteCsrfToken", deleteCsrfToken);

        } else {

            session.removeAttribute("adminRoomDeleteCsrfToken");
        }

        // 6. 객실 등록, 수정, 삭제 처리 결과 메시지
        String roomMsg = (String) session.getAttribute("roomMsg");

        if (roomMsg != null) {
            request.setAttribute("roomMsg", roomMsg);
            session.removeAttribute("roomMsg");
        }

        // 7. 객실 상세정보 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminAcc/adminRoomInfo.jsp"
        );

        rd.forward(request, response);
    }
}
