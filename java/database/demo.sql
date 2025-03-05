
INSERT INTO cake_options (name, category, available, price)
VALUES ('Small', 'size', true, 2.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Medium', 'size', true, 3.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Large', 'size', true, 4.99);

INSERT INTO cake_options (name, category, available, price)
VALUES ('Chocolate', 'flavor', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Vanilla', 'flavor', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Marble', 'flavor', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Red Velvet', 'flavor', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Birthday', 'flavor', true, 4.99);

INSERT INTO cake_options (name, category, available, price)
VALUES ('Buttercream', 'frosting', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Whipped Frosting', 'frosting', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Chocolate Buttercream', 'frosting', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Cream Cheese', 'frosting', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('No Frosting', 'frosting', true, 0);


INSERT INTO cake_options (name, category, available, price)
VALUES ('Melted Chocolate', 'filling', true, 2.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Lemon', 'filling', true, 3.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Delicious Cream Cheese', 'filling', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Natural Vanilla', 'filling', true, 1.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('No Filling', 'filling', true, 0);

INSERT INTO cake_options (name, category, available, price)
VALUES ('Sprinkles', 'extra', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Candles', 'extra', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Icing Writing', 'extra', true, 4.99);

INSERT INTO cake_options (name, category, available, price)
VALUES ('Cupcake', 'style', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Sheet', 'style', true, 4.99);
INSERT INTO cake_options (name, category, available, price)
VALUES ('Layer', 'style', true, 4.99);

INSERT INTO cakes (style, size, flavor, frosting, filling)
VALUES('Layer', 'Medium', 'Vanilla', 'Cream Cheese', 'Natural Vanilla');
INSERT INTO cakes (style, size, flavor, frosting, filling)
VALUES('Sheet', 'Small', 'Chocolate', 'Chocolate Buttercream', 'Melted Chocolate');
INSERT INTO cakes (style, size, flavor, frosting, filling)
VALUES('Cupcake', 'Small', 'Red Velvet', 'Cream Cheese', 'Lemon');
INSERT INTO cakes (style, size, flavor, frosting, filling)
VALUES('Layer', 'Large', 'Marble', 'Buttercream', 'Melted Chocolate');
INSERT INTO cakes (style, size, flavor, frosting, filling)
VALUES('Layer', 'Large', 'Birthday', 'Whipped Frosting', 'Melted Chocolate');

INSERT INTO standard_cakes (name, price, description, available, cake_id)
VALUES('Vanilla Dream', 19.99, 'Classic vanilla', true, 1);
INSERT INTO standard_cakes (name, price, description, available, cake_id)
VALUES('Choco Luxe', 14.99, 'Decadent Chocolate', true, 2);
INSERT INTO standard_cakes (name, price, description, available, cake_id)
VALUES('Velvet Bliss', 12.99, 'Crimson Heaven', true, 3);
INSERT INTO standard_cakes (name, price, description, available, cake_id)
VALUES('Marble Delight', 23.99, 'Marbled Glory', true, 4);
INSERT INTO standard_cakes (name, price, description, available, cake_id)
VALUES('Sprinkle Party', 20.99, 'Birthday Delight', true, 5);