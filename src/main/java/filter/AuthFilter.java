package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(urlPatterns = {"/dashboard.jsp", "/admin/*"}) // Adjust this to match protected URLs
public class AuthFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        System.out.println("AuthFilter: Processing request for " + req.getRequestURI());
        
        HttpSession session = req.getSession(false);

        // No session or not logged in
        if (session == null || session.getAttribute("role") == null) {
            System.out.println("AuthFilter: No session or role found, redirecting to login.jsp");
            resp.sendRedirect("login.jsp");
            return;
        }

        // Check role
        String role = (String) session.getAttribute("role");
        System.out.println("AuthFilter: User role is " + role);
        
        if (!"admin".equalsIgnoreCase(role)) {
            System.out.println("AuthFilter: User is not admin, redirecting to home.jsp");
            resp.sendRedirect("home.jsp");
            return;
        }

        System.out.println("AuthFilter: User is admin, proceeding to " + req.getRequestURI());
        // User is authenticated and is admin, proceed
        chain.doFilter(request, response);
    }

    public void destroy() {}
}
