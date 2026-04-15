package com.aqpg.dao;

import com.aqpg.config.DBConnection;
import com.aqpg.model.Option;
import com.aqpg.model.Question;

import java.sql.*;
import java.util.*;

public class AQPGDao {

    public boolean login(String email, String password, String role) {
        String sql = "SELECT id FROM users WHERE email=? AND password=? AND role=?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, role);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Integer> getDashboardStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("courses", count("courses"));
        stats.put("classes", count("classes"));
        stats.put("papers", count("question_papers"));
        stats.put("users", count("users"));
        return stats;
    }

    private int count(String table) {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addQuestion(Question q, List<Option> options) {
        String questionSql = "INSERT INTO questions(class_id,question_text,difficulty,marks,type,topic) VALUES(?,?,?,?,?,?)";
        String optionSql = "INSERT INTO options(question_id,option_text,is_correct) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement qps = con.prepareStatement(questionSql, Statement.RETURN_GENERATED_KEYS)) {
                qps.setInt(1, q.getClassId());
                qps.setString(2, q.getQuestionText());
                qps.setString(3, q.getDifficulty());
                qps.setInt(4, q.getMarks());
                qps.setString(5, q.getType());
                qps.setString(6, "DBMS Basics");
                qps.executeUpdate();
                ResultSet keys = qps.getGeneratedKeys();
                if (keys.next()) {
                    int questionId = keys.getInt(1);
                    try (PreparedStatement ops = con.prepareStatement(optionSql)) {
                        for (Option option : options) {
                            ops.setInt(1, questionId);
                            ops.setString(2, option.getOptionText());
                            ops.setBoolean(3, option.isCorrect());
                            ops.addBatch();
                        }
                        ops.executeBatch();
                    }
                }
            }
            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Question> listQuestionsByType(String type) {
        String sql = "SELECT * FROM questions WHERE type=? ORDER BY id DESC";
        List<Question> questions = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setClassId(rs.getInt("class_id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setDifficulty(rs.getString("difficulty"));
                q.setMarks(rs.getInt("marks"));
                q.setType(rs.getString("type"));
                questions.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    public int createPaper(int classId, String title, int createdBy) {
        String sql = "INSERT INTO question_papers(class_id,title,created_by) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, classId);
            ps.setString(2, title);
            ps.setInt(3, createdBy);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<Question> randomQuestionsByDifficulty(int classId, String difficulty, int limit) {
        String sql = "SELECT * FROM questions WHERE class_id=? AND difficulty=? ORDER BY RAND() LIMIT ?";
        List<Question> questions = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ps.setString(2, difficulty);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setId(rs.getInt("id"));
                q.setClassId(rs.getInt("class_id"));
                q.setQuestionText(rs.getString("question_text"));
                q.setDifficulty(rs.getString("difficulty"));
                q.setMarks(rs.getInt("marks"));
                q.setType(rs.getString("type"));
                questions.add(q);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    public void insertPaperQuestionsDistinct(int paperId, List<Question> questions) {
        String checkSql = "SELECT DISTINCT question_id FROM paper_questions WHERE paper_id=?";
        String insertSql = "INSERT INTO paper_questions(paper_id,question_id) VALUES(?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement check = con.prepareStatement(checkSql);
             PreparedStatement insert = con.prepareStatement(insertSql)) {
            check.setInt(1, paperId);
            Set<Integer> used = new HashSet<>();
            ResultSet rs = check.executeQuery();
            while (rs.next()) used.add(rs.getInt(1));

            for (Question q : questions) {
                if (!used.contains(q.getId())) {
                    insert.setInt(1, paperId);
                    insert.setInt(2, q.getId());
                    insert.addBatch();
                    used.add(q.getId());
                }
            }
            insert.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, Object>> paperQuestions(int paperId) {
        String sql = "SELECT q.* FROM paper_questions pq JOIN questions q ON pq.question_id=q.id WHERE pq.paper_id=? ORDER BY q.type, q.id";
        List<Map<String, Object>> rows = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, paperId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("text", rs.getString("question_text"));
                row.put("type", rs.getString("type"));
                row.put("marks", rs.getInt("marks"));
                row.put("options", optionsByQuestion(rs.getInt("id")));
                rows.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    public List<Option> optionsByQuestion(int questionId) {
        String sql = "SELECT * FROM options WHERE question_id=?";
        List<Option> options = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Option o = new Option();
                o.setId(rs.getInt("id"));
                o.setQuestionId(rs.getInt("question_id"));
                o.setOptionText(rs.getString("option_text"));
                o.setCorrect(rs.getBoolean("is_correct"));
                options.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return options;
    }

    public int evaluateAndStore(int studentId, int paperId, Map<Integer, List<Integer>> answers) {
        int total = 0;
        int wrong = 0;
        String query = "SELECT q.id qid, q.marks, q.topic, o.id oid, o.is_correct FROM paper_questions pq " +
                "JOIN questions q ON pq.question_id=q.id LEFT JOIN options o ON o.question_id=q.id WHERE pq.paper_id=?";
        Map<Integer, Integer> marksByQ = new HashMap<>();
        Map<Integer, String> topicByQ = new HashMap<>();
        Map<Integer, Set<Integer>> correct = new HashMap<>();
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, paperId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int qid = rs.getInt("qid");
                marksByQ.put(qid, rs.getInt("marks"));
                topicByQ.put(qid, rs.getString("topic"));
                if (rs.getBoolean("is_correct")) {
                    correct.computeIfAbsent(qid, k -> new HashSet<>()).add(rs.getInt("oid"));
                }
            }
            for (Integer qid : marksByQ.keySet()) {
                Set<Integer> given = new HashSet<>(answers.getOrDefault(qid, Collections.emptyList()));
                Set<Integer> cset = correct.getOrDefault(qid, Collections.emptySet());
                if (given.equals(cset)) {
                    total += marksByQ.get(qid);
                } else {
                    wrong++;
                    insertTopicWeakness(studentId, paperId, topicByQ.getOrDefault(qid, "General"));
                }
            }
            try (PreparedStatement resultPs = con.prepareStatement("INSERT INTO results(student_id,paper_id,score,wrong_answers) VALUES(?,?,?,?)")) {
                resultPs.setInt(1, studentId);
                resultPs.setInt(2, paperId);
                resultPs.setInt(3, total);
                resultPs.setInt(4, wrong);
                resultPs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return total;
    }

    private void insertTopicWeakness(int studentId, int paperId, String topic) throws SQLException {
        String sql = "INSERT INTO result_topics(student_id,paper_id,topic,wrong_answers) VALUES(?,?,?,1)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, paperId);
            ps.setString(3, topic);
            ps.executeUpdate();
        }
    }

    public List<String> weakTopicSuggestions() {
        String sql = "SELECT topic, COUNT(*) c FROM result_topics WHERE wrong_answers > 0 GROUP BY topic ORDER BY c DESC";
        List<String> suggestions = new ArrayList<>();
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String topic = rs.getString("topic");
                suggestions.add("Focus on " + topic);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (suggestions.isEmpty()) {
            suggestions.add("Practice Medium Level Questions");
        }
        return suggestions;
    }
}
