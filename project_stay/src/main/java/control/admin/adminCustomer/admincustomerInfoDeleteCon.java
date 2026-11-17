package control.admin.adminCustomer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminCustomerDAO;


@WebServlet("/admincustomerInfoDeleteCon.do")
public class admincustomerInfoDeleteCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

        if (!AdminCustomerSupport.admin(request, response)) return;

        HttpSession session = request.getSession(false);
        String token = request.getParameter("csrf");

        if (token == null ||
            !token.equals(session.getAttribute("adminCustomerCsrf"))) {
            response.sendError(403);
            return;
        }

        int code = AdminCustomerSupport.number(
        		request.getParameter("user_code"));

        try {
            boolean ok = new AdminCustomerDAO().delete(code);

            session.setAttribute("adminCustomerFlash",
                ok ? "회원이 탈퇴 처리되었습니다."
                   : "삭제할 수 없는 회원입니다.");

            response.sendRedirect(
                request.getContextPath() + "/AdminCustomerListProc.do"
            );

        } catch (Exception e) {
            throw new ServletException(e);
        }
	
	}

}
