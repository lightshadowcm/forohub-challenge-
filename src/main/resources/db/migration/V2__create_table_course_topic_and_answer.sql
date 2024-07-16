create table courses (
    id bigint not null auto_increment primary key,
    name varchar(100) not null,
    category varchar(100) not null
);

create table topics (
    id bigint not null auto_increment primary key,
    title varchar(100) not null unique,
    message varchar(250) not null unique,
    creation_date datetime not null,
    status tinyint not null default 0,
    answers bigint not null default 0,
    user_id bigint not null,
    course_id bigint not null,

    constraint fk_topics_courses_id foreign key(course_id) references courses(id),
    constraint fk_topics_users_id foreign key(user_id) references users(id)
);

create table answers (
    id bigint not null auto_increment primary key,
    message varchar(250) not null,
    creation_date datetime not null,
    solution tinyint not null default 0,
    topic_id bigint not null,
    user_id bigint not null,

    constraint fk_answers_topics_id foreign key(topic_id) references topics(id),
    constraint fk_answers_users_id foreign key(user_id) references users(id)
);