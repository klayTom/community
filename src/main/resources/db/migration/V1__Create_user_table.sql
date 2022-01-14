create table user
(
    id int auto_increment primary key not null,
    account_id varchar(100),
    name varchar(100),
    token varchar(36),
    gmt_create bigint,
    gmt_modified bigint
);
