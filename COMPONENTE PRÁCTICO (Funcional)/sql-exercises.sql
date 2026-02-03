PRAGMA foreign_keys = ON;

-- Limpieza por si lo ejecutas varias veces
DROP TABLE IF EXISTS career_student;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS careers;

-- 1) Tablas
CREATE TABLE students (
  student_id INTEGER PRIMARY KEY,
  name       TEXT NOT NULL,
  birthdate  TEXT NOT NULL,  -- YYYY-MM-DD
  gender     TEXT NOT NULL CHECK (gender IN ('M','F')),
  email      TEXT NOT NULL,
  phone      TEXT NOT NULL
);

CREATE TABLE careers (
  career_id    INTEGER PRIMARY KEY,
  name         TEXT NOT NULL,
  description  TEXT NOT NULL,
  subjects     INTEGER NOT NULL,
  cost         REAL NOT NULL
);

CREATE TABLE career_student (
  career_id           INTEGER NOT NULL,
  student_id          INTEGER NOT NULL,
  start_date          TEXT NOT NULL,
  estimated_end_date  TEXT NOT NULL,
  end_date            TEXT,      -- puede ser NULL
  extended            TEXT,      -- en el enunciado está NULL
  calification        INTEGER NOT NULL,
  PRIMARY KEY (career_id, student_id, start_date),
  FOREIGN KEY (career_id) REFERENCES careers(career_id),
  FOREIGN KEY (student_id) REFERENCES students(student_id)
);

-- 2) Inserts: STUDENTS
INSERT INTO students (student_id, name, birthdate, gender, email, phone) VALUES
(9000185, 'PEDRO GOMEZ',       '1985-10-30', 'M', 'PEDRO.GOMEZ@GMAIL.COM',       '1185432478'),
(9000186, 'JUAN HERNANDEZ',    '1995-01-25', 'M', 'Juan.Hernandez@gmail.com',    '1185432479'),
(9000187, 'MARIA GONZALEZ',    '1998-05-05', 'F', 'MARIA.GONZALEZ@HOTMAIL.COM',  '1185432480'),
(9000188, 'PABLO SANCHEZ',     '2001-08-09', 'M', 'pablo.sanchez@gmail.com',     '1185432481'),
(9000189, 'ELENA CASTRO',      '1972-02-18', 'F', 'elena.casto@hotmail.com',     '1185432482'),
(9000190, 'MATIAS PEREZ',      '1993-11-01', 'M', 'MATIAS.PEREZ@gmail.COM',      '1185432483'),
(9000191, 'SEBASTIAN VARGAS',  '2000-09-06', 'M', 'SEBASTIAN.VARGAS@OUTLOOK.COM','1185432484'),
(9000192, 'PEDRO HERNANDEZ',   '1998-04-22', 'M', 'PEDRO.HERNANDEZ@GMAIL.COM',   '1185432485'),
(9000193, 'CAROLINA LUNA',     '1997-07-21', 'F', 'CAROLINA.LUNA@YAHOO.COM',     '1185432486'),
(9000194, 'MONICA FIGUEROA',   '1987-05-06', 'F', 'MONICA.FIGUEROA@HOTMAIL.COM', '1185432487');

-- 3) Inserts: CAREERS
INSERT INTO careers (career_id, name, description, subjects, cost) VALUES
(10025, 'INGENIERIA',      'INGENIERIA',      90, 25000.00),
(10026, 'ADMINISTRACION',  'ADMINISTRACION',  72, 18000.00),
(10027, 'CONTABILIDAD',    'CONTABILIDAD',    60, 15000.00),
(10028, 'DERECHO',         'DERECHO',         85, 23000.00),
(10029, 'INFORMATICA',     'INFORMATICA',     78, 19500.00),
(10030, 'COMUNICACION',    'COMUNICACION',    65, 17800.00);

-- 4) Inserts: CAREER_STUDENT
INSERT INTO career_student
(career_id, student_id, start_date, estimated_end_date, end_date, extended, calification) VALUES
(10025, 9000185, '2009-01-01', '2010-01-01', '2010-01-01', NULL, 85),
(10030, 9000186, '2008-01-01', '2013-01-01', '2013-07-01', NULL, 90),
(10027, 9000190, '2014-01-01', '2019-01-01', NULL,        NULL, 52),
(10030, 9000187, '2010-01-01', '2015-01-01', '2015-02-28', NULL, 63),
(10028, 9000191, '2009-01-01', '2014-01-01', '2014-01-01', NULL, 87),
(10025, 9000189, '2008-01-01', '2013-01-01', '2018-01-01', NULL, 96),
(10026, 9000191, '2015-01-01', '2020-01-01', NULL,        NULL, 85),
(10029, 9000190, '2012-01-01', '2017-01-01', '2017-07-05', NULL, 99),
(10026, 9000191, '2016-01-01', '2021-01-01', NULL,        NULL, 74),
(10026, 9000192, '2015-01-01', '2020-01-01', NULL,        NULL, 72),
(10030, 9000186, '2011-01-01', '2016-01-01', '2016-01-01', NULL, 91),
(10027, 9000193, '2012-01-01', '2017-01-01', NULL,        NULL, 32),
(10029, 9000185, '2008-01-01', '2013-01-01', '2013-01-01', NULL, 68);



-- Consultas Solicitadas: 

--a. Seleccione el total de estudiantes Masculinos Registrados
SELECT COUNT(*) AS total_masculinos
FROM students
WHERE gender = 'M';

--b. Elimine los estudiantes nacidos antes 2000 (por probar)

--1 verificar estudiantes nacidos antes del 2000:
SELECT student_id, name, birthdate
FROM students
WHERE birthdate < '2000-01-01'
ORDER BY birthdate;
--2 eliminar estudiantes nacidos antes del 200 
--(“Primero elimino en career_student porque tiene FK hacia students; 
--si borro el estudiante primero, rompería integridad referencial. Luego
--valido con una query de conteo para confirmar que no quedan nacidos antes del 2000.”)
DELETE FROM career_student
WHERE student_id IN (
  SELECT student_id
  FROM students
  WHERE birthdate < '2000-01-01'
);
SELECT changes() AS carreras_borradas;

DELETE FROM students
WHERE birthdate < '2000-01-01';
SELECT changes() AS estudiantes_borrados;


--c. Actualice la descripción de la carrera con id '10026’ de “Administración” a “Administración de Empresas” 
UPDATE careers
SET description = 'Administración de Empresas'
WHERE career_id = 10026;

--d. Muestre los nombres de los estudiantes que estén cursando dos o más materias ( no existe en la bd materias por estudiantes, existe es estudiantes inscritos en carreras con varios registros)
SELECT s.name
FROM students s
JOIN career_student cs ON cs.student_id = s.student_id
GROUP BY s.student_id, s.name
HAVING COUNT(*) >= 2;

--e. Calificación promedio de todos los estudiantes que estén cursando carreras ( agregar calificacion por estudiante)
SELECT AVG(cs.calification) AS promedio_calificacion
FROM career_student cs;
  --promedio por estudiante:
SELECT s.student_id,
       s.name,
       AVG(cs.calification) AS promedio_calificacion
FROM students s
JOIN career_student cs ON cs.student_id = s.student_id
GROUP BY s.student_id, s.name
ORDER BY promedio_calificacion DESC;


--f. Muestra el nombre, carrera id, correos de los estudiantes que tienen una calificación mayor 70
SELECT s.name,
       cs.career_id,
       s.email,
       cs.calification
FROM students s
JOIN career_student cs ON cs.student_id = s.student_id
WHERE cs.calification > 70;

--g. Cuántos estudiantes que estén cursando 2 o más carreras
SELECT COUNT(*) AS estudiantes_con_2_o_mas_carreras
FROM (
  SELECT cs.student_id
  FROM career_student cs
  GROUP BY cs.student_id
  HAVING COUNT(*) >= 2
) t;


