package control.user.mypage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WishList.WishListDAO;


@WebServlet("/myWishDeleteCon.do")
public class myWishDeleteCon extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("utf-8");
		// 정보가져오기
		int wish_code = Integer.parseInt(request.getParameter("wish_code"));
		
		// wdao 인스턴스 및 삭제하기 (소프트삭제 아님)
		WishListDAO wdao = new WishListDAO();
		wdao.deleteMywish(wish_code);
		
		// 페이지 넘기기 (내 찜 목록 그리는 proc)
		response.sendRedirect("myWishProc.do");
	}
}
