package control.admin.adminBook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminBookDAO;

@WebServlet("/AdminBookInfoDeleteCon.do")
public class AdminBookInfoDeleteCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 예약 삭제는 POST 방식으로만 허용
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST 방식으로 요청해 주세요.");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 관리자 로그인 및 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null || !"admin@naver.com".equals(session.getAttribute("user_email"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다." );
            return;
        }

        // 2. CSRF 토큰 확인
        // 예약 상세 화면에서 발급한 토큰과 제출된 토큰 비교
        String sessionToken = (String) session.getAttribute("adminBookDeleteCsrfToken");
        String requestToken = request.getParameter("csrfToken");

        if (sessionToken == null || !sessionToken.equals(requestToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 삭제 요청입니다." );
            return;
        }

        // 3. 삭제할 예약의 기본키(book_code) 가져오기
        int bookCode;

        try {
            bookCode = Integer.parseInt(request.getParameter("book_code"));

            if (bookCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 예약 코드입니다." );
            return;
        }

        // 4. DAO를 이용하여 예약 소프트 삭제
        AdminBookDAO dao = new AdminBookDAO();

        /*
         * [임시 정책 - 추후 회의 결과에 따라 변경]
         *
         * book_chk_status = 4 : 관리자 소프트 삭제
         *
         * 실제 DELETE문은 실행하지 않는다.
         * 예약 정보는 DB에 그대로 유지한다.
         *
         * book_cancel_date는 변경하지 않는다.
         * 이미 상태값이 4인 예약은 중복 삭제하지 않는다.
         */
        boolean result = dao.softDeleteBook(bookCode);

        // 5. 삭제 요청에 사용한 CSRF 토큰 닫기
        session.removeAttribute("adminBookDeleteCsrfToken");

        // 6. 삭제 결과 메시지 저장
        if (result) {
            session.setAttribute("bookMsg", "예약이 삭제 처리되었습니다.");
        } else {
            session.setAttribute("bookMsg", "예약 삭제에 실패했습니다.");
        }

        // 7. 예약 목록 페이지로 이동
        response.sendRedirect(request.getContextPath() + "/AdminBookListProc.do");
    }
}