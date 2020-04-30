drop database if exists beer_inventory_service;

drop user if exists `beer_inventory_service`@`%`;

create database if not exists beer_inventory_service character set utf8mb4 collate utf8mb4_unicode_ci;

create user if not exists `beer_inventory_service`@`%` identified with mysql_native_password by 'Zaq12wsx';

grant select, insert, update, delete, create, drop, references, index, alter, execute, create view, show view,
create routine, alter routine, event, trigger on `beer_inventory_service`.* to `beer_inventory_service`@`%`;

flush privileges;