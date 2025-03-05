BEGIN TRANSACTION;


INSERT INTO users (username,password_hash,role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('user3','user3','ROLE_USER');



INSERT INTO cake_options (name, category, available, price) VALUES ('Cupcake', 'style', true, 1.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('Cheesecake', 'style', true, 1.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('Sheet', 'style', true, 2.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('Layer', 'style', true, 3.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('style', 'style', true, 1.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('size', 'size', true, 2.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('M', 'size', true, 2.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('flavor', 'flavor', true, 3.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('Raspberry', 'flavor', true, 3.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('frosting', 'frosting', true, 1.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('filling', 'filling', true, 2.99);
INSERT INTO cake_options (name, category, available, price) VALUES ('Fudge', 'filling', true, 2.99);

INSERT INTO cakes (style, size, flavor, frosting, filling) VALUES ('style', 'size', 'flavor', 'frosting', 'filling');
INSERT INTO cakes (style, size, flavor, frosting, filling) VALUES ('style', 'size', 'flavor', 'frosting', 'filling');
INSERT INTO cakes (style, size, flavor, frosting, filling) VALUES ('style', 'size', 'flavor', 'frosting', 'filling');

INSERT INTO cake_options(name, category, price) VALUES ('Sprinkles', 'extra', 1.99);
INSERT INTO cake_options(name, category, price) VALUES ('Strawberries', 'extra', 2.99);
INSERT INTO cake_options(name, category, price) VALUES ('Blueberries', 'extra', 3.99);

INSERT INTO cake_extras (name, cake_id) VALUES ('Sprinkles', 1);
INSERT INTO cake_extras (name, cake_id) VALUES ('Strawberries', 2);
INSERT INTO cake_extras (name, cake_id) VALUES ('Blueberries', 3);

INSERT INTO standard_cakes (name, price, description, available, cake_id) VALUES ('Vanilla Cake', 14.99, 'A vanilla cake', true, 1);
INSERT INTO standard_cakes (name, price, description, available, cake_id) VALUES ('Chocolate Cake', 10.99, 'A Chocolate cake', true, 2);
INSERT INTO standard_cakes (name, price, description, available, cake_id) VALUES ('Strawberry Cake', 23.99, 'A Strawberry cake', false, 3);

INSERT INTO placed_orders (pickup_at, message, cake_id, status, total_price) VALUES ('2025-02-20T08:36:10.152882', null, 1, 'Pending', 5.99);
INSERT INTO placed_orders (pickup_at, message, cake_id, status, total_price) VALUES ('2025-02-20T08:36:10.152882', null, 1, 'Completed', 3.99);
INSERT INTO placed_orders (pickup_at, message, cake_id, status, total_price) VALUES ('2025-02-20T08:36:10.152882', null, 1, 'Pending', 14.99);

INSERT INTO customer (name, phone_number, email, placed_order_id) VALUES ('Customer', 'Phone', 'Email', 1);
INSERT INTO customer (name, phone_number, email, placed_order_id) VALUES ('Customer 2', 'Phone 2', 'Email 2', 2);
INSERT INTO customer (name, phone_number, email, placed_order_id) VALUES ('Customer 3', 'Phone 3', 'Email 3', 3);

COMMIT TRANSACTION;