insert into users (email, pwd, name, last_name, newsletters, create_date) VALUES
  ('admin@jairogalvez.com', '$2a$10$WGpBHskzwC7U6Hiib7KgGeVkGBd.B4LCskRa7U9EPr/eIQ7RxpvhG', 'Jairo', 'Galvez', '0', '2024-01-28'),
  ('admin2@jairogalvez.com', '$2a$10$WGpBHskzwC7U6Hiib7KgGeVkGBd.B4LCskRa7U9EPr/eIQ7RxpvhG', 'Jairo', 'Galvez', '0', '2024-01-28'),
  ('user@jairogalvez.com', '$2a$10$WGpBHskzwC7U6Hiib7KgGeVkGBd.B4LCskRa7U9EPr/eIQ7RxpvhG', 'Jairo', 'Galvez', '0', '2024-01-28');

insert into subscriptions (type, price, status) VALUES
  ('ANUAL', '$9.99', '1'),
  ('MENSUAL', '$90.00', '1');

insert into roles (role_name, description, id_user) VALUES
  ('ROLE_ADMIN', 'rol admin', 1),
  ('ROLE_ADMIN', 'rol admin', 2),
  ('ROLE_USER', 'rol user', 3);

insert into payments (id_user, id_subscription, create_date, expiration_date) VALUES
    (1, 1, '2023-11-25', '2024-11-25');

insert into clients(
    client_id,
    client_name,
    client_secret,
    scopes,
    grant_types,
    authentication_methods,
    redirect_uri,
    redirect_uri_logout
)
values ('company',
            'debuggeando company',
            '$2a$10$pxElUc8ZfiJZDUDI7EQNeOcV2Rw26kTBd0SdsUYL.gjm1XID4D98S',
            'read,write',
            'authorization_code,refresh_token',
            'client_secret_basic,client_secret_jwt',
            'http://localhost:4200/',
            'https://springone.io/authorized')

