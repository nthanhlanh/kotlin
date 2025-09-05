-- Insert teachers
INSERT INTO teachers (name, department) VALUES
('Nguyen Van A', 'Computer Science'),
('Tran Thi B', 'Mathematics');

-- Insert classrooms
INSERT INTO classrooms (name, teacher_id) VALUES
('CNTT1', 1),
('MATH1', 2);

-- Insert students
INSERT INTO students (name, email, date_of_birth, class_id) VALUES
('Le Van C', 'c@example.com', '2001-05-20', 1),
('Pham Thi D', 'd@example.com', '2002-09-15', 2);

-- Insert courses
INSERT INTO courses (name, credits) VALUES
('Database Systems', 3),
('Linear Algebra', 4);

-- Insert enrollments (student-course-semester)
INSERT INTO enrollments (student_id, course_id, semester) VALUES
(1, 1, '2023-1'),
(1, 2, '2023-1'),
(2, 2, '2023-1');
