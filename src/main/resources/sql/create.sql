DROP TABLE IF EXISTS education CASCADE;
CREATE TABLE education (
    id         serial       PRIMARY KEY,
    educ_name  varchar(64)  NOT NULL,
    CHECK (educ_name in ('без образования', 'начальное',
                        'основное общее (9 кл)', 'среднее общее (11 кл)',
                        'среднее профессиональное (колледж)', 'Высшее (бакалавриат)',
                        'Высшее (специалитет)', 'Высшее (магистратура)')
       )
    /*
     * [0] без образования -> [1] начальное -> [2] основное общее (9 кл) -> [3] среднее общее (11 кл) ->
     * [4] среднее профессиональное (колледж) -> [5] Высшее (бакалавриат) ->
     * [6] Высшее (специалитет) -> [7] Высшее (магистратура)
     */
);

DROP TABLE IF EXISTS person CASCADE;
CREATE TABLE person (
    id             serial        PRIMARY KEY,
    first_name     varchar(256)  NOT NULL,
    last_name      varchar(256)  NOT NULL,
    sur_name       varchar(256)  NOT NULL,
    education_id   int           REFERENCES education
                                     ON DELETE SET NULL
        NOT NULL,
    home_address   text          NOT NULL,
    search_status  varchar(32)   NOT NULL,
    CHECK (search_status in ('ищет работу', 'открыт к предложениям', 'не ищет'))
    /*  'ищет работу', 'открыт к предложениям', 'не ищет' */
);

DROP TABLE IF EXISTS resume CASCADE;
CREATE TABLE resume (
    id              serial        PRIMARY KEY,
    person_id       int           REFERENCES person
        ON DELETE CASCADE
                                  NOT NULL,
    position        text          NOT NULL,
    desired_salary  int           NOT NULL,
    experience      float         NOT NULL,
    CHECK (desired_salary >= 0),
    CHECK (experience >= 0)
);

DROP TABLE IF EXISTS places_of_work CASCADE;
CREATE TABLE places_of_work (
    id          serial  PRIMARY KEY,
    person_id   int     REFERENCES person
        ON DELETE CASCADE
                        NOT NULL,
    company     text,
    position    text    NOT NULL,
    salary      int     NOT NULL,
    start_date  date    NOT NULL,
    finish_day  date    NOT NULL,
    CHECK (start_date < finish_day),
    CHECK (salary >= 0)
);

DROP TABLE IF EXISTS company CASCADE;
CREATE TABLE company (
    id         serial        PRIMARY KEY,
    name       varchar(256)  NOT NULL,
    full_name  varchar(256)  NOT NULL
);

DROP TABLE IF EXISTS vacancies CASCADE;
CREATE TABLE vacancies (
   id                  serial        PRIMARY KEY,
   company_id          int           REFERENCES company
       ON DELETE CASCADE
                                     NOT NULL,
   position            text          NOT NULL,
   offered_salary      int           NOT NULL,
   experience          numeric(5,1)  NOT NULL,
   required_education  int           REFERENCES education
                                         ON DELETE SET NULL
       NOT NULL,
   CHECK (experience >= 0),
   CHECK (offered_salary >= 0)
);