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
import model.User.AdminRoomDAO;
import model.User.AdminRoomDAO.Room;

@WebServlet("/AdminRoomListProc.do")
public class AdminRoomListProc extends HttpServlet {

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

        if (!"roomName".equals(searchType) && !"accName".equals(searchType)) {
            searchType = "roomName";
        }

        if (keyword == null) {
            keyword = "";
        }

        keyword = keyword.trim();

        // 3. 객실 상태 필터
        // status가 없으면 정상 객실만 조회
        // 0 : 정상, 1 : 삭제처리
        Integer status = null;

        String statusParam = request.getParameter("status");

        if ("0".equals(statusParam)) {
            status = AdminRoomDAO.STATUS_ACTIVE;
        } else if ("1".equals(statusParam)) {
            status = AdminRoomDAO.STATUS_DELETED;
        }

        // 4. 특정 숙소의 객실 조회 여부 확인
        // acc_code가 없으면 전체 숙소의 객실 조회
        Integer accCode = null;
        Acc acc = null;

        String accCodeParam = request.getParameter("acc_code");

        if (accCodeParam != null && !accCodeParam.trim().isEmpty()) {

            try {
                accCode = Integer.parseInt(accCodeParam);

                if (accCode <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 숙소 코드입니다.");
                return;
            }

            // 해당 숙소 정보 조회
            AdminAccDAO accDao = new AdminAccDAO();
            acc = accDao.find(accCode);

            if (acc == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "숙소 정보를 찾을 수 없습니다.");
                return;
            }
        }

        // 5. 현재 페이지 번호
        int pageNo = 1;

        try {
            pageNo = Integer.parseInt(request.getParameter("page"));

            if (pageNo < 1) {
                pageNo = 1;
            }

        } catch (NumberFormatException e) {
            pageNo = 1;
        }

        // 6. DAO를 이용하여 객실 검색 및 건수 조회
        AdminRoomDAO dao = new AdminRoomDAO();

        int total = dao.count(searchType, keyword, status, accCode);

        int pages = (int) Math.ceil((double) total / PAGE_SIZE);

        if (pages > 0 && pageNo > pages) {
            pageNo = pages;
        }

        List<Room> roomList = dao.list(
                searchType,
                keyword,
                status,
                accCode,
                pageNo,
                PAGE_SIZE
        );

        // 7. 객실 상태별 건수
        // 특정 숙소 조회 시 해당 숙소의 객실만 집계
        int activeCount = dao.countByStatus(
                AdminRoomDAO.STATUS_ACTIVE, accCode
        );

        int deletedCount = dao.countByStatus(
                AdminRoomDAO.STATUS_DELETED, accCode
        );

        // 8. JSP에서 사용할 데이터 전달
        request.setAttribute("roomList", roomList);
        request.setAttribute("total", total);
        request.setAttribute("pages", pages);
        request.setAttribute("pageNo", pageNo);

        request.setAttribute("searchType", searchType);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);

        request.setAttribute("accCode", accCode);
        request.setAttribute("acc", acc);

        request.setAttribute("activeCount", activeCount);
        request.setAttribute("deletedCount", deletedCount);

        // 9. 객실 등록, 수정, 삭제 처리 결과 메시지
        String roomMsg = (String) session.getAttribute("roomMsg");

        if (roomMsg != null) {
            request.setAttribute("roomMsg", roomMsg);
            session.removeAttribute("roomMsg");
        }

        // 10. 객실관리 목록 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher(
                "/index.jsp?section=admin/adminAcc/adminRoomList.jsp"
        );

        rd.forward(request, response);
    }
}
