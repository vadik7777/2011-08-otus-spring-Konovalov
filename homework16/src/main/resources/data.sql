insert into authors(name)
values ('fio1'),
       ('fio2'),
       ('fio3');
insert into genres(name)
values ('genre1'),
       ('genre2'),
       ('genre3');
insert into books(name, author_id, genre_id)
values ('book1', 1, 1),
       ('book2', 2, 2),
       ('book3', 3, 3);
insert into comments(comment, book_id)
values ('comment1', 1),
       ('comment2', 1),
       ('comment3', 2),
       ('comment4', 3);
insert into users(user_name, password)
-- admin/admin
-- user/user
values ('admin', '$2a$12$DccKMGc3RRKY12mW1DVFe.a5qtitu2ex9EmUGxWkVD44T3SsUdnFS'),
       ('user', '$2a$12$s/nQMZr/j4QxvfRb221Mxeoe9QYALYH.IeXNsgnFv4JIH3.0khqkG');
insert into roles(name, user_id)
values ('ROLE_ADMIN', 1),
       ('ROLE_USER', 2);