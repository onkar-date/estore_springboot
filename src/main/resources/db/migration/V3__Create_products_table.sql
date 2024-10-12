CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price INT NOT NULL, -- Store price in paise as an integer
    stock_quantity INT NOT NULL,
    image LONGBLOB,
    seller_id BIGINT NOT NULL, -- Foreign key to User (seller)
    FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE
);
