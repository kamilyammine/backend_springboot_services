
create table if not exists x_access_key
(
    id                                  int not null generated by default as identity,
    create_datetime                     timestamp with time zone default now(),
    key                                 text not null,
    constraint pk_x_access_key primary key(id)
);