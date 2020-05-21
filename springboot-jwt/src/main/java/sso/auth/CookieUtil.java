package sso.auth;
/*
 * @author CHOU
 * @Description
 * @since 2019-05-17
 **/

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void create(HttpServletResponse httpServletResponse, String name, String value, Integer maxAge) {

        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);//https
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

    }

    public static void clear(HttpServletResponse httpServletResponse, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

    }

    public static String getValue(HttpServletRequest httpServletRequest, String cookieName) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, cookieName);
        return cookie == null ? null : cookie.getValue();
    }
}
