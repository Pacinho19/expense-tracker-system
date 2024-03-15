drop table if exists expense;
create table expense(
    id int primary key auto_increment,
    name varchar(50) not null,
    category varchar(50) not null ,
    amount NUMERIC(10, 2) not null,
    date date not null,
    created_on timestamp,
    updated_on timestamp
);
