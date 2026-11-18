package control.admin.adminAcc;

import java.io.IOException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User.AdminAccDAO;

// ★ 수정 : JSP form action과 Servlet 매핑 일치
@WebServlet("/AdminAccInfoModProcCon.do")
public class AdminAccInfoModProcCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ★ 수정 : GET 방식 수정 요청 차단
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendError(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED,
            "POST 방식으로 요청해 주세요."
        );
    }

    // ★ 수정 : 숙소정보 DB 수정 처리
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. 관리자 로그인 및 권한 확인
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
            (String) session.getAttribute("adminAccModCsrfToken");

        String requestToken =
            request.getParameter("csrfToken");

        if (sessionToken == null ||
            requestToken == null ||
            !sessionToken.equals(requestToken)) {

            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "잘못된 수정 요청입니다."
            );
            return;
        }

        // 3. 수정할 숙소정보 가져오기
        int accCode;
        int cityCode;

        String accType = request.getParameter("acc_type");
        String accName = request.getParameter("acc_name");
        String accAddress = request.getParameter("acc_address");
        String accFacility = request.getParameter("acc_facility");
        String accImg = request.getParameter("acc_img");

        Time checkinTime;
        Time checkoutTime;

        // 4. 숫자 및 시간 형식 검증
        try {
            accCode = Integer.parseInt(
                request.getParameter("acc_code")
            );

            cityCode = Integer.parseInt(
                request.getParameter("city_code")
            );

            String checkin =
                request.getParameter("acc_checkinTime");

            String checkout =
                request.getParameter("acc_checkoutTime");

            checkinTime = Time.valueOf(
                checkin.length() == 5
                    ? checkin + ":00" : checkin
            );

            checkoutTime = Time.valueOf(
                checkout.length() == 5
                    ? checkout + ":00" : checkout
            );

            if (accCode <= 0 ||
                cityCode < 1 ||
                cityCode > 127) {

                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException | NullPointerException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "숙소코드, 지역코드 또는 입퇴실시간이 올바르지 않습니다."
            );
            return;
        }

        // 5. 필수 입력값 검증
        if (accName == null || accName.trim().isEmpty() ||
            accAddress == null || accAddress.trim().isEmpty() ||
            accFacility == null || accFacility.trim().isEmpty() ||
            accImg == null || accImg.trim().isEmpty() ||
            accType == null) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "필수 입력 항목을 확인해 주세요."
            );
            return;
        }

        accName = accName.trim();
        accAddress = accAddress.trim();
        accFacility = accFacility.trim();
        accImg = accImg.trim();

        // 6. 숙소유형 검증
        if (!"호텔".equals(accType) &&
            !"모텔".equals(accType) &&
            !"펜션".equals(accType)) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "올바르지 않은 숙소유형입니다."
            );
            return;
        }

        // 7. 입력 길이 검증
        if (accName.length() > 30 ||
            accAddress.length() > 35 ||
            accFacility.length() > 50 ||
            accImg.length() > 20) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "입력 가능한 글자 수를 초과했습니다."
            );
            return;
        }

        // 8. DAO를 이용하여 숙소정보 수정
        AdminAccDAO dao = new AdminAccDAO();

        boolean result = dao.update(
            accCode,
            cityCode,
            accType,
            accName,
            accAddress,
            accFacility,
            checkinTime,
            checkoutTime,
            accImg
        );

        // 9. 수정 요청에 사용한 CSRF 토큰 폐기
        session.removeAttribute("adminAccModCsrfToken");

        // 10. 수정 결과 메시지 저장
        if (result) {
            session.setAttribute(
                "accMsg",
                "숙소정보가 수정되었습니다."
            );
        } else {
            session.setAttribute(
                "accMsg",
                "숙소정보 수정에 실패했습니다."
            );
        }

        // 11. 숙소 상세정보 페이지로 이동
        response.sendRedirect(
            request.getContextPath()
            + "/AdminAccInfoProc.do?acc_code="
            + accCode
        );
    }
}
