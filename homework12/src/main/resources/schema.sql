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