alter table student add constraint age_check check (age >= 16);

alter table student add constraint unique_name unique (name);

alter table faculty add constraint unique_name_color unique (name, color);

alter table student alter column name set not null;

alter table student alter column age set default 20;