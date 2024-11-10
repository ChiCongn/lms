show databases ;
create database if not exists library;
show databases ;
use library;

create table admins (
    admin_id int primary key auto_increment not null ,
    email varchar(100) not null ,
    username varchar(50) ,
    password varchar(50) not null ,
    avatar_path varchar(255),
    last_login timestamp,
    gender enum('male', 'female', 'other'),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    constraint unique_email unique (email)
);

create table librarians (
    librarian_id int primary key auto_increment not null ,
    email varchar(100) not null ,
    username varchar(50) ,
    password varchar(50) not null ,
    date_of_birth date,
    avatar_path varchar(255),
    last_login timestamp,
    gender enum('male', 'female', 'other'),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    constraint unique_email unique (email)
);

create table clients (
    user_id int primary key auto_increment not null ,
    email varchar(100) not null ,
    username varchar(50) ,
    password varchar(50) not null ,
    date_of_birth date,
    avatar_path varchar(255),
    status enum('active', 'suspended', 'deleted') default 'active',
    last_login timestamp,
    gender enum('male', 'female', 'other'),
    has_completed_tutorial boolean default false,
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    constraint unique_email unique (email)
);

create table authors (
    author_id int auto_increment primary key,
    pseudonym varchar(100),
    first_name varchar(50) not null ,
    last_name varchar(50) not null ,
    nationality varchar(100),
    info_url varchar(255),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table categories (
    category_id int auto_increment primary key ,
    name varchar(100) not null ,
    description text
);

create table books (
    book_id int auto_increment primary key ,
    title varchar(255) not null ,
    publication_year YEAR,
    page_count int,
    language varchar(50),
    description text,
    rating decimal(10, 1) check ( rating >= 1.0 and rating <= 5.0 ),
    total_copies int not null ,
    copies_available int not null ,
    cover_image_path VARCHAR(255),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

create table booksauthors (
    book_id int not null ,
    author_id int not null ,
    primary key (book_id, author_id),
    constraint fk_booksauthors_book foreign key (book_id)
        references books(book_id) on delete cascade on update cascade ,
    constraint fk_booksauthors_author foreign key (author_id)
        references authors(author_id) on delete cascade on update cascade
);

create table bookcategories (
    book_id int not null ,
    category_id int not null ,
    primary key (book_id, category_id),
    foreign key (book_id) references books(book_id),
    foreign key (category_id) references categories(category_id)
);

create table borrowedbooks (
    borrow_id int auto_increment primary key ,
    client_id int not null ,
    book_id int not null ,
    borrow_date date not null ,
    due_dat date not null ,
    return_date date not null ,
    status enum('borrowed', 'returned', 'overdue') default 'borrowed',
    constraint fk_borrowedbooks_user foreign key (client_id) references clients(client_id) on delete cascade ,
    constraint fk_borrowedbooks_book foreign key (book_id) references books(book_id) on delete cascade
);

create table fines (
    fine_id int auto_increment primary key ,
    client_id int not null ,
    borrow_id int not null ,
    fine_amount decimal(10, 2) not null ,
    paid boolean default false,
    payment_date date default null,
    constraint fk_fines_user foreign key (client_id) references clients(client_id) on delete cascade ,
    constraint fk_fines_borrow foreign key (borrow_id) references borrowedbooks(borrow_id) on delete cascade
);

create table reviews (
    review_id int auto_increment primary key ,
    client_id int not null ,
    book_id int not null ,
    review_text text,
    rating int check ( rating >= 1 and rating <= 5 ),
    review_date timestamp default CURRENT_TIMESTAMP,
    constraint fk_reviews_user foreign key (client_id) references clients(client_id) on delete cascade ,
    constraint fk_reviews_book foreign key (book_id) references books(book_id) on delete cascade
);

