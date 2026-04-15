package com.aqpg.model;

public class Question {
    private int id;
    private int classId;
    private String questionText;
    private String difficulty;
    private int marks;
    private String type;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
