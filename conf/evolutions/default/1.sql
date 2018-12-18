# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                            integer not null,
  username                      varchar(255),
  distance_traveled             double,
  constraint pk_user primary key (id)
);

create table user_location (
  id                            integer not null,
  user_id                       integer,
  timestamp                     timestamp,
  latitude                      double,
  longitude                     double,
  speed                         float,
  constraint pk_user_location primary key (id),
  foreign key (user_id) references user (id) on delete restrict on update restrict
);


# --- !Downs

drop table if exists user;

drop table if exists user_location;

