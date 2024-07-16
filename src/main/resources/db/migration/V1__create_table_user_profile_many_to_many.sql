create table users (
    id bigint not null auto_increment primary key,
    name varchar(100) not null,
    email varchar(100) not null,
    password varchar(300) not null
);

create table profiles (
    id bigint not null auto_increment primary key,
    name varchar(100) not null
);

create table user_profile (
    user_id bigint not null,
    profile_id bigint not null,

    constraint fk_user_id foreign key (user_id) references users (id),
    constraint fk_profile_id foreign key (profile_id) references profiles (id)
);