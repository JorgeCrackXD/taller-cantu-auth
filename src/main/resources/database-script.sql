CREATE TABLE user_role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP
);


CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP,
    role_id INTEGER NOT NULL,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
        REFERENCES user_role (id)
);


INSERT INTO user_role (name, creation_datetime) VALUES ('ADMINISTRATOR', NOW());

/*
CREATE TABLE phone_number (
	id SERIAL PRIMARY KEY,
	number INTEGER NOT NULL,
	creation_datetime TIMESTAMP NOT NULL,
	update_datetime TIMESTAMP NOT NULL,
	user_id INTEGER NOT NULL,
	CONSTRAINT fk_user
		FOREIGN KEY (user_id)
		REFERENCES users (id)
);

CREATE TABLE country (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP
);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    interior_number VARCHAR(10),
    exterior_number VARCHAR(10) NOT NULL,
    zip_number INTEGER NOT NULL,
    colony VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP,
    country_id INTEGER NOT NULL,
    CONSTRAINT fk_country
        FOREIGN KEY (country_id)
        REFERENCES country (id)
);

CREATE TABLE user_address (
    id SERIAL PRIMARY KEY,
    creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP,
    user_id INTEGER NOT NULL,
    address_id INTEGER NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users (id),
    CONSTRAINT fk_address
        FOREIGN KEY (address_id)
        REFERENCES address (id)
);

CREATE TABLE product_type (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP
);

CREATE TABLE product_brand (
	id SERIAL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DOUBLE PRECISION,
    stock INTEGER NOT NULL,
    model VARCHAR(100),
    creation_datetime TIMESTAMP NOT NULL,
    update_datetime TIMESTAMP,
    product_type_id INTEGER NOT NULL,
    product_brand_id INTEGER NOT NULL,
    CONSTRAINT fk_product_type
        FOREIGN KEY (product_type_id)
        REFERENCES product_type (id),
    CONSTRAINT fk_product_brand
        FOREIGN KEY (product_brand_id)
        REFERENCES product_brand (id)
);
*/

