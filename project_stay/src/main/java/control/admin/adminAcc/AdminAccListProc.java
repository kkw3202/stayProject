package control.admin.adminAcc;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminAccDAO;
import model.User.AdminAccDAO.Acc;

@WebServlet("/AdminAccListProc.do")
public class AdminAccListProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int PAGE_SIZE = 10;

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

        // 2. 검색 조건 가져오기
        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");

        if (!"name".equals(searchType) && !"type".equals(searchType) && !"address".equals(searchType)) {
            searchType = "name";
        }

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim();

        // 3. 숙소 삭제 상태 필터
        // status가 없으면 정상 숙소만 조회한다.
        // 0 : 정상, 1 : 관리자 소프트 삭제
        Integer status = null;

        String statusParam = request.getParameter("status");

        if ("0".equals(statusParam)) {
            status = 0;
        } else if ("1".equals(statusParam)) {
            status = 1;
        }

        // 4. 현재 페이지 번호
        int pageNo = 1;

        try {
            pageNo = Integer.parseInt(request.getParameter("page"));

            if (pageNo < 1) {
                pageNo = 1;
            }

        } catch (NumberFormatException e) {
            pageNo = 1;
        }

        // 5. DAO를 이용하여 숙소 검색 및 전체 건수 조회
        AdminAccDAO dao = new AdminAccDAO();

        int total = dao.count(searchType, keyword, status);
        int pages = (int) Math.ceil((double) total / PAGE_SIZE);

        if (pages > 0 && pageNo > pages) {
            pageNo = pages;
        }

        List<Acc> accList = dao.list(searchType, keyword, status, pageNo, PAGE_SIZE);

        // 6. 숙소 상태별 전체 건수
        int activeCount = dao.countByStatus(AdminAccDAO.STATUS_ACTIVE);
        int deletedCount = dao.countByStatus(AdminAccDAO.STATUS_DELETED);

        // 7. JSP에서 사용할 데이터 전달
        request.setAttribute("accList", accList);
        request.setAttribute("total", total);
        request.setAttribute("pages", pages);
        request.setAttribute("pageNo", pageNo);

        request.setAttribute("searchType", searchType);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);

        request.setAttribute("activeCount", activeCount);
        request.setAttribute("deletedCount", deletedCount);

        // 8. 등록, 수정, 삭제 처리 결과 메시지
        String accMsg = (String) session.getAttribute("accMsg");

        if (accMsg != null) {
            request.setAttribute("accMsg", accMsg);
            session.removeAttribute("accMsg");
        }

        // 9. 숙소관리 목록 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=admin/adminAcc/adminAccList.jsp");
        rd.forward(request, response);
    }
}
