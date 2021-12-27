create table matryoshka
(
    id            bigserial,
    step          integer,
    max_steps     integer,
    matryoshka_id bigint references matryoshka (id) on delete cascade,
    primary key (id)
);