create table users
(
    user_id bigserial primary key,
    user_email varchar(255) not null,
    user_created timestamp not null,
    user_password varchar(100) not null,
    user_enabled boolean not null
);

create table authorities
(
    authority_id bigserial primary key,
    authority_name varchar(50)
);

create table users_authorities
(
    user_id bigint,
    authority_id bigint
);

create table customers
(
    customer_id bigserial primary key,
    customer_email varchar(50),
    customer_address varchar (50),
    customer_phone varchar(50)
);

create table orders
(
    order_id bigserial primary key,
    order_date timestamp,
    order_delivery varchar(50),
    order_items varchar(255),
    customer_id bigint references customers(customer_id)
);

create table items
(
    item_id bigserial primary key,
    item_name varchar(50)
);

create table orders_items
(
    order_id bigint,
    item_id bigint
);
