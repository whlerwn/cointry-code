create table code
(
    id         uuid not null,
    number     varchar(255),
    country_id uuid,
    primary key (id)
);

create table country
(
    id   uuid not null,
    name varchar(255),
    primary key (id)
);

alter table if exists code
    add constraint FK_code_account
        foreign key (country_id) references country