package com.aqpg.servlet;

import com.aqpg.dao.AQPGDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet({"/admin/dashboard", "/teacher/dashboard"})
public class DashboardServlet extends HttpServlet {
    private final AQPGDao dao = new AQPGDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("stats", dao.getDashboardStats());
        if (req.getServletPath().contains("admin")) {
            req.getRequestDispatcher("/WEB-INF/views/admin-dashboard.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/teacher-dashboard.jsp").forward(req, resp);
        }
    }
}
