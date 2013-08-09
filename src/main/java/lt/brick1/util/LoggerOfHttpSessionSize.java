package lt.brick1.util;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static lt.brick1.util.DebugHttpSessionAttributes.debugInfoOfAttributesOf;

public class LoggerOfHttpSessionSize extends HttpFilter {
    @Override
    protected void doFilterHttp(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        System.out.println(debugInfoOfAttributesOf(request.getSession(false)));
    }
}
