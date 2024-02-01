create table users(
    id bigserial primary key,
    email varchar(50) not null,
    pwd varchar(500) not null,
    name varchar(50) not null,
    last_name varchar(50) not null,
    newsletters bool,
    create_date date not null
);

create table subscriptions(
    id bigserial primary key,
    type varchar(15) not null,
    price money not null,
    status bool not null
);

create table payments(
    id bigserial primary key,
    id_user bigint,
    id_subscription bigint,
    create_date date not null,
    expiration_date date not null,
    constraint fk_user foreign key(id_user) references users(id),
    constraint fk_subscription foreign key(id_subscription) references subscriptions(id)
);

create table roles(
    id  bigserial primary key,
    role_name varchar(50),
    description varchar(100),
    id_user bigint,
    constraint fk_user foreign key(id_user) references users(id)
);

create table clients (
     id bigserial primary key,
     client_id varchar(256),
     client_name varchar(256),
     client_secret varchar(256),
     scopes varchar(256),
     grant_types varchar(256),
     authentication_methods varchar(256),
     redirect_uri varchar(256),
     redirect_uri_logout varchar(256)
);