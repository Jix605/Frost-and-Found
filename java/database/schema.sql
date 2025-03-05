BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, cakes, placed_orders, standard_cakes, cake_options, customer, cake_extras;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE cake_options (
    name varchar(50) UNIQUE NOT NULL,
	category varchar(50) NOT NULL,
	available boolean DEFAULT true,
	price numeric NOT NULL,
	CONSTRAINT PK_cake_options PRIMARY KEY (name)
);

CREATE TABLE cakes (
    id SERIAL,
    style varchar(50) NOT NULL,
	size varchar(50) NOT NULL,
	flavor varchar(50) NOT NULL,
	frosting varchar(50) NULL,
	filling varchar(50) NULL,
	CONSTRAINT PK_cakes PRIMARY KEY (id),
	CONSTRAINT FK_cakes_style FOREIGN KEY (style) REFERENCES cake_options(name),
	CONSTRAINT FK_cakes_size FOREIGN KEY (size) REFERENCES cake_options(name),
	CONSTRAINT FK_cakes_flavor FOREIGN KEY (flavor) REFERENCES cake_options(name),
	CONSTRAINT FK_cakes_frosting FOREIGN KEY (frosting) REFERENCES cake_options(name),
	CONSTRAINT FK_cakes_filling FOREIGN KEY (filling) REFERENCES cake_options(name)
);

CREATE TABLE placed_orders (
    id SERIAL,
    pickup_at timestamp NOT NULL,
    message varchar(500) NULL,
	cake_id integer NOT NULL,
	status varchar(10) NOT NULL,
	total_price numeric NOT NULL,
	CONSTRAINT PK_placed_orders PRIMARY KEY (id),
	CONSTRAINT FK_cakes FOREIGN KEY (cake_id) REFERENCES cakes(id)
);

CREATE TABLE standard_cakes (
    id SERIAL,
    cake_id integer NOT NULL,
    name varchar(50) NOT NULL,
	price numeric NOT NULL,
	description varchar(255) NOT NULL,
	available boolean DEFAULT true,
	CONSTRAINT PK_standard_cakes PRIMARY KEY (id),
	CONSTRAINT FK_standard_cakes_cakes FOREIGN KEY (cake_id) REFERENCES cakes(id)
);

CREATE TABLE cake_extras (
    name varchar(50) NOT NULL,
    cake_id integer NOT NULL,
    CONSTRAINT FK_cake_extras_cake_id FOREIGN KEY (cake_id) REFERENCES cakes(id),
    CONSTRAINT FK_cake_extras_name FOREIGN KEY (name) REFERENCES cake_options(name)
);

CREATE TABLE customer (
    id serial NOT NULL,
	name varchar(50) NOT NULL,
	phone_number varchar(20) NOT NULL,
	email varchar(320) NOT NULL,
	placed_order_id integer NOT NULL,
	CONSTRAINT PK_customer PRIMARY KEY (id),
	CONSTRAINT FK_customer_placed_orders FOREIGN KEY (placed_order_id) REFERENCES placed_orders(id)
);

COMMIT TRANSACTION;
--ROLLBACK;