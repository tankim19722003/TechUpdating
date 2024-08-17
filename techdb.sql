DROP DATABASE techupdatingdb2;
create database techupdatingdb2;
USE techupdatingdb2;

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fullname VARCHAR(255) NOT NULL,
    account VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    avatar varchar(255)
);


CREATE TABLE role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    user_id INT,
    CONSTRAINT fk_role_user FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

-- table category
create table category(
	id INT PRIMARY KEY auto_increment,
    name varchar(255) not null
);

-- create table language
create table language(
     id INT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(255) NOT NULL,
     category_id int not null,
     FOREIGN KEY(category_id) references category(id)  ON DELETE CASCADE
);
alter table language add  column img_name varchar(255);

CREATE TABLE course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(255) NOT NULL,
--    user_id int,
--    CONSTRAINT fk_course_user FOREIGN KEY (user_id) references user(id),
    language_id INT NOT NULL,
    CONSTRAINT fk_course_language FOREIGN KEY(language_id) REFERENCES language(id) ON DELETE CASCADE
);

alter table course add column image_name varchar(255);

alter table course add column price int check(price >= 0);
alter table course add column quantity_of_user int check(quantity_of_user >= 0);
alter table course add column short_description text;
alter table course add column created_at date;
alter table course add column updated_at date;

create table user_course(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id int,
    CONSTRAINT fk_user_course FOREIGN KEY (user_id) references user(id) ON DELETE CASCADE,
    course_id int,
    CONSTRAINT fk_course_user FOREIGN KEY (course_id) references course(id) ON DELETE CASCADE
);

alter table user_course add column enroll_date date;
alter table user_course add column enabled tinyint;

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

-- create category
INSERT INTO `techupdatingdb2`.`category` (`id`, `name`) VALUES ('1', 'frontend');
INSERT INTO `techupdatingdb2`.`category` (`id`, `name`) VALUES ('2', 'backend');


-- Create language
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `img_name`,`category_id`) VALUES ('1', 'Java', 'java.png',2);
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `img_name`,`category_id`) VALUES ('2', 'Ruby', 'ruby.jpg',2);
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `img_name`,`category_id`) VALUES ('3', 'Spring boot', 'springboot.webp',2);
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `img_name`,`category_id`) VALUES ('4', 'C#', 'cshape.webp',2);
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `img_name`,`category_id`) VALUES ('5', '.Net', 'dotnet.png',2);
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `img_name`,`category_id`) VALUES ('6', 'Ruby', 'ruby.jpg',2);

INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `category_id`, `img_name`) VALUES ('7', 'HTML', '1', 'html.jpg');
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `category_id`, `img_name`) VALUES ('8', 'CSS', '1', 'css.png');
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `category_id`, `img_name`) VALUES ('9', 'Bootstrap', '1', 'bootstrap.jpeg');
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `category_id`, `img_name`) VALUES ('10', 'Javascript', '1', 'javascript.png');
INSERT INTO `techupdatingdb2`.`language` (`id`, `name`, `category_id`, `img_name`) VALUES ('11', 'ReactJS', '1', 'reactjs.png');

-- create course
INSERT INTO `techupdatingdb2`.`course` (`id`, `course_name`, `language_id`, `image_name`, `price`, `quantity_of_user`, `short_description`, `created_at`, `updated_at`) VALUES ('1', 'Lập trình java core ', '1', 'java.png', '1000', '1', 'Khóa học Lập trình Java Core cung cấp kiến thức cơ bản và nền tảng về ngôn ngữ lập trình Java, giúp học viên hiểu rõ cách viết mã, cấu trúc chương trình, và các khái niệm cốt lõi như biến, vòng lặp, điều kiện, hàm, và lập trình hướng đối tượng (OOP). Thông qua các bài tập thực hành, học viên sẽ nắm vững các kỹ năng cần thiết để phát triển ứng dụng Java, mở ra cơ hội tham gia các dự án lập trình chuyên nghiệp và phát triển sự nghiệp trong lĩnh vực công nghệ thông tin.', '2003-01-01', '2024-08-15');
INSERT INTO `techupdatingdb2`.`course` (`id`, `course_name`, `language_id`, `image_name`, `price`, `quantity_of_user`, `short_description`, `created_at`, `updated_at`) VALUES ('2', 'Lập trình java nâng cao', '1', 'dotnet.png', '2000', '50', 'Khóa học Lập trình Java Nâng Cao tập trung vào việc mở rộng và phát triển các kỹ năng lập trình Java với các chủ đề chuyên sâu như xử lý đa luồng, lập trình mạng, quản lý bộ nhớ, và tối ưu hóa hiệu suất. Khóa học cũng đi sâu vào các framework và thư viện phổ biến như Spring, Hibernate, giúp học viên xây dựng các ứng dụng phức tạp và hiệu quả hơn. Thông qua việc giải quyết các bài toán thực tế và tham gia vào các dự án lớn, học viên sẽ có khả năng phát triển phần mềm chất lượng cao, đáp ứng các yêu cầu khắt khe trong môi trường doanh nghiệp.', '2003-01-01', '2024-08-15');

-- create register course
INSERT INTO `techupdatingdb2`.`user_course` (`id`, `user_id`, `course_id`) VALUES ('1', '1', '1');
UPDATE `techupdatingdb2`.`user_course` SET `enroll_date` = '2024-01-01', `enabled` = '1' WHERE (`id` = '1');
