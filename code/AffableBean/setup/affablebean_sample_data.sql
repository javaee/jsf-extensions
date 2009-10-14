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

INSERT INTO `category` VALUES(1, 'dairy', NOW());
INSERT INTO `category` VALUES(2, 'meats', NOW());
INSERT INTO `category` VALUES(3, 'bakery', NOW());
INSERT INTO `category` VALUES(4, 'fruitVeg', NOW());


--
-- Sample data for table `customer`
--

INSERT INTO `customer` VALUES(1, 'Fred Savage', 'fsavage@gmail.com', '606-252-924', 'Korunni 56', 2, '1234123412341234', NOW());

--
-- Sample data for table `product`
--

INSERT INTO `product` VALUES(1, 'milk', 1.19, NOW(), 1);
INSERT INTO `product` VALUES(2, 'cheese', 2.39, NOW(), 1);
INSERT INTO `product` VALUES(3, 'butter', 0.99, NOW(), 1);
INSERT INTO `product` VALUES(4, 'eggs', 1.99, NOW(), 1);
INSERT INTO `product` VALUES(5, 'meatPatties', 2.29, NOW(), 2);
INSERT INTO `product` VALUES(6, 'parmaHam', 3.49, NOW(), 2);
INSERT INTO `product` VALUES(7, 'chicken', 4.09, NOW(), 2);
INSERT INTO `product` VALUES(8, 'sausages', 3.55, NOW(), 2);
INSERT INTO `product` VALUES(9, 'loaf', 0.89, NOW(), 3);
INSERT INTO `product` VALUES(10, 'bagel', 1.19, NOW(), 3);
INSERT INTO `product` VALUES(11, 'bun', 1.65, NOW(), 3);
INSERT INTO `product` VALUES(12, 'cookie', 2.39, NOW(), 3);
INSERT INTO `product` VALUES(13, 'corn', 0.29, NOW(), 4);
INSERT INTO `product` VALUES(14, 'berries', 0.25, NOW(), 4);
INSERT INTO `product` VALUES(15, 'broccoli', 0.39, NOW(), 4);
INSERT INTO `product` VALUES(16, 'watermelon', 0.19, NOW(), 4);
