insert into users
values (nextval('users_user_id_seq'), 'admin@admin.com', now(), '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW', true);

insert into users
values (nextval('users_user_id_seq'), 'user@user.com', now(), '$2a$12$wyR9dXt1YCK7UjZiqctcM.grkrv0O68jlh/29eRjYLm8cyqD2dXsS', true);

insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');

insert into users_authorities
values (1, 2);

insert into users_authorities
values (1, 1);

insert into users_authorities
values (2, 1);

insert into customers
values(nextval('customers_customer_id_seq'), 'jessi@user.com', '240 Wood Duck Drive', '906-324-1092');

insert into customers
values(nextval('customers_customer_id_seq'), 'unicorn@user.com', '3816 Hedge Street', '201-580-8829');

insert into items
values(nextval('items_item_id_seq'), 'Banana');

insert into items
values(nextval('items_item_id_seq'), 'Cucumber');

insert into items
values(nextval('items_item_id_seq'), 'Milk');

insert into orders_items
values(1, 1);

insert into orders_items
values(1, 3);

insert into orders
values(nextval('orders_order_id_seq'), now(), '2022-05-08', 1);

insert into orders
values(nextval('orders_order_id_seq'), now(), '2022-05-30', 2);

insert into orders
values(nextval('orders_order_id_seq'), now(), '2022-06-22', 1
);


