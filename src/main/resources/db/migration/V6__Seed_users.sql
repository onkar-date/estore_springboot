-- Check if the ADMIN user exists, if not, insert it
INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'admin' AS username, 'pass123' AS password, 'admin@example.com' AS email,
                      'Admin' AS first_name, 'User' AS last_name, 'ADMIN' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin' OR email = 'admin@example.com')
LIMIT 1;

-- Insert SELLER users (starting from ID 2 onwards)
INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'seller1' AS username, 'pass123' AS password, 'seller1@example.com' AS email,
                      'Seller' AS first_name, 'One' AS last_name, 'SELLER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seller1' OR email = 'seller1@example.com')
LIMIT 1;

INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'seller2' AS username, 'pass123' AS password, 'seller2@example.com' AS email,
                      'Seller' AS first_name, 'Two' AS last_name, 'SELLER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seller2' OR email = 'seller2@example.com')
LIMIT 1;

INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'seller3' AS username, 'pass123' AS password, 'seller3@example.com' AS email,
                      'Seller' AS first_name, 'Three' AS last_name, 'SELLER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seller3' OR email = 'seller3@example.com')
LIMIT 1;

INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'seller4' AS username, 'pass123' AS password, 'seller4@example.com' AS email,
                      'Seller' AS first_name, 'Four' AS last_name, 'SELLER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'seller4' OR email = 'seller4@example.com')
LIMIT 1;

-- Insert CUSTOMER users (starting after sellers)
INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'customer1' AS username, 'pass123' AS password, 'customer1@example.com' AS email,
                      'Customer' AS first_name, 'One' AS last_name, 'CUSTOMER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'customer1' OR email = 'customer1@example.com')
LIMIT 1;

INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'customer2' AS username, 'pass123' AS password, 'customer2@example.com' AS email,
                      'Customer' AS first_name, 'Two' AS last_name, 'CUSTOMER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'customer2' OR email = 'customer2@example.com')
LIMIT 1;

INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'customer3' AS username, 'pass123' AS password, 'customer3@example.com' AS email,
                      'Customer' AS first_name, 'Three' AS last_name, 'CUSTOMER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'customer3' OR email = 'customer3@example.com')
LIMIT 1;

INSERT INTO users (username, password, email, first_name, last_name, role, active)
SELECT * FROM (SELECT 'customer4' AS username, 'pass123' AS password, 'customer4@example.com' AS email,
                      'Customer' AS first_name, 'Four' AS last_name, 'CUSTOMER' AS role, true AS active) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'customer4' OR email = 'customer4@example.com')
LIMIT 1;
