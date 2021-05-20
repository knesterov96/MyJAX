--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: work; Type: SCHEMA; Schema: -; Owner: NKA
--

CREATE SCHEMA work;


ALTER SCHEMA work OWNER TO "NKA";

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: organization; Type: TABLE; Schema: public; Owner: NKA
--

CREATE TABLE public.organization (
    id integer NOT NULL,
    legal_adress character varying(255),
    manager character varying(255),
    name character varying(255),
    physical_adress character varying(255)
);


ALTER TABLE public.organization OWNER TO "NKA";

--
-- Name: organization_id_seq; Type: SEQUENCE; Schema: public; Owner: NKA
--

CREATE SEQUENCE public.organization_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organization_id_seq OWNER TO "NKA";

--
-- Name: organization_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: NKA
--

ALTER SEQUENCE public.organization_id_seq OWNED BY public.organization.id;


--
-- Name: assignment; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.assignment (
    id integer NOT NULL,
    subject character varying(100) NOT NULL,
    text character varying(1000) NOT NULL,
    end_date date NOT NULL,
    head_organization integer NOT NULL
);


ALTER TABLE work.assignment OWNER TO "NKA";

--
-- Name: TABLE assignment; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.assignment IS 'Таблица содержит основную информацию о поручении';


--
-- Name: COLUMN assignment.subject; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment.subject IS 'Предмет поручения';


--
-- Name: COLUMN assignment.text; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment.text IS 'Текст поручения';


--
-- Name: COLUMN assignment.end_date; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment.end_date IS 'Дата окончания поручения';


--
-- Name: COLUMN assignment.head_organization; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment.head_organization IS 'Автор поручения';


--
-- Name: assignment_employee; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.assignment_employee (
    id integer NOT NULL,
    head_subdivision integer NOT NULL,
    employee integer NOT NULL
);


ALTER TABLE work.assignment_employee OWNER TO "NKA";

--
-- Name: TABLE assignment_employee; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.assignment_employee IS 'Поручение для сотрудника';


--
-- Name: COLUMN assignment_employee.head_subdivision; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment_employee.head_subdivision IS 'Ссылка на начальика отдела, который поручил';


--
-- Name: COLUMN assignment_employee.employee; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment_employee.employee IS 'Ссылка на сотрудника, которому поручено';


--
-- Name: assignment_employee_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.assignment_employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.assignment_employee_id_seq OWNER TO "NKA";

--
-- Name: assignment_employee_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.assignment_employee_id_seq OWNED BY work.assignment_employee.id;


--
-- Name: assignment_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.assignment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.assignment_id_seq OWNER TO "NKA";

--
-- Name: assignment_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.assignment_id_seq OWNED BY work.assignment.id;


--
-- Name: assignment_subdivision; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.assignment_subdivision (
    id integer NOT NULL,
    assignment integer NOT NULL,
    control boolean DEFAULT false NOT NULL,
    execution boolean DEFAULT false NOT NULL,
    head_subdivision integer NOT NULL
);


ALTER TABLE work.assignment_subdivision OWNER TO "NKA";

--
-- Name: TABLE assignment_subdivision; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.assignment_subdivision IS 'Поручения для начальников отделов';


--
-- Name: COLUMN assignment_subdivision.assignment; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment_subdivision.assignment IS 'Ссылка на поручение';


--
-- Name: COLUMN assignment_subdivision.control; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment_subdivision.control IS 'Признак контрольности';


--
-- Name: COLUMN assignment_subdivision.execution; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment_subdivision.execution IS 'Признак исполнения порчения';


--
-- Name: COLUMN assignment_subdivision.head_subdivision; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.assignment_subdivision.head_subdivision IS 'Ссылка на начальника отдела';


--
-- Name: assignment_subdivision_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.assignment_subdivision_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.assignment_subdivision_id_seq OWNER TO "NKA";

--
-- Name: assignment_subdivision_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.assignment_subdivision_id_seq OWNED BY work.assignment_subdivision.id;


--
-- Name: employee; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.employee (
    id integer NOT NULL,
    surname character varying(50) NOT NULL,
    name character varying(50) NOT NULL,
    patronymic character varying(50),
    subdivision integer NOT NULL,
    "position" integer NOT NULL
);


ALTER TABLE work.employee OWNER TO "NKA";

--
-- Name: TABLE employee; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.employee IS 'Сотрудник';


--
-- Name: COLUMN employee.surname; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.employee.surname IS 'Фамилия сотрудника';


--
-- Name: COLUMN employee.name; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.employee.name IS 'Имя сотрудника';


--
-- Name: COLUMN employee.patronymic; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.employee.patronymic IS 'Отчество сотрудника';


--
-- Name: COLUMN employee.subdivision; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.employee.subdivision IS 'Внешний ключ на таблицу подразделений';


--
-- Name: COLUMN employee."position"; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.employee."position" IS 'Должность, внешний ключ на справочник долностей';


--
-- Name: employee_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.employee_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.employee_id_seq OWNER TO "NKA";

--
-- Name: employee_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.employee_id_seq OWNED BY work.employee.id;


--
-- Name: head_organization; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.head_organization (
    id integer NOT NULL,
    surname character varying(50) NOT NULL,
    name character varying(50) NOT NULL,
    patronymic character varying(50),
    organization integer NOT NULL
);


ALTER TABLE work.head_organization OWNER TO "NKA";

--
-- Name: TABLE head_organization; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.head_organization IS 'Руководитель организации';


--
-- Name: COLUMN head_organization.surname; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_organization.surname IS 'Имя руководителя';


--
-- Name: COLUMN head_organization.name; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_organization.name IS 'Имя руководителя';


--
-- Name: COLUMN head_organization.patronymic; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_organization.patronymic IS 'Отчество руководитлея';


--
-- Name: COLUMN head_organization.organization; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_organization.organization IS 'Внешний ключ на таблицу организаций';


--
-- Name: head_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.head_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.head_id_seq OWNER TO "NKA";

--
-- Name: head_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.head_id_seq OWNED BY work.head_organization.id;


--
-- Name: head_subdivision; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.head_subdivision (
    id integer NOT NULL,
    surname character varying(50) NOT NULL,
    name character varying(50) NOT NULL,
    patronymic character varying(50),
    subdivision integer NOT NULL
);


ALTER TABLE work.head_subdivision OWNER TO "NKA";

--
-- Name: COLUMN head_subdivision.surname; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_subdivision.surname IS 'Фамлия начальника отдела';


--
-- Name: COLUMN head_subdivision.name; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_subdivision.name IS 'Имя начальника отдела';


--
-- Name: COLUMN head_subdivision.patronymic; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_subdivision.patronymic IS 'Отчество начальника отдела';


--
-- Name: COLUMN head_subdivision.subdivision; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.head_subdivision.subdivision IS 'Внешний ключ на таблицу подразделений';


--
-- Name: head_subdivision_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.head_subdivision_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.head_subdivision_id_seq OWNER TO "NKA";

--
-- Name: head_subdivision_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.head_subdivision_id_seq OWNED BY work.head_subdivision.id;


--
-- Name: organization; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.organization (
    id integer NOT NULL,
    name character varying(200) NOT NULL,
    physical character varying(200) NOT NULL,
    legal character varying(200) NOT NULL,
    headorganization_id integer
);


ALTER TABLE work.organization OWNER TO "NKA";

--
-- Name: TABLE organization; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.organization IS 'Организация';


--
-- Name: COLUMN organization.name; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.organization.name IS 'Наименование организации';


--
-- Name: COLUMN organization.physical; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.organization.physical IS 'Физичский адрес';


--
-- Name: COLUMN organization.legal; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.organization.legal IS 'Юридический адрес';


--
-- Name: organization_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.organization_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.organization_id_seq OWNER TO "NKA";

--
-- Name: organization_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.organization_id_seq OWNED BY work.organization.id;


--
-- Name: position; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work."position" (
    id integer NOT NULL,
    text character varying(50) NOT NULL
);


ALTER TABLE work."position" OWNER TO "NKA";

--
-- Name: TABLE "position"; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work."position" IS 'Справочник должностей';


--
-- Name: positions_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.positions_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.positions_id_seq OWNER TO "NKA";

--
-- Name: positions_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.positions_id_seq OWNED BY work."position".id;


--
-- Name: subdivision; Type: TABLE; Schema: work; Owner: NKA
--

CREATE TABLE work.subdivision (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    contact character varying(200) NOT NULL,
    organization integer NOT NULL
);


ALTER TABLE work.subdivision OWNER TO "NKA";

--
-- Name: TABLE subdivision; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON TABLE work.subdivision IS 'Подразделение';


--
-- Name: COLUMN subdivision.name; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.subdivision.name IS 'Название подразделения';


--
-- Name: COLUMN subdivision.contact; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.subdivision.contact IS 'Контактная информация';


--
-- Name: COLUMN subdivision.organization; Type: COMMENT; Schema: work; Owner: NKA
--

COMMENT ON COLUMN work.subdivision.organization IS 'Внешний ключ на таблицу оранизаций';


--
-- Name: subdivision_id_seq; Type: SEQUENCE; Schema: work; Owner: NKA
--

CREATE SEQUENCE work.subdivision_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE work.subdivision_id_seq OWNER TO "NKA";

--
-- Name: subdivision_id_seq; Type: SEQUENCE OWNED BY; Schema: work; Owner: NKA
--

ALTER SEQUENCE work.subdivision_id_seq OWNED BY work.subdivision.id;


--
-- Name: organization id; Type: DEFAULT; Schema: public; Owner: NKA
--

ALTER TABLE ONLY public.organization ALTER COLUMN id SET DEFAULT nextval('public.organization_id_seq'::regclass);


--
-- Name: assignment id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment ALTER COLUMN id SET DEFAULT nextval('work.assignment_id_seq'::regclass);


--
-- Name: assignment_employee id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_employee ALTER COLUMN id SET DEFAULT nextval('work.assignment_employee_id_seq'::regclass);


--
-- Name: assignment_subdivision id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision ALTER COLUMN id SET DEFAULT nextval('work.assignment_subdivision_id_seq'::regclass);


--
-- Name: employee id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.employee ALTER COLUMN id SET DEFAULT nextval('work.employee_id_seq'::regclass);


--
-- Name: head_organization id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_organization ALTER COLUMN id SET DEFAULT nextval('work.head_id_seq'::regclass);


--
-- Name: head_subdivision id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_subdivision ALTER COLUMN id SET DEFAULT nextval('work.head_subdivision_id_seq'::regclass);


--
-- Name: organization id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.organization ALTER COLUMN id SET DEFAULT nextval('work.organization_id_seq'::regclass);


--
-- Name: position id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work."position" ALTER COLUMN id SET DEFAULT nextval('work.positions_id_seq'::regclass);


--
-- Name: subdivision id; Type: DEFAULT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.subdivision ALTER COLUMN id SET DEFAULT nextval('work.subdivision_id_seq'::regclass);


--
-- Name: organization organization_pkey; Type: CONSTRAINT; Schema: public; Owner: NKA
--

ALTER TABLE ONLY public.organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (id);


--
-- Name: assignment_employee assignment_employee_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_employee
    ADD CONSTRAINT assignment_employee_pk PRIMARY KEY (id);


--
-- Name: assignment assignment_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment
    ADD CONSTRAINT assignment_pk PRIMARY KEY (id);


--
-- Name: assignment_subdivision assignment_subdivision_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision
    ADD CONSTRAINT assignment_subdivision_pk PRIMARY KEY (id);


--
-- Name: employee employee_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.employee
    ADD CONSTRAINT employee_pk PRIMARY KEY (id);


--
-- Name: head_organization head_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_organization
    ADD CONSTRAINT head_pk PRIMARY KEY (id);


--
-- Name: head_subdivision head_subdivision_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_subdivision
    ADD CONSTRAINT head_subdivision_pk PRIMARY KEY (id);


--
-- Name: organization organization_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.organization
    ADD CONSTRAINT organization_pk PRIMARY KEY (id);


--
-- Name: position positions_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work."position"
    ADD CONSTRAINT positions_pk PRIMARY KEY (id);


--
-- Name: subdivision subdivision_pk; Type: CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.subdivision
    ADD CONSTRAINT subdivision_pk PRIMARY KEY (id);


--
-- Name: assignment_employee assignment_employee_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_employee
    ADD CONSTRAINT assignment_employee_fk FOREIGN KEY (head_subdivision) REFERENCES work.assignment_subdivision(id) ON DELETE CASCADE;


--
-- Name: assignment_employee assignment_employee_fk2; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_employee
    ADD CONSTRAINT assignment_employee_fk2 FOREIGN KEY (employee) REFERENCES work.employee(id);


--
-- Name: assignment assignment_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment
    ADD CONSTRAINT assignment_fk FOREIGN KEY (head_organization) REFERENCES work.head_organization(id) ON DELETE SET NULL;


--
-- Name: assignment_subdivision assignment_subdivision_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision
    ADD CONSTRAINT assignment_subdivision_fk FOREIGN KEY (assignment) REFERENCES work.assignment(id) ON DELETE CASCADE;


--
-- Name: assignment_subdivision assignment_subdivision_fk2; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision
    ADD CONSTRAINT assignment_subdivision_fk2 FOREIGN KEY (head_subdivision) REFERENCES work.head_subdivision(id) ON DELETE CASCADE;


--
-- Name: assignment_subdivision assignment_subdivision_fk3; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision
    ADD CONSTRAINT assignment_subdivision_fk3 FOREIGN KEY (assignment) REFERENCES work.assignment(id);


--
-- Name: employee employee_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.employee
    ADD CONSTRAINT employee_fk FOREIGN KEY (subdivision) REFERENCES work.subdivision(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: employee employee_position; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.employee
    ADD CONSTRAINT employee_position FOREIGN KEY ("position") REFERENCES work."position"(id) ON DELETE SET NULL;


--
-- Name: organization fk2dgu7hk8mlanw1eht7c2nr2xa; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.organization
    ADD CONSTRAINT fk2dgu7hk8mlanw1eht7c2nr2xa FOREIGN KEY (headorganization_id) REFERENCES work.head_organization(id);


--
-- Name: head_subdivision fk5pxevjb0o79erq1ak5th8jftm; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_subdivision
    ADD CONSTRAINT fk5pxevjb0o79erq1ak5th8jftm FOREIGN KEY (subdivision) REFERENCES work.subdivision(id);


--
-- Name: assignment_employee fk5vxuqktyw91qvrmt5dp2j1jfu; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_employee
    ADD CONSTRAINT fk5vxuqktyw91qvrmt5dp2j1jfu FOREIGN KEY (head_subdivision) REFERENCES work.assignment_subdivision(id);


--
-- Name: employee fk7r4mt1rwghd3enpivpadinsdt; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.employee
    ADD CONSTRAINT fk7r4mt1rwghd3enpivpadinsdt FOREIGN KEY ("position") REFERENCES work."position"(id);


--
-- Name: head_organization fk7xlwcuoh47e7pujqbb6lbljtg; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_organization
    ADD CONSTRAINT fk7xlwcuoh47e7pujqbb6lbljtg FOREIGN KEY (organization) REFERENCES work.organization(id);


--
-- Name: assignment fkh2sw6iwc0omf98b99cswis4dt; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment
    ADD CONSTRAINT fkh2sw6iwc0omf98b99cswis4dt FOREIGN KEY (head_organization) REFERENCES work.head_organization(id);


--
-- Name: assignment_subdivision fkiwwsxv8na62ymo3isj70t7i66; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision
    ADD CONSTRAINT fkiwwsxv8na62ymo3isj70t7i66 FOREIGN KEY (head_subdivision) REFERENCES work.head_subdivision(id);


--
-- Name: assignment_subdivision fkj5gutq8blhi28x4jir7h0u54n; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_subdivision
    ADD CONSTRAINT fkj5gutq8blhi28x4jir7h0u54n FOREIGN KEY (assignment) REFERENCES work.assignment(id);


--
-- Name: subdivision fkkbky4d55viyd41gkabrm90nof; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.subdivision
    ADD CONSTRAINT fkkbky4d55viyd41gkabrm90nof FOREIGN KEY (organization) REFERENCES work.organization(id);


--
-- Name: assignment_employee fknnf88pg32nke4u39nnuv271hl; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.assignment_employee
    ADD CONSTRAINT fknnf88pg32nke4u39nnuv271hl FOREIGN KEY (employee) REFERENCES work.employee(id);


--
-- Name: employee fknwpjyto3f91dpm13o3vvm2d9e; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.employee
    ADD CONSTRAINT fknwpjyto3f91dpm13o3vvm2d9e FOREIGN KEY (subdivision) REFERENCES work.subdivision(id);


--
-- Name: head_organization head_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_organization
    ADD CONSTRAINT head_fk FOREIGN KEY (organization) REFERENCES work.organization(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: head_subdivision head_subdivision_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.head_subdivision
    ADD CONSTRAINT head_subdivision_fk FOREIGN KEY (subdivision) REFERENCES work.subdivision(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: subdivision subdivision_fk; Type: FK CONSTRAINT; Schema: work; Owner: NKA
--

ALTER TABLE ONLY work.subdivision
    ADD CONSTRAINT subdivision_fk FOREIGN KEY (organization) REFERENCES work.organization(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

