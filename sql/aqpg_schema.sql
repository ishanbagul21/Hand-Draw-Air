CREATE DATABASE IF NOT EXISTS aqpg;
USE aqpg;

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(120) UNIQUE NOT NULL,
  password VARCHAR(120) NOT NULL,
  role ENUM('admin','teacher','student') NOT NULL
);

CREATE TABLE courses (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL,
  description TEXT
);

CREATE TABLE classes (
  id INT PRIMARY KEY AUTO_INCREMENT,
  course_id INT NOT NULL,
  class_name VARCHAR(150) NOT NULL,
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE
);

CREATE TABLE questions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  class_id INT NOT NULL,
  question_text TEXT NOT NULL,
  difficulty ENUM('easy','medium','hard') NOT NULL,
  marks INT NOT NULL,
  type ENUM('single','multiple','text') NOT NULL,
  topic VARCHAR(120) DEFAULT 'DBMS Basics',
  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE
);

CREATE TABLE options (
  id INT PRIMARY KEY AUTO_INCREMENT,
  question_id INT NOT NULL,
  option_text VARCHAR(255) NOT NULL,
  is_correct BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE question_papers (
  id INT PRIMARY KEY AUTO_INCREMENT,
  class_id INT NOT NULL,
  title VARCHAR(160) NOT NULL,
  created_by INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
  FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE paper_questions (
  id INT PRIMARY KEY AUTO_INCREMENT,
  paper_id INT NOT NULL,
  question_id INT NOT NULL,
  UNIQUE KEY uq_paper_question(paper_id, question_id),
  FOREIGN KEY (paper_id) REFERENCES question_papers(id) ON DELETE CASCADE,
  FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE results (
  id INT PRIMARY KEY AUTO_INCREMENT,
  student_id INT NOT NULL,
  paper_id INT NOT NULL,
  score INT NOT NULL,
  wrong_answers INT DEFAULT 0,
  FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (paper_id) REFERENCES question_papers(id) ON DELETE CASCADE
);

CREATE TABLE result_topics (
  id INT PRIMARY KEY AUTO_INCREMENT,
  student_id INT NOT NULL,
  paper_id INT NOT NULL,
  topic VARCHAR(120) NOT NULL,
  wrong_answers INT DEFAULT 1
);

INSERT INTO users(name,email,password,role) VALUES
('Administrator Admin','admin@aqpg.com','admin123','admin'),
('Teacher One','teacher@aqpg.com','teacher123','teacher'),
('Student One','student@aqpg.com','student123','student');
