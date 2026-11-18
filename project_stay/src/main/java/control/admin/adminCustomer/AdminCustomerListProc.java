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

        // 1. 관리자 권한 확인
        if (!AdminCustomerSupport.admin(request, response)) {
            return;
        }

        // 2. 기존 검색 조건
        String type = request.getParameter("searchType");

        if (!"name".equals(type) && !"phone".equals(type)) {
            type = "email";
        }

        String keyword = request.getParameter("keyWord");

        if (keyword == null) {
            keyword = "";
        }

        if (keyword.length() > 100) {
            keyword = keyword.substring(0, 100);
        }

        // ★ 추가 3. 회원탈퇴여부 검색 조건
        // null : 전체회원
        // 0    : 정상회원
        // 1    : 탈퇴회원
        Integer deleted = null;

        String deletedParam = request.getParameter("deleted");

        if ("0".equals(deletedParam)) {
            deleted = 0;
        } else if ("1".equals(deletedParam)) {
            deleted = 1;
        }

        // 4. 현재 페이지 번호
        int page = AdminCustomerSupport.number(
                request.getParameter("page"));

        try {
            AdminCustomerDAO dao = new AdminCustomerDAO();

            // ★ 수정 5. 탈퇴여부 조건을 포함한 전체 건수
            int total = dao.count(type, keyword, deleted);

            int pages = Math.max(1, (total + 9) / 10);

            page = Math.min(page, pages);

            // ★ 수정 6. 탈퇴여부 조건을 포함한 회원 목록
            request.setAttribute(
                    "members",
                    dao.list(type, keyword, deleted, page, 10)
            );

            // 7. 기존 검색 및 페이징 데이터 전달
            request.setAttribute("total", total);
            request.setAttribute("pages", pages);
            request.setAttribute("pageNo", page);
            request.setAttribute("searchType", type);
            request.setAttribute("keyWord", keyword);

            // ★ 추가 8. 탈퇴여부 검색 조건 전달
            request.setAttribute("deleted", deleted);

            // 9. 회원관리 JSP 이동
            request.getRequestDispatcher(
                "/index.jsp?section=admin/adminCustomer/adminCustomerList.jsp"
            ).forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
