create database techupdatingdb2;
USE techupdatingdb2;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(255) NOT NULL,
    account VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE avatar (
    id INT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    user_id INT,
    CONSTRAINT fk_avatar_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    user_id INT,
    CONSTRAINT fk_role_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(255) NOT NULL,
    user_id int,
    CONSTRAINT fk_course_user FOREIGN KEY (user_id) references user(id)
);

CREATE TABLE post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    short_description TEXT,
    created_at DATE,
    updated_at DATE,
    quantity_of_like INT CHECK (quantity_of_like >= 0),
    post_view INT CHECK (post_view >= 0),
    user_id INT,
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES user(id),
    course_id INT,
    CONSTRAINT fk_post_course FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);


CREATE TABLE comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    CONSTRAINT fk_comment_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    post_id int,
    CONSTRAINT fk_comment_post FOREIGN KEY(post_id) REFERENCES post(id) ON DELETE CASCADE,
    content TEXT
);

CREATE TABLE part (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    content TEXT,
    post_id INT NOT NULL,
    CONSTRAINT fk_part_post FOREIGN KEY(post_id) REFERENCES post(id) ON DELETE CASCADE
);

CREATE TABLE image (
    id INT PRIMARY KEY AUTO_INCREMENT,
    url_image TEXT,
    part_id int,
	CONSTRAINT fk_image_part FOREIGN KEY(part_id) REFERENCES part(id) ON DELETE CASCADE
);


-- remove the user foreign key in role
ALTER TABLE role
DROP FOREIGN KEY fk_role_user;

ALTER TABLE role
DROP COLUMN user_id;

-- create table user_role
create table user_role (
     id INT PRIMARY KEY AUTO_INCREMENT,
     role_id INT NOT NULL CHECK (role_id > 0),
     CONSTRAINT fk_role_user FOREIGN KEY (role_id) REFERENCES role(id),
     user_id INT NOT NULL check (user_id > 0),
    CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES user(id)
);


-- query to add new role
INSERT INTO `techupdatingdb2`.`role` (`id`, `name`) VALUES ('1', 'user');
INSERT INTO `techupdatingdb2`.`role` (`id`, `name`) VALUES ('2', 'admin');


-- query to create new user
INSERT INTO `techupdatingdb2`.`user` (`id`, `fullname`, `account`, `password`, `email`, `enabled`) VALUES ('1', 'Kim Ngọc Tân', 'tankim123', 'tankim123', 'tankim1972@gmail.com', '1');

-- create role for user
INSERT INTO `techupdatingdb2`.`user_role` (`id`, `role_id`, `user_id`) VALUES ('1', '1', '1');

-- create new course
INSERT INTO `techupdatingdb2`.`course` (`id`, `course_name`, `user_id`) VALUES ('1', 'Java Course', '1');



