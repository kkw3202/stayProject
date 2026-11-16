package control.admin.adminCustomer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminCustomerDAO;


@WebServlet("/admincustomerInfoModProcCon.do")
public class admincustomerInfoModProcCon extends HttpServlet {
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

        String name = request.getParameter("user_name");
        String phone = request.getParameter("user_phone");
        String birth = request.getParameter("user_birth");
        String passkey = request.getParameter("user_passkey");

        int gender = "1".equals(request.getParameter("user_gender"))
                ? 1 : 0;

        if (name == null || name.trim().isEmpty()
                || name.length() > 20
                || phone == null || !phone.matches("[0-9]{8,11}")
                || birth == null
                || !birth.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")
                || passkey == null || passkey.trim().isEmpty()
                || passkey.length() > 20) {

            response.sendError(400, "입력값을 확인하세요.");
            return;
        }

        try {
            boolean ok = new AdminCustomerDAO().update(
                code, name.trim(), phone, birth,
                gender, passkey.trim()
            );

            session.setAttribute("adminCustomerFlash",
                ok ? "회원정보가 수정되었습니다."
                   : "수정할 수 없는 회원입니다.");

            response.sendRedirect(
                request.getContextPath()
                + "/admincustomerInfoProc.do?user_code=" + code
            );

        } catch (Exception e) {
            throw new ServletException(e);
        }
	
	}

}
