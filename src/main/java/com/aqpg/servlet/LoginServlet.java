package com.aqpg.servlet;

import com.aqpg.dao.AQPGDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AQPGDao dao = new AQPGDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        if (dao.login(email, password, role)) {
            HttpSession session = req.getSession();
            session.setAttribute("userEmail", email);
            session.setAttribute("role", role);
            session.setAttribute("userId", 1);
            if ("teacher".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/teacher/dashboard");
            } else if ("admin".equals(role)) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/student/exam?paperId=1");
            }
        } else {
            req.setAttribute("error", "Invalid credentials");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
