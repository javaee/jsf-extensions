--
-- Affable Bean Sample Database Schema
-- Version 1.0
--
-- author: troy giunipero
-- date: 10 february 2009
--


--
-- Create SCHEMA: `affablebean`
--

DROP SCHEMA IF EXISTS `affablebean`;
CREATE SCHEMA `affablebean`;

USE `affablebean`;


-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `image_path` VARCHAR(100) NOT NULL,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains product categories, e.g., dairy, meats, etc.';



--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `product_id` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `price` DECIMAL(5,2) NOT NULL,
  `category_id` TINYINT UNSIGNED NOT NULL,
  `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`),
  KEY `idx_fk_category_id` (`category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains product details';



--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(50) NOT NULL,
  `familyname` VARCHAR(50) NOT NULL,
  `telephone` VARCHAR(50) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `address1` VARCHAR(50) NOT NULL,
  `address2` VARCHAR(50) NOT NULL,
  `city` VARCHAR(30) NOT NULL,
  `postcode` VARCHAR(10) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer details';



--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `customer_id` SMALLINT UNSIGNED NOT NULL,
  `amount` DECIMAL(5,2),
  `status` VARCHAR(25),
  `comments` TEXT DEFAULT NULL,
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `idx_fk_customer_id` (`customer_id`),
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer order details';



--
-- Table structure for table `ordered_product`
--

DROP TABLE IF EXISTS `ordered_product`;
CREATE TABLE `ordered_product` (
  `order_id` MEDIUMINT UNSIGNED NOT NULL,
  `product_id` MEDIUMINT UNSIGNED NOT NULL,
  `quantity` SMALLINT DEFAULT 1,
  PRIMARY KEY (`order_id`, `product_id`),
  CONSTRAINT `fk_ordered_product_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_ordered_product_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains details for an individual product type in an order';
