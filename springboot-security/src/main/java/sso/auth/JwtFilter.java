package sso.auth;
/*
 * @author chou
 * @Description
 * @since 2019-05-17
 **/

import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "jwtFilter", urlPatterns = "/"
        , dispatcherTypes = DispatcherType.REQUEST)
@Order(1)
public class JwtFilter extends OncePerRequestFilter {

    PathMatcher pathMatcher = new AntPathMatcher("/");
    List excludes = new ArrayList();

    /**
     * Subclasses may override this to perform custom initialization.
     * All bean properties of this filter will have been set before this
     * method is invoked.
     * <p>Note: This method will be called from standard filter initialization
     * as well as filter bean initialization in a Spring application context.
     * Filter name and ServletContext will be available in both cases.
     * <p>This default implementation is empty.
     *
     * @throws ServletException if subclass initialization fails
     * @see #getFilterName()
     * @see #getServletContext()
     */
    @Override
    protected void initFilterBean() throws ServletException {
        excludes.add("*.css");
        excludes.add("*.js");
        excludes.add("*.html");
        excludes.add("*.wsdl");
        excludes.add("*.png");
        excludes.add("*.ico");
        excludes.add("*.gif");
        excludes.add("*.otf");
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //  处理忽略过滤的路径
        String uri = request.getRequestURI();
        for (Object exclude : excludes) {
            if (pathMatcher.match(((String) exclude), uri)) {

                filterChain.doFilter(request, response);
                return;
            }
        }
        String username = JwtUtil.getSubject(request);
        if (username == null) {
            //  跳转验证页面

        } else {
            filterChain.doFilter(request, response);
        }
    }
}
