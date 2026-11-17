package control.admin.adminCustomer;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.AdminCustomerDAO;


@WebServlet("/admincustomerInfoModProc.do")
public class admincustomerInfoModProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 if (!AdminCustomerSupport.admin(request, response)) return;

	        try {
	            AdminCustomerDAO.Member m = new AdminCustomerDAO().find(AdminCustomerSupport.number(request.getParameter("user_code")));

	            if (m == null || m.getDeleted() != 0) {
	            	response.sendRedirect(request.getContextPath() + "/AdminCustomerListProc.do" );
	                return;
	            }

	            request.setAttribute("member", m);

	            RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=admin/adminCustomer/adminCustomerModForm.jsp");
	            rd.forward(request, response);

	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
	
	
	}

}
