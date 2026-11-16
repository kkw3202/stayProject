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

@WebServlet("/AdminAccInsertCon.do")
public class AdminAccInsertCon extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST 요청만 허용합니다.");
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

        // 2. CSRF 토큰 검증
        String sessionToken = (String) session.getAttribute("adminAccInsertCsrfToken");
        String requestToken = request.getParameter("csrfToken");

        if (sessionToken == null || requestToken == null || !sessionToken.equals(requestToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "잘못된 등록 요청입니다.");
            return;
        }

        // 3. 숙소 등록 데이터 가져오기
        String cityCodeParam = request.getParameter("city_code");
        String accType = request.getParameter("acc_type");
        String accName = request.getParameter("acc_name");
        String accAddress = request.getParameter("acc_address");
        String accFacility = request.getParameter("acc_facility");
        String checkinParam = request.getParameter("acc_checkinTime");
        String checkoutParam = request.getParameter("acc_checkoutTime");
        String accImg = request.getParameter("acc_img");

        // 4. 필수값, 데이터 길이 및 형식 검사
        int cityCode;
        Time checkinTime;
        Time checkoutTime;

        try {
            if (cityCodeParam == null || accType == null || accName == null
                    || accAddress == null || accFacility == null
                    || checkinParam == null || checkoutParam == null
                    || accImg == null) {
                throw new IllegalArgumentException();
            }

            cityCode = Integer.parseInt(cityCodeParam);

            // MySQL의 부호 있는 TINYINT는 최대 127
            if (cityCode < 1 || cityCode > 127) {
                throw new IllegalArgumentException();
            }

            accType = accType.trim();
            accName = accName.trim();
            accAddress = accAddress.trim();
            accFacility = accFacility.trim();
            accImg = accImg.trim();

            // 숙소유형 검사
            if (!"호텔".equals(accType)
                    && !"모텔".equals(accType)
                    && !"펜션".equals(accType)) {
                throw new IllegalArgumentException();
            }

            // accommodation 테이블의 VARCHAR 길이 검사
            if (accName.isEmpty() || accName.length() > 30
                    || accAddress.isEmpty() || accAddress.length() > 35
                    || accFacility.isEmpty() || accFacility.length() > 50
                    || accImg.isEmpty() || accImg.length() > 20) {
                throw new IllegalArgumentException();
            }

            // HTML input type="time"은 보통 HH:mm 형식
            if (checkinParam.length() == 5) {
                checkinParam += ":00";
            }

            if (checkoutParam.length() == 5) {
                checkoutParam += ":00";
            }

            checkinTime = Time.valueOf(checkinParam);
            checkoutTime = Time.valueOf(checkoutParam);

        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "입력한 숙소 정보를 확인해 주세요.");
            return;
        }

        // 5. DAO를 이용하여 신규 숙소 등록
        AdminAccDAO dao = new AdminAccDAO();

        int accCode = dao.insert(
                cityCode,
                accType,
                accName,
                accAddress,
                accFacility,
                checkinTime,
                checkoutTime,
                accImg
        );

        // 6. 사용한 CSRF 토큰 제거
        session.removeAttribute("adminAccInsertCsrfToken");

        // 7. 등록 결과에 따라 페이지 이동
        if (accCode > 0) {

            session.setAttribute("accMsg", "신규 숙소가 등록되었습니다.");

            // 등록한 숙소의 상세 페이지로 이동
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminAccInfoProc.do?acc_code=" + accCode
            );

        } else {

            session.setAttribute("accMsg", "숙소 등록에 실패했습니다.");

            response.sendRedirect(
                    request.getContextPath() + "/AdminAccListProc.do"
            );
        }
    }
}
