-- phpMyAdmin SQL Dump
-- version 2.11.7.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 05, 2009 at 03:15 PM
-- Server version: 5.0.41
-- PHP Version: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` tinyint(3) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `image_path` varchar(100) NOT NULL,
  `last_update` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains product categories, e.g., dairy, meats, etc.' AUTO_INCREMENT=5 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` VALUES(1, 'dairy', 'img/categories/dairy.jpg', '2009-04-02 22:13:06');
INSERT INTO `category` VALUES(2, 'meats', 'img/categories/meats.jpg', '2009-04-02 22:13:06');
INSERT INTO `category` VALUES(3, 'bakery', 'img/categories/bakery.jpg', '2009-04-02 22:13:06');
INSERT INTO `category` VALUES(4, 'fruit & veg', 'img/categories/fruitVeg.jpg', '2009-04-02 22:13:06');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customer_id` smallint(5) unsigned NOT NULL auto_increment,
  `firstname` varchar(50) NOT NULL,
  `familyname` varchar(50) NOT NULL,
  `telephone` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `address1` varchar(50) NOT NULL,
  `address2` varchar(50) NOT NULL,
  `city` varchar(30) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer details' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `customer`
--


-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` mediumint(8) unsigned NOT NULL auto_increment,
  `customer_id` smallint(5) unsigned NOT NULL,
  `amount` decimal(5,2) default NULL,
  `status` varchar(25) default NULL,
  `comments` text,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`order_id`),
  KEY `idx_fk_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer order details' AUTO_INCREMENT=1 ;

--
-- Dumping data for table `order`
--


-- --------------------------------------------------------

--
-- Table structure for table `ordered_product`
--

CREATE TABLE `ordered_product` (
  `order_id` mediumint(8) unsigned NOT NULL,
  `product_id` mediumint(8) unsigned NOT NULL,
  `quantity` smallint(6) default '1',
  PRIMARY KEY  (`order_id`,`product_id`),
  KEY `fk_ordered_product_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains details for an individual product type in an order';

--
-- Dumping data for table `ordered_product`
--


-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` mediumint(8) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `price` decimal(5,2) NOT NULL,
  `category_id` tinyint(3) unsigned NOT NULL,
  `last_update` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`product_id`),
  KEY `idx_fk_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains product details' AUTO_INCREMENT=17 ;

--
-- Dumping data for table `product`
--

INSERT INTO `product` VALUES(1, 'milk', 1.19, 1, '2009-04-04 20:39:01');
INSERT INTO `product` VALUES(2, 'parmesan', 2.39, 1, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(3, 'cheddar', 0.99, 1, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(4, 'ice cream', 1.99, 1, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(5, 'beef', 2.29, 2, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(6, 'lamb', 3.49, 2, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(7, 'paté', 4.09, 2, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(8, 'sausages', 3.55, 2, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(9, 'white loaf', 0.89, 3, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(10, 'rye', 1.19, 3, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(11, 'wholemeal loaf', 1.65, 3, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(12, 'ciabatta', 2.39, 3, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(13, 'apples', 0.29, 4, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(14, 'bananas', 0.25, 4, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(15, 'carrots', 0.19, 4, '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(16, 'turnips', 0.39, 4, '2009-04-04 20:39:02');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`) ON UPDATE CASCADE;

--
-- Constraints for table `ordered_product`
--
ALTER TABLE `ordered_product`
  ADD CONSTRAINT `fk_ordered_product_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ordered_product_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON UPDATE CASCADE;
