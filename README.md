# AI Powered Automatic Question Paper Generator System (Java + JSP/Servlet + JDBC + MySQL)

This project is a **college-ready full-stack web application** built strictly using:
- Java Servlet + JSP
- JDBC
- MySQL
- HTML/CSS/JavaScript
- Bootstrap 5 + AdminLTE-inspired dashboard UI

## Features delivered

### Public + Authentication
1. Public landing page (`/home`)
2. Login page (`/login`) with role-based redirect for admin/teacher/student

### Admin/Teacher UI
3. Admin dashboard (`/admin/dashboard`) with cards
4. Teacher dashboard (`/teacher/dashboard`)
5. Question paper details page (`/teacher/paper/details`)
6. Question list page (`/teacher/questions?type=single`)
7. Generate paper modal popup (in details page)
8. Generated paper page (`/teacher/paper/view?paperId=...`)
9. Print view page (`/teacher/paper/print?paperId=...`)

### Teacher core flow
- Add question with options + difficulty + marks + type
- Generate paper by selecting easy/medium/hard counts
- Random fetching logic by difficulty using `ORDER BY RAND() LIMIT ?`
- No duplicate insertion into `paper_questions` using distinct check + unique key

### Student flow
- Login as student
- Start exam (`/student/exam?paperId=1`)
- Timer using JavaScript countdown and auto-submit
- Auto-evaluation via Java comparing selected options to correct options
- Store score in `results`

### Smart AI-feel logic
- Weak topic capture in `result_topics`
- Topic-based suggestion generation: “Focus on DBMS Basics” etc.

---

## Project structure

```text
src/main/java/com/aqpg
  config/DBConnection.java
  dao/AQPGDao.java
  servlet/*.java
  model/*.java
src/main/webapp
  WEB-INF/views/*.jsp
  assets/css/style.css
sql/aqpg_schema.sql
```

---

## JDBC Connection

`DBConnection.java` uses:

```java
jdbc:mysql://localhost:3306/aqpg
```

Default credentials in code:
- user: `root`
- password: `` (empty)

Change these in `DBConnection.java` if needed for your machine.

---

## MySQL setup

1. Start MySQL (XAMPP/WAMP/standalone).
2. Import SQL:
   - open MySQL CLI or phpMyAdmin SQL console
   - run `sql/aqpg_schema.sql`

Quick CLI command:

```bash
mysql -u root -p < sql/aqpg_schema.sql
```

---

## Tomcat deployment (Servlet/JSP)

### Option A: IntelliJ / Eclipse
1. Open as Maven project.
2. Configure Apache Tomcat (10+ for Jakarta packages).
3. Deploy artifact `aqpg:war exploded`.
4. Run and visit:
   - `http://localhost:8080/aqpg/home`

### Option B: Terminal

```bash
mvn clean package
```

WAR generated at:

```text
target/aqpg-1.0.0.war
```

Copy WAR to Tomcat `webapps/` and start Tomcat.

---

## Login credentials (seed data)

- Admin: `admin@aqpg.com / admin123`
- Teacher: `teacher@aqpg.com / teacher123`
- Student: `student@aqpg.com / student123`

---

## Important SQL logic references

### Random difficulty-based generation

```sql
SELECT * FROM questions
WHERE class_id=? AND difficulty='easy'
ORDER BY RAND()
LIMIT ?;
```

### Duplicate-safe mapping

```sql
SELECT DISTINCT question_id FROM paper_questions WHERE paper_id=?;
```

And DB-level protection:

```sql
UNIQUE KEY uq_paper_question(paper_id, question_id)
```

### Weak-topic detection

```sql
SELECT topic, COUNT(*)
FROM result_topics
WHERE wrong_answers > 0
GROUP BY topic
ORDER BY COUNT(*) DESC;
```

---

## Bonus notes

- “Reload = New Paper” supported because generation uses random selection each request.
- Print-friendly mode included with:

```css
@media print {
  body { background: white; }
}
```

- PDF Upload feature can be integrated by adding Apache PDFBox + a new upload servlet.

---

## College submission checklist

- Java + MySQL only ✅
- JDBC connectivity ✅
- Admin/Teacher/Student flows ✅
- Random paper generation with easy/medium/hard ✅
- No duplicate question mapping ✅
- Dashboard UI in educational style with sidebar/topbar/cards ✅
- Printable generated paper ✅
