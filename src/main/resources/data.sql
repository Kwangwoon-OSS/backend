insert into semesters (semester_id, years, semester) values(default, '2019', '2');
insert into departments (department_id, name) values(default, '소프트웨어');
insert into professors (professor_id, department_id, name) values(default, 1, '문승현');
insert into subjects(subject_id, department_id, semester_id, professor_id, name, code) values(1, 1, 1, 1, '오픈소스소프트웨어실습', '100011');

