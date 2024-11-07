use library;

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE NOT NULL,
    role ENUM('Admin', 'Client') NOT NULL,
    avatar_path VARCHAR(255),
    date_of_birth DATE,
    address VARCHAR(255),
    status ENUM('active', 'suspended', 'deleted') DEFAULT 'active',
    last_login TIMESTAMP,
    is_verified BOOLEAN DEFAULT FALSE,
    gender ENUM('male', 'female', 'other'),
    has_completed_tutorial BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);