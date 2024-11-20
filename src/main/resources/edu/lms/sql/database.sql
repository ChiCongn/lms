show databases ;
create database if not exists library;
show databases ;
use library;

create table users (
    user_id int primary key auto_increment not null ,
    email varchar(100) not null ,
    username varchar(50) ,
    password varchar(50) not null ,
    role enum('admin', 'librarian', 'client'),
    date_of_birth date,
    avatar_path varchar(255),
    status enum('active', 'suspended') default 'active',
    last_login timestamp,
    gender enum('male', 'female', 'other'),
    has_completed_tutorial boolean default false,
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    constraint unique_email unique (email)
);

create table categories (
    category_id int auto_increment primary key ,
    name varchar(100) not null ,
    description text
);

create table books (
    book_id int auto_increment primary key ,
    title varchar(255) not null ,
    authors varchar(255),
    published_year varchar(12),
    page_count int,
    language varchar(50),
    description text,
    rating decimal(10, 1) check ( rating >= 0.0 and rating <= 5.0 ),
    total_copies int not null ,
    available_copies int not null ,
    cover_image_path varchar(255),
    canonical_volume_link varchar(255),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    recommended bit,
    favorite bit,
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
    user_id int not null ,
    book_id int not null ,
    borrow_date date not null ,
    due_dat date not null ,
    return_date date not null ,
    status enum('borrowed', 'returned', 'overdue') default 'borrowed',
    constraint fk_borrowedbooks_user foreign key (user_id) references users(user_id) on delete cascade ,
    constraint fk_borrowedbooks_book foreign key (book_id) references books(book_id) on delete cascade
);

create table fines (
    fine_id int auto_increment primary key ,
    user_id int not null ,
    borrow_id int not null ,
    fine_amount decimal(10, 2) not null ,
    paid boolean default false,
    payment_date date default null,
    constraint fk_fines_user foreign key (user_id) references users(user_id) on delete cascade ,
    constraint fk_fines_borrow foreign key (borrow_id) references borrowedbooks(borrow_id) on delete cascade
);

create table reviews (
    review_id int auto_increment primary key ,
    user_id int not null ,
    book_id int not null ,
    review_text text,
    rating int check ( rating >= 1 and rating <= 5 ),
    review_date timestamp default CURRENT_TIMESTAMP,
    constraint fk_reviews_user foreign key (user_id) references users(user_id) on delete cascade ,
    constraint fk_reviews_book foreign key (book_id) references books(book_id) on delete cascade
);

insert into users (email, username, password, role, date_of_birth, gender)
    VALUE ('qunhchi@gmail.com', 'quynhchi', 'QuynhChi@12', 'librarian', '2007-08-08', 'female');
 insert into reviews (user_id, book_id, review_text, rating) value (1, 2, 'test du me', 2.0);
insert into users (email, username, password, role, date_of_birth, gender) value ('23021461@vnu.edu.vn', 'chauanh', 'ChauAnh@08', 'client', '2005-02-21', 'female');
insert into users (email, username, password, role, date_of_birth, gender) value ('23021465@vnu.edu.vn', 'nguyenanh', 'NguyenAnh@06', 'client', '2005-08-15', 'male');
insert into users (email, username, password, role, date_of_birth, gender) value ('23021469@vnu.edu.vn', 'maianh', 'MaiAnh@08', 'client', '2005-10-11', 'female');
insert into users (email, username, password, role, date_of_birth, gender) value ('23021473@vnu.edu.vn', 'vietanh', 'VietAnh@06', 'client', '2005-02-01', 'male');
insert into users (email, username, password, role, date_of_birth, gender) value ('23021477@vnu.edu.vn', 'vanbien', 'VanBien@06', 'client', '2005-10-05', 'male');

insert into books (title, authors, language, description) value ('')

