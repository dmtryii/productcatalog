CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    category VARCHAR(255),
    stock INT NOT NULL CHECK (stock >= 0),
    created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated_date DATETIME DEFAULT NULL
);

INSERT INTO products (name, description, price, category, stock, created_date) VALUES
-- Electronics
('Laptop', 'A high-end laptop with 16GB RAM and 512GB SSD.', 1200.99, 'Electronics', 10, NOW()),
('Smartphone', 'Latest model with advanced features.', 899.99, 'Electronics', 50, NOW()),
('Tablet', '10-inch tablet with 64GB storage.', 299.99, 'Electronics', 25, NOW()),
('Smartwatch', 'Smartwatch with health monitoring features.', 199.99, 'Electronics', 40, NOW()),
('Gaming Console', 'Next-gen console with 4K support.', 499.99, 'Electronics', 15, NOW()),

-- Furniture
('Office Chair', 'Ergonomic office chair for better posture.', 299.99, 'Furniture', 20, NOW()),
('Dining Table', 'Wooden dining table with seating for six.', 599.99, 'Furniture', 8, NOW()),
('Sofa', 'Comfortable three-seater sofa.', 999.99, 'Furniture', 5, NOW()),
('Bookshelf', 'Spacious bookshelf with 5 compartments.', 199.99, 'Furniture', 10, NOW()),
('Bed Frame', 'Queen-size wooden bed frame.', 799.99, 'Furniture', 7, NOW()),

-- Accessories
('Headphones', 'Wireless noise-cancelling headphones.', 199.99, 'Accessories', 30, NOW()),
('Backpack', 'Waterproof laptop backpack.', 49.99, 'Accessories', 100, NOW()),
('Sunglasses', 'UV-protected polarized sunglasses.', 29.99, 'Accessories', 60, NOW()),
('Wristband', 'Fitness wristband with step tracking.', 99.99, 'Accessories', 35, NOW()),
('Wallet', 'Leather wallet with RFID blocking.', 24.99, 'Accessories', 50, NOW()),

-- Home Appliances
('Microwave Oven', '900W microwave oven with 20L capacity.', 129.99, 'Home Appliances', 12, NOW()),
('Refrigerator', 'Double-door refrigerator with frost-free feature.', 1199.99, 'Home Appliances', 8, NOW()),
('Washing Machine', 'Front-loading washing machine with 7kg capacity.', 899.99, 'Home Appliances', 10, NOW()),
('Vacuum Cleaner', 'Cordless vacuum cleaner with HEPA filter.', 149.99, 'Home Appliances', 18, NOW()),
('Air Purifier', 'Air purifier with 3-stage filtration system.', 199.99, 'Home Appliances', 15, NOW()),

-- Clothing
('T-shirt', 'Cotton t-shirt available in multiple colors.', 19.99, 'Clothing', 200, NOW()),
('Jeans', 'Regular-fit denim jeans.', 49.99, 'Clothing', 100, NOW()),
('Jacket', 'Water-resistant windbreaker jacket.', 89.99, 'Clothing', 50, NOW()),
('Sneakers', 'Lightweight running sneakers.', 79.99, 'Clothing', 70, NOW()),
('Hat', 'Wide-brimmed hat for sun protection.', 25.99, 'Clothing', 30, NOW());