CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50),
    total DECIMAL(19, 4) CHECK (total > 0),
    start_at TIMESTAMP,
    end_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    client_id TEXT REFERENCES clients(id)
);