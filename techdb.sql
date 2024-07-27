create database techupdatingdb;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(255) NOT NULL,
    account VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enable BOOLEAN NOT NULL DEFAULT TRUE,
    birthdate DATE,
    role_id int,
    foreign key(role_id) references role(id)
);

CREATE TABLE avatar (
    id INT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
);

CREATE TABLE course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(255) NOT NULL,
    user_id int,
    FOREIGN KEY (user_id) references user(id)
);

CREATE TABLE post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    short_description TEXT,
    created_at DATE,
    updated_at DATE,
    quantity_of_like int check(quantity_of_like >= 0),
    post_view int check(quantity_of_like >= 0),
    user_id int,
    FOREIGN KEY(user_id) REFERENCES user(id),
    course_id int,
    FOREIGN KEY (course_id) REFERENCES course(id)
);


CREATE TABLE comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    FOREIGN KEY (user_id) REFERENCES user(id),
    post_id int,
    FOREIGN KEY(post_id) REFERENCES post(id),
    content TEXT
);

CREATE TABLE part (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    post_id int,
    FOREIGN KEY(post_id) REFERENCES post(id)
);

CREATE TABLE paragraph (
    id INT PRIMARY KEY AUTO_INCREMENT,
    content TEXT,
    part_id int,
    FOREIGN KEY(part_id) REFERENCES part(id)
);

CREATE TABLE image (
    id INT PRIMARY KEY AUTO_INCREMENT,
    url_image TEXT,
    paragraph_id int,
    FOREIGN KEY(paragraph_id) REFERENCES paragraph(id)
);


-- remove colum enable
alter table user drop column enable;


-- remove birthdate in user
ALTER TABLE user DROP COLUMN birthdate;








