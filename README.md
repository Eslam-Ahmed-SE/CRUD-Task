# CRUD-Task


# Create the database and table:



CREATE DATABASE `crud` /*!40100 DEFAULT CHARACTER SET utf8 */;


CREATE TABLE  `crud`.`employees` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `phone` int(10) unsigned NOT NULL,
  `level` varchar(45) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;


insert into employees values (2, 'Eslam Ahmed', '15 shshshs st.', 1099999999, 'junior'),
(5, 'Ahmed Mohammed', '13 zzz aaa st.', 1088888888, 'junior'),
(6, 'Ahmed Hassan', '123 asd st.', 1011111111, 'junior'),
(7, 'Khaled Hamada', '123 asd st.', 1011111111, 'junior'),
(8, 'Hazem Samara', '123 asd st.', 1011111111, 'junior'),
(9, 'Said Sayed', '123 asdf st.', 1011111111, 'junior'),
(10, 'Walaa Hasan', '123 asdf st.', 1011111111, 'junior'),
(11, 'Sara El Sayed', '123 asdf st.', 1011111111, 'junior');
