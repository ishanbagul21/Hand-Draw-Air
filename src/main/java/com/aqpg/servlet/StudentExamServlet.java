package com.aqpg.servlet;

import com.aqpg.dao.AQPGDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;

@WebServlet("/student/exam")
public class StudentExamServlet extends HttpServlet {
    private final AQPGDao dao = new AQPGDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int paperId = Integer.parseInt(req.getParameter("paperId") == null ? "1" : req.getParameter("paperId"));
        req.setAttribute("paperId", paperId);
        req.setAttribute("paperQuestions", dao.paperQuestions(paperId));
        req.getRequestDispatcher("/WEB-INF/views/student-exam.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int paperId = Integer.parseInt(req.getParameter("paperId"));
        Map<Integer, List<Integer>> answers = new HashMap<>();
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith("q_")) {
                int qid = Integer.parseInt(name.substring(2));
                String[] vals = req.getParameterValues(name);
                List<Integer> ids = new ArrayList<>();
                if (vals != null) {
                    for (String v : vals) ids.add(Integer.parseInt(v));
                }
                answers.put(qid, ids);
            }
        }
        int score = dao.evaluateAndStore(3, paperId, answers);
        req.setAttribute("score", score);
        req.setAttribute("suggestions", dao.weakTopicSuggestions());
        req.getRequestDispatcher("/WEB-INF/views/result.jsp").forward(req, resp);
    }
}
