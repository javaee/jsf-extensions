--
-- author: tgiunipero
-- date: 17 july 2009
--

--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Sample data for table `category`
--

INSERT INTO `category` (name) VALUES ('dairy'),('meats'),('bakery'),('fruitVeg');


--
-- Sample data for table `product`
--

INSERT INTO `product` (`name`, price, category_id) VALUES ('milk', 1.19, 1);
INSERT INTO `product` (`name`, price, category_id) VALUES ('cheese', 2.39, 1);
INSERT INTO `product` (`name`, price, category_id) VALUES ('butter', 0.99, 1);
INSERT INTO `product` (`name`, price, category_id) VALUES ('eggs', 1.99, 1);

INSERT INTO `product` (`name`, price, category_id) VALUES ('meatPatties', 2.29, 2);
INSERT INTO `product` (`name`, price, category_id) VALUES ('parmaHam', 3.49, 2);
INSERT INTO `product` (`name`, price, category_id) VALUES ('chicken', 4.09, 2);
INSERT INTO `product` (`name`, price, category_id) VALUES ('sausages', 3.55, 2);

INSERT INTO `product` (`name`, price, category_id) VALUES ('loaf', 0.89, 3);
INSERT INTO `product` (`name`, price, category_id) VALUES ('bagel', 1.19, 3);
INSERT INTO `product` (`name`, price, category_id) VALUES ('bun', 1.65, 3);
INSERT INTO `product` (`name`, price, category_id) VALUES ('cookie', 2.39, 3);

INSERT INTO `product` (`name`, price, category_id) VALUES ('corn', 0.29, 4);
INSERT INTO `product` (`name`, price, category_id) VALUES ('berries', 0.25, 4);
INSERT INTO `product` (`name`, price, category_id) VALUES ('broccoli', 0.39, 4);
INSERT INTO `product` (`name`, price, category_id) VALUES ('watermelon', 0.19, 4);