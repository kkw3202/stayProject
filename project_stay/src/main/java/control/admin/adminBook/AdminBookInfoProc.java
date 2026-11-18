package control.admin.adminBook;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminBookDAO;
import model.User.AdminBookDAO.Book;

@WebServlet("/AdminBookInfoProc.do")
public class AdminBookInfoProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void reqPro(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 관리자 로그인 및 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null
                || !"admin@naver.com".equals(
                        session.getAttribute("user_email"))) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "관리자만 접근할 수 있습니다."
            );
            return;
        }

        // 2. 상세 조회할 예약의 PK 가져오기
        int bookCode;

        try {
            bookCode = Integer.parseInt(
                    request.getParameter("book_code")
            );

            if (bookCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "올바르지 않은 예약 코드입니다."
            );
            return;
        }

        // 3. DAO를 이용하여 예약 정보 조회
        AdminBookDAO dao = new AdminBookDAO();
        Book book = dao.find(bookCode);

        // 4. 예약 정보가 존재하지 않는 경우
        if (book == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "예약 정보를 찾을 수 없습니다."
            );
            return;
        }

        // 5. 예약 상세 정보를 JSP에 전달
        request.setAttribute("book", book);

        /*
         * 예약 상태값
         * 1 : 예약취소
         * 2 : 예약만료
         * 3 : 예약중
         *
         * [임시 정책 - 추후 회의 결과에 따라 변경]
         * 4 : 관리자 소프트 삭제
         */

        // 6. 삭제 요청 검증용 CSRF 토큰 발급
        if (book.getStatus()
                != AdminBookDAO.STATUS_ADMIN_DELETED) {

            String csrfToken = UUID.randomUUID().toString();

            session.setAttribute(
                    "adminBookDeleteCsrfToken",
                    csrfToken
            );

            request.setAttribute(
                    "deleteCsrfToken",
                    csrfToken
            );

        } else {
            session.removeAttribute(
                    "adminBookDeleteCsrfToken"
            );
        }

        // ★ 추가 7. 예약수정 완료 메시지 전달
        String bookMsg =
                (String) session.getAttribute("bookMsg");

        if (bookMsg != null) {
            request.setAttribute("bookMsg", bookMsg);

            // 한 번 표시한 메시지는 세션에서 제거
            session.removeAttribute("bookMsg");
        }

        // 8. 예약 상세 JSP로 이동
        RequestDispatcher rd =
                request.getRequestDispatcher(
                    "/index.jsp?section=admin/adminBook/adminBookInfo.jsp"
                );

        rd.forward(request, response);
    }
}
