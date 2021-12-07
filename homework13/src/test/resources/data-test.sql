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
       ('comment4', 3),
       ('comment5', 3);
insert into users(user_name, password)
    -- admin/admin
-- user/user
values ('admin', '$2a$12$DccKMGc3RRKY12mW1DVFe.a5qtitu2ex9EmUGxWkVD44T3SsUdnFS'),
       ('user', '$2a$12$s/nQMZr/j4QxvfRb221Mxeoe9QYALYH.IeXNsgnFv4JIH3.0khqkG');
insert into roles(name, user_id)
values ('ROLE_ADMIN', 1),
       ('ROLE_USER', 2);

--asl
INSERT INTO acl_sid (id, principal, sid)
VALUES (1, 0, 'ROLE_ADMIN'),
       (2, 0, 'ROLE_USER');

INSERT INTO acl_class (id, class)
VALUES (1, 'ru.otus.homework13.dto.BookDto');

INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting)
VALUES (1, 1, 1, NULL, 1, 0),
       (2, 1, 2, NULL, 1, 0),
       (3, 1, 3, NULL, 2, 0);

INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure)
VALUES (1, 1, 1, 1, 16, 1, 0, 0),
       (2, 2, 1, 1, 16, 1, 0, 0),
       (3, 3, 1, 2, 16, 1, 0, 0);