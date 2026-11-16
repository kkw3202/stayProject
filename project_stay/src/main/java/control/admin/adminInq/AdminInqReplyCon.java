package control.admin.adminInq;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminInqDAO;
import model.User.AdminInqDAO.Inq;

@WebServlet("/AdminInqReplyCon.do")
public class AdminInqReplyCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 1. GET 요청으로 답변 등록 방지
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST 요청만 허용됩니다.");
    }

    // 2. POST 요청 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    // 3. 답변 등록 처리
    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 4. 관리자 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }

        // 5. CSRF 토큰 검증
        String sessionToken = (String) session.getAttribute("adminInqReplyCsrfToken");
        String requestToken = request.getParameter("csrfToken");

        if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 요청입니다.");
            return;
        }

        // 6. 원본 문의번호 검증
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

        // 7. 답변 제목 및 내용 수신
        String replyTitle = request.getParameter("reply_title");
        String replyContent = request.getParameter("reply_content");

        if (replyTitle == null) {
            replyTitle = "";
        }

        if (replyContent == null) {
            replyContent = "";
        }

        replyTitle = replyTitle.trim();
        replyContent = replyContent.trim();

        // 8. 입력값 검증
        if (replyTitle.isEmpty() || replyTitle.length() > 50) {
            session.setAttribute("inqMsg", "답변 제목은 1~50자로 입력해 주세요.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqReplyProc.do?inq_code=" + inqCode
            );
            return;
        }

        if (replyContent.isEmpty()) {
            session.setAttribute("inqMsg", "답변 내용을 입력해 주세요.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqReplyProc.do?inq_code=" + inqCode
            );
            return;
        }

        // 9. 원본 문의 상태 확인
        AdminInqDAO dao = new AdminInqDAO();
        Inq inq = dao.find(inqCode);

        if (inq == null || inq.getReStep() != AdminInqDAO.STEP_QUESTION) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "원본 문의를 찾을 수 없습니다.");
            return;
        }

        if (inq.getDeleted() != AdminInqDAO.STATUS_ACTIVE) {
            session.setAttribute("inqMsg", "삭제된 문의에는 답변할 수 없습니다.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqInfoProc.do?inq_code=" + inqCode
            );
            return;
        }

        if (inq.getChkStatus() != AdminInqDAO.STATUS_WAITING) {
            session.setAttribute("inqMsg", "이미 답변완료된 문의입니다.");
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqInfoProc.do?inq_code=" + inqCode
            );
            return;
        }

        // 10. 관리자 답변 등록
        // DAO 내부에서 INSERT와 UPDATE를 트랜잭션으로 처리
        boolean success = dao.insertReply(
                inqCode,
                replyTitle,
                replyContent
        );

        // 11. 사용한 CSRF 토큰 제거
        session.removeAttribute("adminInqReplyCsrfToken");

        // 12. 처리 결과 및 이동
        if (success) {
            session.setAttribute("inqMsg", "관리자 답변이 정상적으로 등록되었습니다.");
        } else {
            session.setAttribute("inqMsg", "답변 등록에 실패했습니다. 문의 상태를 확인해 주세요.");
        }

        response.sendRedirect(
                request.getContextPath()
                + "/AdminInqInfoProc.do?inq_code=" + inqCode
        );
    }
}
