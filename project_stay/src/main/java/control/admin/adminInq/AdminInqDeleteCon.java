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

@WebServlet("/AdminInqDeleteCon.do")
public class AdminInqDeleteCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // 1. GET 요청으로 삭제 처리 방지
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(
                HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "POST 요청만 허용됩니다."
        );
    }

    // 2. POST 요청 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    // 3. 문의 삭제 처리
    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 4. 관리자 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "관리자만 접근할 수 있습니다."
            );
            return;
        }

        // 5. CSRF 토큰 검증
        String sessionToken =
                (String) session.getAttribute("adminInqDeleteCsrfToken");

        String requestToken = request.getParameter("csrfToken");

        if (sessionToken == null
                || requestToken == null
                || !sessionToken.equals(requestToken)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "잘못된 삭제 요청입니다."
            );
            return;
        }

        // 6. 문의번호 검증
        int inqCode;

        try {
            inqCode = Integer.parseInt(
                    request.getParameter("inq_code")
            );

            if (inqCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "올바르지 않은 문의번호입니다."
            );
            return;
        }

        // 7. 원본 문의 조회
        AdminInqDAO dao = new AdminInqDAO();
        Inq inq = dao.find(inqCode);

        if (inq == null
                || inq.getReStep() != AdminInqDAO.STEP_QUESTION) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "원본 문의를 찾을 수 없습니다."
            );
            return;
        }

        // 8. 이미 삭제된 문의인지 확인
        if (inq.getDeleted() != AdminInqDAO.STATUS_ACTIVE) {

            session.setAttribute(
                    "inqMsg",
                    "이미 삭제 처리된 문의입니다."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminInqInfoProc.do?inq_code=" + inqCode
            );
            return;
        }

        // 9. 원본 문의와 답변 함께 소프트 삭제
        boolean success = dao.softDeleteInq(inqCode);

        // 10. 사용한 CSRF 토큰 제거
        session.removeAttribute("adminInqDeleteCsrfToken");

        // 11. 처리 결과 메시지
        if (success) {

            session.setAttribute(
                    "inqMsg",
                    "문의와 연결된 답변이 삭제 처리되었습니다."
            );

        } else {

            session.setAttribute(
                    "inqMsg",
                    "문의 삭제에 실패했습니다."
            );
        }

        // 12. 문의 목록으로 이동
        response.sendRedirect(
                request.getContextPath()
                + "/AdminInqListProc.do"
        );
    }
}
