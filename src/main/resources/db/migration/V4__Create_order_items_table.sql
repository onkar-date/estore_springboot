CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL, -- Foreign key to orders table
    product_id BIGINT NOT NULL, -- Foreign key to products table
    quantity INT NOT NULL, -- Quantity of the product ordered
    price INT NOT NULL, -- Price of the product in paise (integer)

    -- Foreign key constraints
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
