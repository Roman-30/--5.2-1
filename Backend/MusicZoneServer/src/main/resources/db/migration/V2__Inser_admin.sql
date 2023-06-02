insert into person (id, email, name, nickname, password, phone, surname)
values (1, 'admin@ad.min', 'Admin', 'Admin', '$2y$08$7csy3l89yvmby6leblhoJ.WzGbSyqFIxXllfD4MqoAIZjWuqkDsai',
        '8951801261', '');

insert into person (id, email, name, nickname, password, phone, surname)
values (2, 'gmv.vrn@bk.ru', 'Максим', 'Client_dev', '$2y$08$ZTXpvaGDq6tyw0QaH.4MuOJ0z5rcz.twMBtZHSvJEkPKQAudWxcoi',
        '8951801261', 'Гончаренко');

insert into user_role (user_id, roles)
values (1, 'ADMIN');

insert into user_role (user_id, roles)
values (2, 'USER');