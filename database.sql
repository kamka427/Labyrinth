create database if not exists labyrinth;
use labyrinth;
create table if not exists scores
(
    name  varchar(255) not null
    primary key,
    score int          null
);

