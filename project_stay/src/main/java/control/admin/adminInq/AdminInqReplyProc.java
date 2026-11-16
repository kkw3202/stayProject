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

@WebServlet("/AdminInqReplyProc.do")
public class AdminInqReplyProc extends HttpServlet {

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

        // 7. 삭제된 문의는 답변 불가
        if (inq.getDeleted() != AdminInqDAO.STATUS_ACTIVE) {
            session.setAttribute("inqMsg", "삭제된 문의에는 답변할 수 없습니다.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqInfoProc.do?inq_code=" + inqCode
            );
            return;
        }

        // 8. 답변완료 문의는 중복 답변 불가
        Inq reply = dao.findReply(inq.getInqRef());

        if (inq.getChkStatus() != AdminInqDAO.STATUS_WAITING || reply != null) {
            session.setAttribute("inqMsg", "이미 답변이 등록된 문의입니다.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqInfoProc.do?inq_code=" + inqCode
            );
            return;
        }

        // 9. 답변 등록용 CSRF 토큰 발급
        String csrfToken = UUID.randomUUID().toString();

        session.setAttribute("adminInqReplyCsrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);

        // 10. 답변 작성 JSP에 전달할 문의 정보
        request.setAttribute("inq", inq);

        // 11. 이전 처리 결과 메시지
        String inqMsg = (String) session.getAttribute("inqMsg");

        if (inqMsg != null) {
            request.setAttribute("inqMsg", inqMsg);
            session.removeAttribute("inqMsg");
        }

        // 12. 답변 작성 JSP 호출
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminInq/adminInqReplyForm.jsp"
        );

        rd.forward(request, response);
    }
}
