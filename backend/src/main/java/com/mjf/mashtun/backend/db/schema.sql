DROP TABLE IF EXISTS public.app_user;
drop table if exists public.recipe_ingredient;
drop table if exists public.unit;
drop table if exists public.ingredient;
drop table if exists public.recipe;

drop index if exists ix_ingredient_label;
drop index if exists unit_label_uk;

CREATE TABLE IF NOT EXISTS public.recipe (
  id    serial not null primary key,
  name         character varying(100) COLLATE pg_catalog."default" NOT NULL,
  description  TEXT,
  instructions TEXT
);

CREATE TABLE IF NOT EXISTS public.ingredient (
  id serial not null primary key,
  ingredient_label         character varying(100) COLLATE pg_catalog."default" NOT NULL
);
create unique index if not exists ix_ingredient_label ON ingredient (ingredient_label);

CREATE TABLE IF NOT EXISTS public.unit (
  id serial not null primary key,
  unit_label   character varying(100) COLLATE pg_catalog."default" NOT NULL
);
CREATE UNIQUE INDEX if not exists unit_label_uk on unit (unit_label);

CREATE TABLE IF NOT EXISTS public.recipe_ingredient (
  id serial not null primary key,
  recipe_id            integer NOT NULL,
  ingredient_id        integer NOT NULL,
  unit_id              integer NOT NULL,
  amount               DECIMAL(5, 2) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS public.app_user
(
    id serial not null primary key,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    login character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL
);
