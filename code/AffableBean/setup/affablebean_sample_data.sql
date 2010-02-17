--
-- author: troy giunipero
-- date: 17 july 2009
--

--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Sample data for table `category`
--

INSERT INTO `category` (name) VALUES ('dairy'),('meats'),('bakery'),('fruit & veg');


--
-- Sample data for table `customer`
--

-- INSERT INTO `customer` VALUES(NULL, 'Derek Mosley', 'd.mosley@gmail.com', '606-252-924', 'Korunní 56', 2, '1234123412341234', NOW());
INSERT INTO affablebean.customer (`name`, email, phone, address, city_region, cc_number, date_created)
       VALUES ('Derek Mosley', 'd.mosley@gmail.com', '606-252-924', 'Korunní 56', '2', '1234123412341234', DEFAULT)


--
-- Sample data for table `product`
--

INSERT INTO `product` VALUES(NULL, 'milk', 1.19, NOW(), 1);
INSERT INTO `product` VALUES(NULL, 'cheese', 2.39, NOW(), 1);
INSERT INTO `product` VALUES(NULL, 'butter', 0.99, NOW(), 1);
INSERT INTO `product` VALUES(NULL, 'eggs', 1.99, NOW(), 1);

INSERT INTO `product` VALUES(NULL, 'meatPatties', 2.29, NOW(), 2);
INSERT INTO `product` VALUES(NULL, 'parmaHam', 3.49, NOW(), 2);
INSERT INTO `product` VALUES(NULL, 'chicken', 4.09, NOW(), 2);
INSERT INTO `product` VALUES(NULL, 'sausages', 3.55, NOW(), 2);

INSERT INTO `product` VALUES(NULL, 'loaf', 0.89, NOW(), 3);
INSERT INTO `product` VALUES(NULL, 'bagel', 1.19, NOW(), 3);
INSERT INTO `product` VALUES(NULL, 'bun', 1.65, NOW(), 3);
INSERT INTO `product` VALUES(NULL, 'cookie', 2.39, NOW(), 3);

INSERT INTO `product` VALUES(NULL, 'corn', 0.29, NOW(), 4);
INSERT INTO `product` VALUES(NULL, 'berries', 0.25, NOW(), 4);
INSERT INTO `product` VALUES(NULL, 'broccoli', 0.39, NOW(), 4);
INSERT INTO `product` VALUES(NULL, 'watermelon', 0.19, NOW(), 4);
