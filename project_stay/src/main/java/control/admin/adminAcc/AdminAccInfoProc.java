package control.admin.adminAcc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminAccDAO;
import model.User.AdminAccDAO.Acc;

@WebServlet("/AdminAccInfoProc.do")
public class AdminAccInfoProc extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

        // 2. 상세 조회할 숙소의 기본키 가져오기
        // acc_code : accommodation 테이블의 기본키
        int accCode;

        try {
            accCode = Integer.parseInt(request.getParameter("acc_code"));

            if (accCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "올바르지 않은 숙소 코드입니다.");
            return;
        }

        // 3. DAO를 이용하여 숙소 상세정보 조회
        AdminAccDAO dao = new AdminAccDAO();
        Acc acc = dao.find(accCode);

        // 4. 숙소 정보가 존재하지 않는 경우
        if (acc == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "숙소 정보를 찾을 수 없습니다.");
            return;
        }

        // 5. 숙소 상세정보를 JSP에 전달
        request.setAttribute("acc", acc);

        /*
         * 숙소 삭제 정책
         *
         * acc_chk_del = 0 : 정상 숙소
         * acc_chk_del = 1 : 관리자 소프트 삭제
         *
         * 삭제된 숙소도 상세 조회는 허용한다.
         * 단, 수정 및 중복 삭제는 허용하지 않는다.
         *
         * 숙소 삭제 시 연결된 객실의 room_chk_del 값은 변경하지 않는다.
         */

        // 6. 삭제 요청 검증용 CSRF 토큰 발급
        if (acc.getDeleted() == AdminAccDAO.STATUS_ACTIVE) {

            String csrfToken = java.util.UUID.randomUUID().toString();

            session.setAttribute("adminAccDeleteCsrfToken", csrfToken);
            request.setAttribute("deleteCsrfToken", csrfToken);

        } else {
            // 삭제된 숙소 조회 시 기존 삭제 토큰 제거
            session.removeAttribute("adminAccDeleteCsrfToken");
        }

        // 7. 숙소 등록 및 수정 결과 메시지 전달 (추가)
        String accMsg = (String) session.getAttribute("accMsg");

        if (accMsg != null) {
            request.setAttribute("accMsg", accMsg);
            session.removeAttribute("accMsg");
        }

        // 8. 숙소 상세 JSP로 이동
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?section=admin/adminAcc/adminAccInfo.jsp");
        rd.forward(request, response);
    }
}
