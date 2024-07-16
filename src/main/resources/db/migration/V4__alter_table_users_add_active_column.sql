alter table users add column active tinyint not null;
alter table users alter active set default 0;