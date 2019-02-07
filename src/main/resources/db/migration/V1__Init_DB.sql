create sequence hibernate_sequence start 1 increment 1;

create table nomenclatures (
  id int8 not null,
  description varchar(2048),
  name varchar(255),
  primary key (id));

create table sale_item (
  id int8 not null,
  amount int8 not null,
  nomenclature_id int8 not null,
  sale_id int8 not null,
  primary key (id));

create table sales (
  id int8 not null,
  user_id int8 not null,
  primary key (id));

create table user_role (
  user_id int8 not null,
  roles varchar(255));

create table usr (
  id int8 not null,
  active boolean not null,
  email varchar(255),
  fullname varchar(255),
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id));

alter table if exists sale_item
  add constraint nomenclature_id_fk
  foreign key (nomenclature_id) references nomenclatures;

alter table if exists sale_item
  add constraint sale_id_fk
  foreign key (sale_id) references sales;

alter table if exists sales
  add constraint user_id_fk
  foreign key (user_id) references usr;

alter table if exists user_role
  add constraint user_id_fk
  foreign key (user_id) references usr;