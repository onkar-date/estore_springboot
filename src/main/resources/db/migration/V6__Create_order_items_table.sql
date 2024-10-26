CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    sub_order_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,

    quantity INT NOT NULL,
    price INT NOT NULL,
    total_price INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,

    -- Foreign key constraints
    FOREIGN KEY (sub_order_id) REFERENCES sub_orders(id) ON DELETE CASCADE,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES images(id) ON DELETE CASCADE
);
