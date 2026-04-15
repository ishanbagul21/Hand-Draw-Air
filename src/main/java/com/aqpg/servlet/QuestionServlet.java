package com.aqpg.servlet;

import com.aqpg.dao.AQPGDao;
import com.aqpg.model.Option;
import com.aqpg.model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/teacher/questions")
public class QuestionServlet extends HttpServlet {
    private final AQPGDao dao = new AQPGDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String type = req.getParameter("type");
        if (type == null) type = "single";
        req.setAttribute("questions", dao.listQuestionsByType(type));
        req.setAttribute("selectedType", type);
        req.getRequestDispatcher("/WEB-INF/views/question-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Question q = new Question();
        q.setClassId(Integer.parseInt(req.getParameter("classId")));
        q.setQuestionText(req.getParameter("questionText"));
        q.setDifficulty(req.getParameter("difficulty"));
        q.setMarks(Integer.parseInt(req.getParameter("marks")));
        q.setType(req.getParameter("type"));

        String[] optionText = req.getParameterValues("optionText");
        String[] correctIndex = req.getParameterValues("correctIndex");
        List<Option> options = new ArrayList<>();
        if (optionText != null) {
            for (int i = 0; i < optionText.length; i++) {
                Option o = new Option();
                o.setOptionText(optionText[i]);
                boolean isCorrect = false;
                if (correctIndex != null) {
                    for (String index : correctIndex) {
                        if (Integer.parseInt(index) == i) isCorrect = true;
                    }
                }
                o.setCorrect(isCorrect);
                options.add(o);
            }
        }

        dao.addQuestion(q, options);
        resp.sendRedirect(req.getContextPath() + "/teacher/questions?type=" + q.getType());
    }
}
