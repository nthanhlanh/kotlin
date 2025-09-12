-- Xóa dữ liệu cũ và reset id
TRUNCATE TABLE enrollments RESTART IDENTITY CASCADE;
TRUNCATE TABLE students RESTART IDENTITY CASCADE;
TRUNCATE TABLE classrooms RESTART IDENTITY CASCADE;
TRUNCATE TABLE teachers RESTART IDENTITY CASCADE;
TRUNCATE TABLE courses RESTART IDENTITY CASCADE;

-- 1. Teachers (100 giáo viên)
INSERT INTO teachers (name, department)
SELECT
    'Teacher ' || g,
    CASE (g % 3)
        WHEN 0 THEN 'Computer Science'
        WHEN 1 THEN 'Mathematics'
        ELSE 'Physics'
    END
FROM generate_series(1, 2000000) g;

-- 2. Classrooms (mỗi giáo viên 1 lớp)
INSERT INTO classrooms (name, teacher_id)
SELECT
    'Class ' || g,
    (SELECT id FROM teachers ORDER BY random() LIMIT 1)  -- chọn ngẫu nhiên 1 teacher
FROM generate_series(1, 2000000) g;



-- 3. Students (10,000 sinh viên)
INSERT INTO students (name, email, date_of_birth, class_id)
SELECT
    'Student ' || g,
    'student' || g || '@example.com',
    DATE '2000-01-01' + (g % 700),   -- ngày sinh lệch trong ~2 năm
    (SELECT id FROM classrooms ORDER BY random() LIMIT 1) -- random classroom
FROM generate_series(1, 2000000) g;


-- 4. Courses (1,000 môn học)
INSERT INTO courses (name, credits)
SELECT
    'Course ' || g,
    (g % 5) + 2
FROM generate_series(1, 2000000) g;

-- 5. Enrollments (mỗi sinh viên học 5 môn → 50,000 bản ghi)
INSERT INTO enrollments (student_id, course_id, semester)
SELECT
    (SELECT id FROM students ORDER BY random() LIMIT 1),  -- random student
    (SELECT id FROM courses ORDER BY random() LIMIT 1),   -- random course
    '2023-' || (FLOOR(random() * 2) + 1)                 -- random semester: 2023-1 hoặc 2023-2
FROM generate_series(1, 2000000) g;  -- số lượng enrollment muốn sinh (vd: 50k)

