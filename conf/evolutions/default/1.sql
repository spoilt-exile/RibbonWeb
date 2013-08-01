# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table message_probe (
  id                        varchar(255) not null,
  header                    varchar(450) not null,
  date                      timestamp not null,
  pseudo_dir                varchar(255) not null,
  tags                      varchar(255),
  content                   varchar(4000000) not null,
  author                    varchar(255) not null,
  curr_status               integer not null,
  curr_error                varchar(255),
  constraint ck_message_probe_curr_status check (curr_status in (0,1,2,3)),
  constraint pk_message_probe primary key (id))
;

create table session (
  id                        varchar(255) not null,
  username                  varchar(255),
  password                  varchar(255),
  constraint pk_session primary key (id))
;

create sequence message_probe_seq;

create sequence session_seq;




# --- !Downs

drop table if exists message_probe cascade;

drop table if exists session cascade;

drop sequence if exists message_probe_seq;

drop sequence if exists session_seq;
