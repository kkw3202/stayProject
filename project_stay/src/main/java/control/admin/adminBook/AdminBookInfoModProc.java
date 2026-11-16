package control.admin.adminBook;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminBookDAO;
import model.User.AdminBookDAO.Book;

@WebServlet("/AdminBookInfoModProc.do")
public class AdminBookInfoModProc extends HttpServlet {

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
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자만 접근할 수 있습니다." );
            return;
        }

        // 2. 수정할 예약의 기본키(book_code) 가져오기
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

        // 3. DAO를 이용하여 기존 예약 정보 조회
        AdminBookDAO dao = new AdminBookDAO();
        Book book = dao.find(bookCode);

        // 4. 예약 정보가 존재하지 않는 경우
        if (book == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "예약 정보를 찾을 수 없습니다." );
            return;
        }

        /*
         * [임시 정책 - 추후 회의 결과에 따라 변경]
         *
         * book_chk_status = 4 : 관리자 소프트 삭제
         *
         * 삭제된 예약은 상세 조회만 허용하고
         * 수정 화면으로 이동할 수 없도록 제한한다.
         */
        if (book.getStatus() == AdminBookDAO.STATUS_ADMIN_DELETED) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "삭제 처리된 예약은 수정할 수 없습니다." );
            return;
        }

        // 5. 수정 요청 검증용 CSRF 토큰 발급
        // 수정 화면에 접근할 때마다 새로운 토큰 생성
        String csrfToken = java.util.UUID.randomUUID().toString();

        session.setAttribute("adminBookCsrfToken", csrfToken);
        request.setAttribute("csrfToken", csrfToken);

        // 6. 기존 예약 정보를 수정 화면 JSP에 전달
        request.setAttribute("book", book);

        // 7. 예약 수정 화면으로 이동
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=admin/adminBook/adminBookModForm.jsp" );
        rd.forward(request, response);
    }
}
