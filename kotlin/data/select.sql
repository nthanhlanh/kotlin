explain analyze
select
    students.name as studentName,
    classrooms.name as className,
    teachers.name as teacherName,
    courses.name as courseName,
    enrollments.semester as semester
from students
join classrooms on students.class_id = classrooms.id
join teachers on classrooms.teacher_id = teachers.id
join enrollments on students.id = enrollments.student_id
join courses on enrollments.course_id = courses.id
where classrooms."name" like '%Class%'
order by students.name asc
limit 200




-- Bật extension pg_trgm (chỉ cần làm 1 lần cho DB)
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Tạo index cho cột name trong bảng classrooms
CREATE INDEX idx_classrooms_name_trgm
    ON classrooms
    USING gin (name gin_trgm_ops);
ANALYZE VERBOSE




create statistics s1 (dependencies) on name,teacher_id from classrooms;
ANALYZE verbose

DROP STATISTICS s1;
ANALYZE verbose