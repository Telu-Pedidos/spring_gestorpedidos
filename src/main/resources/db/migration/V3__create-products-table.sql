CREATE TABLE products (
    id INTEGER PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(500),
    image_url VARCHAR(2000),
    price DECIMAL(19, 4) NOT NULL CHECK (price >= 0),
    active BOOLEAN DEFAULT TRUE,
    category_id BIGINT REFERENCES categories(id)
);
