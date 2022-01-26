# --- !Ups
create table people (
    id int auto_increment primary key
    , name varchar(255) not null
    , mail varchar(255) not null
    , tel varchar(255) 
);

insert into people values (default, 'taro', 'taro@example.com', '111-111');
insert into people values (default, 'hana', 'hana@example.com', '222-222');
insert into people values (default, 'saki', 'saki@example.com', '333-333');

# --- !Downs
drop table people

