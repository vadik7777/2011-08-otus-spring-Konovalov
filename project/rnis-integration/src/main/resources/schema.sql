drop table if exists  navigation_information;
drop table if exists  transport_unit;
drop table if exists  organization;
drop table if exists  transport_type;
drop table if exists  municipality;
create table municipality
(
    id   bigint,
    name varchar,
    primary key (id)
);
create table transport_type
(
    id   bigint,
    name varchar,
    primary key (id)
);
create table organization
(
    id   bigint,
    name varchar,
    primary key (id)
);
create table transport_unit
(
    id                   bigint,
    object_name          varchar,
    phone_of_responsible varchar,
    information_date     timestamp without time zone,
    longitude            double,
    latitude             double,
    speed                varchar,
    direction            double,
    municipality_id      bigint references municipality (id) on delete cascade,
    transport_type_id    bigint references transport_type (id) on delete cascade,
    organization_id      bigint references organization (id) on delete cascade,
    primary key (id)
);
create table navigation_information
(
    id                bigserial,
    information_date  timestamp without time zone,
    longitude         double,
    latitude          double,
    speed             varchar,
    direction         double,
    transport_unit_id bigint references transport_unit (id) on delete cascade,
    primary key (id)
);