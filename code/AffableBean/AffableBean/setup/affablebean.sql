--
-- author: troy giunipero
-- date: 10 february 2009
--


SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Create database: `affablebean`
--

CREATE DATABASE `affablebean` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `affablebean`;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` smallint(4) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `description` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='describes the category which each product belongs to';



--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
CREATE TABLE IF NOT EXISTS `creditcard` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `type` enum('visa','mastercard') NOT NULL,
  `number` varchar(20) NOT NULL,
  `expiry` varchar(7) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='credit card details';



--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `familyname` varchar(45) NOT NULL,
  `telephone` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `address1` varchar(45) NOT NULL,
  `address2` varchar(45) NOT NULL,
  `city` varchar(30) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  `password` varchar(30) NOT NULL,
  `date_account_created` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer details';



--
-- Table structure for table `customer_basket`
--

DROP TABLE IF EXISTS `customer_basket`;
CREATE TABLE IF NOT EXISTS `customer_basket` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `quantity` varchar(4) NOT NULL default '1',
  `date_last_login` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains details for customer baskets';



--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `date` datetime default NULL,
  `shippingmethod` enum('1','2','3') NOT NULL default '1',
  `address1` varchar(45) default NULL,
  `address2` varchar(45) default NULL,
  `city` varchar(30) default NULL,
  `postcode` varchar(10) default NULL,
  `comments` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer order and shipping details';



--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `name` varchar(45) NOT NULL,
  `price` varchar(4) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='provides product details';
