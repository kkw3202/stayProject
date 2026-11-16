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

import model.User.AdminAccDAO;
import model.User.AdminAccDAO.Acc;

@WebServlet("/AdminRoomInsertProc.do")
public class AdminRoomInsertProc extends HttpServlet {

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

        // 2. 숙소코드 확인
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

        // 3. 숙소정보 조회
        AdminAccDAO dao = new AdminAccDAO();
        Acc acc = dao.find(accCode);

        if (acc == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "숙소 정보를 찾을 수 없습니다.");
            return;
        }

        // 4. 숙소 관리상태 확인
        // 삭제 처리된 숙소에는 신규 객실을 등록할 수 없음
        if (acc.getDeleted() != AdminAccDAO.STATUS_ACTIVE) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "삭제 처리된 숙소에는 객실을 등록할 수 없습니다."
            );
            return;
        }

        // 5. 객실 등록용 CSRF 토큰 발급
        String csrfToken = UUID.randomUUID().toString();

        session.setAttribute("adminRoomInsertCsrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);

        // 6. JSP에 숙소정보 전달
        request.setAttribute("acc", acc);

        // 7. 객실 등록 결과 메시지 전달
        String roomMsg = (String) session.getAttribute("roomMsg");

        if (roomMsg != null) {
            request.setAttribute("roomMsg", roomMsg);
            session.removeAttribute("roomMsg");
        }

        // 8. 객실 등록 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminRoom/adminRoomInsertForm.jsp"
        );

        rd.forward(request, response);
    }
}
