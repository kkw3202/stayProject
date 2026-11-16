package control.admin.adminBook;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminBookDAO;

@WebServlet("/AdminBookInfoModProcCon.do")
public class AdminBookInfoModProcCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 예약 정보 수정은 POST 방식으로만 허용
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
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다.");
            return;
        }

        // 2. CSRF 토큰 확인
        // 수정 화면에서 발급한 토큰과 제출된 토큰이 일치하는지 검사
        String sessionToken = (String) session.getAttribute("adminBookCsrfToken");
        String requestToken = request.getParameter("csrfToken");

        if (sessionToken == null || !sessionToken.equals(requestToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 수정 요청입니다.");
            return;
        }

        // 3. 수정할 예약 정보 가져오기
        int bookCode;
        String bookName = request.getParameter("book_name");
        String checkin = request.getParameter("book_checkin_date");
        String checkout = request.getParameter("book_checkout_date");

        Date checkinDate;
        Date checkoutDate;

        try {
            bookCode = Integer.parseInt(request.getParameter("book_code"));
            checkinDate = Date.valueOf(checkin);
            checkoutDate = Date.valueOf(checkout);

            if (bookCode <= 0) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "예약 정보가 올바르지 않습니다.");
            return;
        }

        // 4. 입력값 검증
        if (bookName == null || bookName.trim().isEmpty() || bookName.trim().length() > 20) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "예약자명은 1~20자로 입력해 주세요.");
            return;
        }

        if (!checkoutDate.after(checkinDate)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "퇴실일은 입실일보다 늦어야 합니다.");
            return;
        }

        // 5. DAO를 이용하여 예약 정보 수정
        AdminBookDAO dao = new AdminBookDAO();

        /*
         * DAO의 update() 메서드는 다음 항목을 수정한다.
         *
         * book_name          : 예약자명
         * book_checkin_date  : 입실일
         * book_checkout_date : 퇴실일
         *
         * [임시 정책 - 추후 회의 결과에 따라 변경]
         * book_chk_status = 4 : 관리자 소프트 삭제
         *
         * 상태값이 4인 예약은 DAO에서 수정하지 않는다.
         */
        boolean result = dao.update(bookCode, bookName, checkinDate, checkoutDate);

        // 6. 수정 요청에 사용한 CSRF 토큰 폐기
        // 다음 수정 화면에서는 새로운 토큰을 발급받아야 함
        session.removeAttribute("adminBookCsrfToken");

        // 7. 수정 결과 메시지 저장
        if (result) {
            session.setAttribute("bookMsg", "예약 정보가 수정되었습니다.");
        } else {
            session.setAttribute("bookMsg", "예약 정보 수정에 실패했습니다.");
        }

        // 8. 수정 결과에 따라 페이지 이동
        if (result) {
            response.sendRedirect(request.getContextPath() + "/AdminBookInfoProc.do?book_code=" + bookCode);
        } else {
            response.sendRedirect(request.getContextPath() + "/AdminBookInfoModProc.do?book_code=" + bookCode);
        }
    }
}
