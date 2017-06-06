create table directors (
	id serial CONSTRAINT directors_primary_key PRIMARY KEY,
	surname varchar(40),
	name varchar(40),
	birth_date date
);

create table films (
	id serial CONSTRAINT films_primary_key PRIMARY KEY,
	director_id integer REFERENCES directors (id),
	name varchar(40),
	release_date date
);

ALTER TABLE "public"."films"
 ADD CONSTRAINT "films_directors_fkey"
 FOREIGN KEY ("director_id")
 REFERENCES "public"."directors" ("id")
 ON UPDATE RESTRICT ON DELETE CASCADE;

CREATE INDEX "films_directors_fkey_idx" ON "public"."films" ("director_id");

ALTER TABLE "public"."directors"
 ADD CONSTRAINT "directors_name_surname_constr" UNIQUE ("name", "surname");