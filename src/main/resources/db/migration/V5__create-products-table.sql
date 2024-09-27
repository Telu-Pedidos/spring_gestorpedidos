CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(500),
    image_url VARCHAR(2000),
    price DECIMAL(19, 2) NOT NULL CHECK (price >= 0),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    category_id BIGINT REFERENCES categories(id),
    model_id BIGINT REFERENCES models(id)
);
