package control.wishList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.WishList.WishListDAO;

@WebServlet("/wishListAddcon.do")
public class wishListAddcon extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		WishListDAO wdao = new WishListDAO();

		int acc_code = Integer.parseInt(request.getParameter("acc_code"));
		HttpSession session = request.getSession(false);
		
		if(session!=null) {
		
			String user_email = (String) session.getAttribute("user_email");
	
			wdao.addWishList(user_email, acc_code);
			
			response.sendRedirect("myWishProc.do");
			
		}else {
			// 세션 없을때 처리..
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\" defer=\"defer\">");
			out.print("alert('로그인 후 이용 가능합니다');");
			out.print("</script>");
		}
		
	}
}