create table question
(
    id int auto_increment,
    creator int,
    title varchar(50),
    description text,
    tag varchar(256),
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    constraint QUESTION_PK
        primary key (id)
);