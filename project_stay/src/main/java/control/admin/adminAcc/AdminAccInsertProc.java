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

@WebServlet("/AdminAccInsertProc.do")
public class AdminAccInsertProc extends HttpServlet {

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

        // 2. 신규 숙소 등록용 CSRF 토큰 발급
        String csrfToken = UUID.randomUUID().toString();

        // 세션에 토큰 저장 (등록 처리 Servlet에서 검증)
        session.setAttribute("adminAccInsertCsrfToken", csrfToken);

        // JSP에서 사용할 토큰 전달
        request.setAttribute("csrfToken", csrfToken);

        // 3. 숙소 등록 화면으로 이동
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=admin/adminAcc/adminAccInsertForm.jsp");
        rd.forward(request, response);
    }
}
