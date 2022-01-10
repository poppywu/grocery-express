-- MySQL dump 10.13  Distrib 8.0.27, for macos11.6 (x86_64)
--
-- Host: localhost    Database: grocery_store
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `archive`
--

DROP TABLE IF EXISTS `archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `archive` (
  `archive_order_id` varchar(255) NOT NULL,
  `archive_store_name` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`archive_order_id`,`archive_store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `archive`
--

LOCK TABLES `archive` WRITE;
/*!40000 ALTER TABLE `archive` DISABLE KEYS */;
/*!40000 ALTER TABLE `archive` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `username` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `credit` varchar(255) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('aapple2','Alana','Apple','222-222-2222','1a7ba2a70a2a878c65df5fc7f65453108d18f029eecb0ec5c93d539cf51811c2',4),('ccherry4','Carlos','Cherry','444-444-4444','de45d698f3526cf08ad08cd94252150e611b7c408a7f4abe591122bd730fb3bd',5);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drone`
--

DROP TABLE IF EXISTS `drone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drone` (
  `drone_id` int NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `remaining_capacity` int DEFAULT NULL,
  `total_capacity` int DEFAULT NULL,
  `trips_remaining` int DEFAULT NULL,
  `pilot_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`drone_id`,`store_name`),
  KEY `FKqu36m0vti1e3i1tsm8dlfcctn` (`pilot_id`),
  KEY `FKjrlk4efxtvpfoocq3uv7054a9` (`store_name`),
  CONSTRAINT `FKjrlk4efxtvpfoocq3uv7054a9` FOREIGN KEY (`store_name`) REFERENCES `store` (`name`),
  CONSTRAINT `FKqu36m0vti1e3i1tsm8dlfcctn` FOREIGN KEY (`pilot_id`) REFERENCES `pilot` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drone`
--

LOCK TABLES `drone` WRITE;
/*!40000 ALTER TABLE `drone` DISABLE KEYS */;
INSERT INTO `drone` VALUES (1,'kroger',5,40,1,'ffig8'),(1,'publix',16,40,3,'ggrape17'),(2,'kroger',16,20,3,NULL);
/*!40000 ALTER TABLE `drone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `item_name` varchar(255) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `weight` int DEFAULT NULL,
  PRIMARY KEY (`item_name`,`store_name`),
  KEY `FKps3ero7utkfr298qnarpojvwr` (`store_name`),
  CONSTRAINT `FKps3ero7utkfr298qnarpojvwr` FOREIGN KEY (`store_name`) REFERENCES `store` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('cheesecake','kroger',4),('cheesecake','publix',8),('pot_roast','kroger',5);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `line`
--

DROP TABLE IF EXISTS `line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `line` (
  `item_name` varchar(255) NOT NULL,
  `price` int DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `weight` int DEFAULT NULL,
  `order_id` varchar(255) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  PRIMARY KEY (`item_name`,`order_id`,`store_name`),
  KEY `FK5py9jqnuk45l9q98r0ujlmka3` (`order_id`,`store_name`),
  CONSTRAINT `FK5py9jqnuk45l9q98r0ujlmka3` FOREIGN KEY (`order_id`, `store_name`) REFERENCES `orders` (`order_id`, `store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `line`
--

LOCK TABLES `line` WRITE;
/*!40000 ALTER TABLE `line` DISABLE KEYS */;
INSERT INTO `line` VALUES ('cheesecake',10,3,8,'purchaseA','publix'),('cheesecake',10,1,4,'purchaseD','kroger'),('pot_roast',10,3,5,'purchaseA','kroger'),('pot_roast',5,4,5,'purchaseB','kroger');
/*!40000 ALTER TABLE `line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` varchar(255) NOT NULL,
  `store_name` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `drone_id` int DEFAULT NULL,
  `store_name_by_drone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`,`store_name`),
  KEY `FK624gtjin3po807j3vix093tlf` (`customer_id`),
  KEY `FKf1ku2vl40sek0594rd7tgu4j0` (`drone_id`,`store_name_by_drone`),
  KEY `FK9f13wgpbbnguc4vwl2rvc1ibm` (`store_name`),
  CONSTRAINT `FK624gtjin3po807j3vix093tlf` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`username`),
  CONSTRAINT `FK9f13wgpbbnguc4vwl2rvc1ibm` FOREIGN KEY (`store_name`) REFERENCES `store` (`name`),
  CONSTRAINT `FKf1ku2vl40sek0594rd7tgu4j0` FOREIGN KEY (`drone_id`, `store_name_by_drone`) REFERENCES `drone` (`drone_id`, `store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('purchaseA','kroger','2021-12-04 20:57:32','aapple2',1,'kroger'),('purchaseA','publix','2021-12-04 20:58:07','ccherry4',1,'publix'),('purchaseB','kroger','2021-12-04 20:57:41','aapple2',1,'kroger'),('purchaseD','kroger','2021-12-04 20:57:57','ccherry4',2,'kroger');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pilot`
--

DROP TABLE IF EXISTS `pilot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pilot` (
  `username` varchar(255) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_num` varchar(255) DEFAULT NULL,
  `tax_id` varchar(255) DEFAULT NULL,
  `experience` int DEFAULT NULL,
  `license_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pilot`
--

LOCK TABLES `pilot` WRITE;
/*!40000 ALTER TABLE `pilot` DISABLE KEYS */;
INSERT INTO `pilot` VALUES ('ffig8','Finneas','Fig','888-888-8888','2db9725a51a8dc73ad1c1d293aa0ddb24ef494de3092f32a26fe492e1f33ae8c',33,'panam_10'),('ggrape17','Gillian','Grape','999-999-9999','56bb12e57a8f8e4d8e149eb1659fc392a803d3690fdf043389a2efd2be525559',31,'twa_21');
/*!40000 ALTER TABLE `pilot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `name` varchar(255) NOT NULL,
  `revenue` int DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('kroger',33000),('publix',33000);
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-04 21:02:31
