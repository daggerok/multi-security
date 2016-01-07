drop table if exists users;
create table users (
  id int primary key,
  username varchar(255) not null,
  password varchar(255) not null
);
insert into users values (1, 'max', 'max');
insert into users values (2, 'dag', 'dag');
insert into users values (3, 'bax', 'bax');