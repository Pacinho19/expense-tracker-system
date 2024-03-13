drop table if exists expense_tracker_user;
create table expense_tracker_user(
    id int primary key auto_increment,
    name varchar(50) not null,
    login varchar(50) not null,
    role varchar(50) not null,
    password varchar(255) not null,
    created_on timestamp,
    updated_on timestamp
);
