package control.admin.adminAcc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// ★ 추가 : 프로젝트의 DB 연결 유틸리티
import DBPKG.Util;

@WebServlet("/AdminAccInfoDeleteCon.do")
public class AdminAccInfoDeleteCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ★ 수정 : 삭제는 POST 방식으로만 허용
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED,
            "POST 방식으로 요청해 주세요."
        );
    }

    // ★ 추가 : 숙소 소프트 삭제 처리
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 관리자 권한 확인
        HttpSession session = request.getSession(false);

        if (session == null ||
            !"admin@naver.com".equals(
                session.getAttribute("user_email"))) {

            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "관리자만 접근할 수 있습니다."
            );
            return;
        }

        // 2. CSRF 토큰 확인
        String sessionToken =
            (String) session.getAttribute("adminAccDeleteCsrfToken");

        String requestToken =
            request.getParameter("csrfToken");

        if (sessionToken == null ||
            !sessionToken.equals(requestToken)) {

            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "잘못된 삭제 요청입니다."
            );
            return;
        }

        // 3. 숙소코드 확인
        int accCode;

        try {
            accCode = Integer.parseInt(
                request.getParameter("acc_code")
            );

            if (accCode <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "올바르지 않은 숙소코드입니다."
            );
            return;
        }

        // 4. 숙소 소프트 삭제
        boolean result = false;

        // ★ 확인 필요 : 실제 테이블명과 컬럼명
        String sql =
            "UPDATE accommodation " +
            "SET acc_chk_del = 1 " +
            "WHERE acc_code = ? " +
            "AND acc_chk_del = 0";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt =
                 conn.prepareStatement(sql)) {

            pstmt.setInt(1, accCode);

            result = pstmt.executeUpdate() == 1;

        } catch (SQLException e) {

            log("숙소 삭제 처리 중 DB 오류", e);

            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "숙소 삭제 처리 중 오류가 발생했습니다."
            );
            return;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 5. 사용한 CSRF 토큰 폐기
        session.removeAttribute("adminAccDeleteCsrfToken");

        // 6. 삭제 결과 메시지 저장
        if (result) {
            session.setAttribute(
                "accMsg",
                "숙소가 삭제 처리되었습니다."
            );
        } else {
            session.setAttribute(
                "accMsg",
                "숙소 삭제에 실패했습니다. 이미 삭제된 숙소인지 확인해 주세요."
            );
        }

        // 7. 숙소 상세정보 페이지로 이동
        response.sendRedirect(
            request.getContextPath()
            + "/AdminAccInfoProc.do?acc_code="
            + accCode
        );
    }
}
