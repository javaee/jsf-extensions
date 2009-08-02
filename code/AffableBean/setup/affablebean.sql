--
-- author: troy giunipero
-- date: 17 july 2009
--

--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` tinyint(3) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `image_path` varchar(100) NOT NULL,
  `last_update` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains product categories, e.g., dairy, meats, etc.';

--
-- Sample data for table `category`
--

INSERT INTO `category` VALUES(1, 'dairy', 'img/categories/dairy.jpg', '2009-04-02 22:13:06');
INSERT INTO `category` VALUES(2, 'meats', 'img/categories/meats.jpg', '2009-04-02 22:13:06');
INSERT INTO `category` VALUES(3, 'bakery', 'img/categories/bakery.jpg', '2009-04-02 22:13:06');
INSERT INTO `category` VALUES(4, 'fruitVeg', 'img/categories/fruitVeg.jpg', '2009-04-02 22:13:06');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `city_region` varchar(2) NOT NULL,
  `cc_number` varchar(16) NOT NULL,
  `date_account_created` timestamp default NOW(),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer details';

--
-- Sample data for table `customer`
--

INSERT INTO `customer` VALUES(1, 'Fred Savage', 'fsavage@gmail.com', '606-252-924', 'Korunni 56', 2, '1234123412341234', '2009-04-04 20:39:01');

-- --------------------------------------------------------

--
-- Table structure for table `customer_order`
--

DROP TABLE IF EXISTS `customer_order`;
CREATE TABLE IF NOT EXISTS `customer_order` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `customer_id` mediumint(8) unsigned NOT NULL,
  `amount` decimal(5,2) default NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  KEY `idx_customer` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='maintains customer order details';

--
-- Sample data for table `customer_order`
--


-- --------------------------------------------------------

--
-- Table structure for table `ordered_product`
--

DROP TABLE IF EXISTS `ordered_product`;
CREATE TABLE IF NOT EXISTS `ordered_product` (
  `custorder_id` mediumint(8) unsigned NOT NULL,
  `product_id` mediumint(8) unsigned NOT NULL,
  `quantity` smallint(6) default '1',
  PRIMARY KEY  (`custorder_id`,`product_id`),
  KEY `idx_customer_order` (`custorder_id`),
  KEY `idx_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains details for an individual product type in an order';

--
-- Sample data for table `ordered_product`
--


-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` mediumint(8) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `category_id` tinyint(3) unsigned NOT NULL,
  `image_path` varchar(100) NOT NULL,
  `last_update` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='contains product details';

--
-- Sample data for table `product`
--

INSERT INTO `product` VALUES(1, 'milk', 1.19, 1, 'img/productThumbs/blah.png', '2009-04-04 20:39:01');
INSERT INTO `product` VALUES(2, 'cheese', 2.39, 1, 'img/productThumbs/cheese.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(3, 'cheddar', 0.99, 1, 'img/productThumbs/blah.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(4, 'eggs', 1.99, 1, 'img/productThumbs/eggs.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(5, 'meatPatties', 2.29, 2, 'img/productThumbs/meatPatties.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(6, 'lamb', 3.49, 2, 'img/productThumbs/blah.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(7, 'paté', 4.09, 2, 'img/productThumbs/blah.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(8, 'sausages', 3.55, 2, 'img/productThumbs/blah.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(9, 'cookie', 0.89, 3, 'img/productThumbs/cookie.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(10, 'bun', 1.19, 3, 'img/productThumbs/bun.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(11, 'loaf', 1.65, 3, 'img/productThumbs/loaf.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(12, 'bagel', 2.39, 3, 'img/productThumbs/bagel.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(13, 'corn', 0.29, 4, 'img/productThumbs/corn.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(14, 'berries', 0.25, 4, 'img/productThumbs/berries.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(15, 'watermelon', 0.19, 4, 'img/productThumbs/watermelon.png', '2009-04-04 20:39:02');
INSERT INTO `product` VALUES(16, 'turnips', 0.39, 4, 'img/productThumbs/blah.png', '2009-04-04 20:39:02');

-- --------------------------------------------------------

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer_order`
--
ALTER TABLE `customer_order`
  ADD CONSTRAINT `fk_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `ordered_product`
--
ALTER TABLE `ordered_product`
  ADD CONSTRAINT `fk_customer_order` FOREIGN KEY (`custorder_id`) REFERENCES `customer_order` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON UPDATE CASCADE;