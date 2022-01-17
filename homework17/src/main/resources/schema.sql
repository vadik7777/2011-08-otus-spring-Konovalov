drop table if exists comments;
drop table if exists books;
drop table if exists authors;
drop table if exists genres;
drop table if exists roles;
drop table if exists users;
create table authors
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);
create table genres
(
    id   bigserial,
    name varchar(255),
    primary key (id)
);
create table books
(
    id        bigserial,
    name      varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id  bigint references genres (id) on delete cascade,
    primary key (id)
);
create table comments
(
    id      bigserial,
    comment varchar(255),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);
create table users
(
    id        bigserial,
    user_name varchar(255),
    password  varchar(60),
    primary key (id)
);
create table roles
(
    id      bigserial,
    name    varchar(255),
    user_id bigint references users (id) on delete cascade,
    primary key (id)
);