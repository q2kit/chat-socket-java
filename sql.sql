-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: q2k.dev    Database: ltm
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.22.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('admin','a90007fa2f433b24ca0da4d4e8a15fdb4aedec170d9a1a26965084e426a86d41'),('miglee','c2c8961e1bdad1877dd479c2999392390d8be7a9bd2ca106d6e41a98acbdfef2'),('user1','43b4409147e5f43d51109a40db053e47a86a2ae6333b6e3d016620139f19e9aa'),('user2','b0f014f279915ad3a32cea894952f0e2ad6c7f1aac7c3cec34a27e1277cfb1fe'),('user3','8f4a08266ee3f7690cd5cc367948c9f2c4c16eaeef02f3d0895704a613165fee');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) NOT NULL,
  `room` int NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `message_account_null_fk` (`sender`),
  KEY `message_room_null_fk` (`room`),
  CONSTRAINT `message_account_null_fk` FOREIGN KEY (`sender`) REFERENCES `account` (`username`),
  CONSTRAINT `message_room_null_fk` FOREIGN KEY (`room`) REFERENCES `room` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10374 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (10356,'user1',10374,'user1 was kicked from the room!'),(10357,'user1',10374,'added user2 to room!'),(10358,'user1',10374,'Added user2 to the room'),(10359,'user1',10374,'user2 was banned from the room!'),(10360,'user1',10374,'user2 was kicked from the room!'),(10361,'user1',10374,'added user2 to room!'),(10362,'user1',10374,'123'),(10363,'user1',10374,'user2 was kicked from the room!'),(10364,'user1',10374,'added user2 to room!'),(10365,'user1',10374,'user2 was kicked from the room!'),(10366,'user1',10374,'added user2 to room!'),(10367,'user2',10374,'123'),(10368,'user2',10373,'Room details changed!'),(10369,'user3',10373,'user3 joined the room!'),(10370,'user3',10373,'123'),(10371,'user1',10374,'user2 was kicked from the room!'),(10372,'user1',10374,'Room details changed!'),(10373,'admin',10372,'added miglee to room!');
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `room_account_null_fk` (`owner`),
  CONSTRAINT `room_account_null_fk` FOREIGN KEY (`owner`) REFERENCES `account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10375 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (10372,'Room created by admin','cbf06754df2f70dd1f853bdccaec98cc6d8ba861a2a91d357540b9d561b6ceb7','admin'),(10373,'r2','37a8eec1ce19687d132fe29051dca629d164e2c4958ba141d5f4133a33f0688f','user2'),(10374,'Room 1','7a346ea7e27af78a4365629e190d950a78543304d372af6acab575240f84fd3e','user1');
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_member`
--

DROP TABLE IF EXISTS `room_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_member` (
  `room_id` int NOT NULL,
  `account_username` varchar(255) NOT NULL,
  `banned` tinyint(1) DEFAULT NULL,
  KEY `room_member_account_null_fk` (`account_username`),
  KEY `room_member_room_null_fk` (`room_id`),
  CONSTRAINT `room_member_account_null_fk` FOREIGN KEY (`account_username`) REFERENCES `account` (`username`),
  CONSTRAINT `room_member_room_null_fk` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_member`
--

LOCK TABLES `room_member` WRITE;
/*!40000 ALTER TABLE `room_member` DISABLE KEYS */;
INSERT INTO `room_member` VALUES (10372,'admin',0),(10373,'user2',0),(10374,'user1',0),(10373,'user3',0),(10372,'miglee',0);
/*!40000 ALTER TABLE `room_member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-10 12:57:00
