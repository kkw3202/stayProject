package control.admin.adminCustomer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.AdminCustomerDAO;


@WebServlet("/AdminCustomerListProc.do")
public class AdminCustomerListProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!AdminCustomerSupport.admin(request, response)) return;

        String type = request.getParameter("searchType");

        if (!"name".equals(type) && !"phone".equals(type)) {
            type = "email";
        }

        String keyword = request.getParameter("keyWord");

        if (keyword == null) keyword = "";
        if (keyword.length() > 100) {
            keyword = keyword.substring(0, 100);
        }

        int page = AdminCustomerSupport.number(
                request.getParameter("page"));

        try {
            AdminCustomerDAO dao = new AdminCustomerDAO();

            int total = dao.count(type, keyword);
            int pages = Math.max(1, (total + 9) / 10);

            page = Math.min(page, pages);

            request.setAttribute("members",
                    dao.list(type, keyword, page, 10));

            request.setAttribute("total", total);
            request.setAttribute("pages", pages);
            request.setAttribute("pageNo", page);
            request.setAttribute("searchType", type);
            request.setAttribute("keyWord", keyword);

            request.getRequestDispatcher(
                "/index.jsp?section=admin/adminCustomer/adminCustomerList.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
	}

}
