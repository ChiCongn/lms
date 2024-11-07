use library;

CREATE TABLE genres (
    name VARCHAR(100) PRIMARY KEY NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id INT,
    genre_name VARCHAR(100),
    publication_year YEAR,
    page_count INT,
    language VARCHAR(50),
    description TEXT,
    image_url VARCHAR(255),
    availability BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_books_author_id FOREIGN KEY (author_id) REFERENCES authors(id) ON UPDATE CASCADE,
    CONSTRAINT fk_books_genre_id FOREIGN KEY (genre_name) REFERENCES genres(name) ON UPDATE CASCADE
);