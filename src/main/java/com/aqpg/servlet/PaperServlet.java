package com.aqpg.servlet;

import com.aqpg.dao.AQPGDao;
import com.aqpg.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/teacher/paper/details", "/teacher/paper/generate", "/teacher/paper/view", "/teacher/paper/print"})
public class PaperServlet extends HttpServlet {
    private final AQPGDao dao = new AQPGDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        int paperId = Integer.parseInt(req.getParameter("paperId") == null ? "1" : req.getParameter("paperId"));
        if (path.endsWith("details")) {
            req.getRequestDispatcher("/WEB-INF/views/question-paper-details.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("paperQuestions", dao.paperQuestions(paperId));
        if (path.endsWith("print")) {
            req.getRequestDispatcher("/WEB-INF/views/print-view.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/generated-paper.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int classId = Integer.parseInt(req.getParameter("classId"));
        int easy = Integer.parseInt(req.getParameter("easyCount"));
        int medium = Integer.parseInt(req.getParameter("mediumCount"));
        int hard = Integer.parseInt(req.getParameter("hardCount"));

        int paperId = dao.createPaper(classId, req.getParameter("title"), 1);
        List<Question> selected = new ArrayList<>();
        selected.addAll(dao.randomQuestionsByDifficulty(classId, "easy", easy));
        selected.addAll(dao.randomQuestionsByDifficulty(classId, "medium", medium));
        selected.addAll(dao.randomQuestionsByDifficulty(classId, "hard", hard));
        dao.insertPaperQuestionsDistinct(paperId, selected);

        resp.sendRedirect(req.getContextPath() + "/teacher/paper/view?paperId=" + paperId);
    }
}
