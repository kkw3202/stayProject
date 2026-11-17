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

// ★ 수정 : 숙소 상세정보 JSP의 링크와 매핑 일치
@WebServlet("/AdminAccInfoModProc.do")
public class AdminAccInfoModProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 관리자 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null ||
            !"admin@naver.com".equals(session.getAttribute("user_email"))) {

            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "관리자만 접근할 수 있습니다."
            );
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
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "올바르지 않은 숙소코드입니다."
            );
            return;
        }

        // 3. 숙소정보 조회
        AdminAccDAO dao = new AdminAccDAO();
        Acc acc = dao.find(accCode);

        if (acc == null) {
            response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "숙소정보를 찾을 수 없습니다."
            );
            return;
        }

        // ★ 추가 : 삭제된 숙소는 수정 불가
        if (acc.getDeleted() != AdminAccDAO.STATUS_ACTIVE) {
            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "삭제 처리된 숙소는 수정할 수 없습니다."
            );
            return;
        }

        // 4. 수정용 CSRF 토큰 발급
        String csrfToken = UUID.randomUUID().toString();

        session.setAttribute("adminAccModCsrfToken", csrfToken);

        // 5. JSP에 데이터 전달
        request.setAttribute("acc", acc);
        request.setAttribute("csrfToken", csrfToken);

        // 6. 수정 화면으로 이동
        RequestDispatcher rd = request.getRequestDispatcher(
        	"/index.jsp?section=admin/adminAcc/adminAccModForm.jsp"
        );

        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED,
            "GET 방식으로 요청해 주세요."
        );
    }
}
