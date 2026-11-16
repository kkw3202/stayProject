package control.admin.adminInq;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminInqDAO;
import model.User.AdminInqDAO.Inq;

@WebServlet("/AdminInqInfoProc.do")
public class AdminInqInfoProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 1. GET 요청 처리
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    // 2. POST 요청 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    // 3. 공통 요청 처리
    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 4. 관리자 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }

        // 5. 문의번호 확인
        int inqCode;

        try {
            inqCode = Integer.parseInt(request.getParameter("inq_code"));

            if (inqCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 문의번호입니다.");
            return;
        }

        // 6. 원본 문의 조회
        AdminInqDAO dao = new AdminInqDAO();
        Inq inq = dao.find(inqCode);

        if (inq == null || inq.getReStep() != AdminInqDAO.STEP_QUESTION) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "문의내역을 찾을 수 없습니다.");
            return;
        }

        // 7. 연결된 관리자 답변 조회
        Inq reply = dao.findReply(inq.getInqRef());

        // 8. JSP에 전달할 데이터
        request.setAttribute("inq", inq);
        request.setAttribute("reply", reply);

        // 9. 정상 문의에만 삭제 토큰 발급
        if (inq.getDeleted() == AdminInqDAO.STATUS_ACTIVE) {

            String deleteCsrfToken = UUID.randomUUID().toString();

            session.setAttribute("adminInqDeleteCsrfToken", deleteCsrfToken);
            request.setAttribute("deleteCsrfToken", deleteCsrfToken);

        } else {
            session.removeAttribute("adminInqDeleteCsrfToken");
        }

        // 10. 답변이 없는 정상 문의에만 답변 작성 허용
        boolean canReply =
                inq.getDeleted() == AdminInqDAO.STATUS_ACTIVE
                && inq.getChkStatus() == AdminInqDAO.STATUS_WAITING
                && reply == null;

        request.setAttribute("canReply", canReply);

        // 11. 처리 결과 메시지
        String inqMsg = (String) session.getAttribute("inqMsg");

        if (inqMsg != null) {
            request.setAttribute("inqMsg", inqMsg);
            session.removeAttribute("inqMsg");
        }

        // 12. 문의 상세 JSP 호출
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminInq/adminInqInfo.jsp"
        );

        rd.forward(request, response);
    }
}
