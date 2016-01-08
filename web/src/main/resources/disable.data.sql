drop table if exists users;
create table users (
  id int primary key,
  username varchar(255) not null,
  password varchar(255) not null
);
-- -- decoded
-- insert into users values (1, 'max', 'max');
-- insert into users values (2, 'dag', 'dag');
-- insert into users values (3, 'bax', 'bax');
-- encoded
insert into users values (1, 'max', '$2a$10$uTDmEmOr8UOa9S.fcikliuluD4q2YzDryqJVrmtBqlAjojOBIL7Me');
insert into users values (2, 'dag', '$2a$10$/zQHj6.2rvvlLSPG996trOOUbOBK.AapUgUOIlKsIj5eixsSZNq8C');
insert into users values (3, 'bax', '$2a$10$iDD/72QNOH8DHSgqxNKaY.KTomJabYzBVpWRvy/2p8gSMrBWlkT7i');