package control.admin.adminCustomer;

import java.io.IOException;
import javax.servlet.http.*;

final class AdminCustomerSupport {

    private AdminCustomerSupport() {}

    static boolean admin(HttpServletRequest req,
                         HttpServletResponse res) throws IOException {

        HttpSession s = req.getSession(false);

        if (s != null &&
            "admin@naver.com".equals(s.getAttribute("user_email"))) {
            return true;
        }

        res.sendError(HttpServletResponse.SC_FORBIDDEN,
                      "관리자 권한이 필요합니다.");
        return false;
    }

    static int number(String s) {
        try {
            return Math.max(1, Integer.parseInt(s));
        } catch (Exception e) {
            return 1;
        }
    }
}
