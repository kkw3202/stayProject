package control.user.join;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User.UserDAO;
import model.User.UserDTO;

@WebServlet("/joinProc.do")
public class joinProc extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		UserDAO udao = new UserDAO();
		UserDTO udto = new UserDTO();
		
		String email = request.getParameter("email");
		String emailChk = udao.getEmail(email);
		String pass = request.getParameter("password");
		String passchk = request.getParameter("passchk");
		
		udto.setUser_email(email);
		udto.setUser_password(pass);
		udto.setUser_name(request.getParameter("name"));
		udto.setUser_phone(request.getParameter("phone"));
		udto.setUser_birth(request.getParameter("birth"));
		udto.setUser_gender(Integer.parseInt(request.getParameter("gender")));
		udto.setUser_passkey(request.getParameter("passkey"));

		String link ="";
<<<<<<< HEAD
		// ±âÁ¸ ÀÌ¸ÞÀÏ°ú °°Àº ÀÌ¸ÞÀÏÀÏ °æ¿ì
		if(emailChk != null && emailChk.equals(email)) {
			request.setAttribute("msg", "ÀÌ¹Ì Á¸ÀçÇÏ´Â ÀÌ¸ÞÀÏ ÀÔ´Ï´Ù");
			link = "/user/joinErr.jsp";
		// ºñ¹Ð¹øÈ£¿Í ºñ¹Ð¹øÈ£ È®ÀÎÀÌ ´Ù¸¥ °æ¿ì
		}else if(pass.equals(passchk)){
			udao.insertUser(udto);
			request.getSession().setAttribute("msg", "È¸¿ø°¡ÀÔÀ» ÃàÇÏÇÕ´Ï´Ù~!");
			response.sendRedirect(request.getContextPath() + "/user/joinOk.jsp"	);
			return;
		}else {
			request.setAttribute("msg", "ºñ¹Ð¹øÈ£¸¦ È®ÀÎÇÏ¼¼¿ä");
			link = "/user/joinErr.jsp";
				
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(link);
		rd.forward(request, response);
		
=======
		// ê¸°ì¡´ ì´ë©”ì¼ê³¼ ê°™ì€ ì´ë©”ì¼ì¸ ê²½ìš°
		System.out.println("email:"+email+"emailChk:"+emailChk);
		// ê¸°ì¡´ ì •ìƒ íšŒì›ê³¼ ì´ë©”ì¼ì´ ì¤‘ë³µë˜ëŠ” ê²½ìš°
		if (email != null && email.equals(emailChk)) {
		    request.setAttribute("msg", "ì´ë¯¸ ì¡´ìž¬í•˜ëŠ” ì´ë©”ì¼ìž…ë‹ˆë‹¤.");
		    request.setAttribute("joinSuccess", false);
		} else {
		    // ì‹ ê·œ íšŒì›ì€ íƒˆí‡´ ì—¬ë¶€ë¥¼ 0ìœ¼ë¡œ ì„¤ì •
		    udto.setUser_chk_del(0);
		    udao.insertUser(udto);

		    request.setAttribute("msg", "íšŒì›ê°€ìž…ì— ì„±ê³µí–ˆìŠµë‹ˆë‹¤.");
		    request.setAttribute("joinSuccess", true);
		}
		// íšŒì›ê°€ìž… í™”ë©´ì„ ë°°ê²½ìœ¼ë¡œ í‘œì‹œ
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=user/joinForm.jsp");
		rd.forward(request, response);
>>>>>>> main
	}
		
		
}
