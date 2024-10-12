CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL, -- Foreign key to User
    order_date DATETIME NOT NULL,
    status ENUM('PENDING', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL, -- Precision and scale for monetary value
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
