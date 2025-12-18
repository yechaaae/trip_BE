-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: localhost    Database: enjoytrip
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `attractions`
--

DROP TABLE IF EXISTS `attractions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attractions` (
  `content_id` int NOT NULL,
  `content_type_id` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `addr1` varchar(255) DEFAULT NULL,
  `first_image` varchar(500) DEFAULT NULL,
  `first_image2` varchar(500) DEFAULT NULL,
  `sido_code` int DEFAULT NULL,
  `gugun_code` int DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`content_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attractions`
--

LOCK TABLES `attractions` WRITE;
/*!40000 ALTER TABLE `attractions` DISABLE KEYS */;
/*!40000 ALTER TABLE `attractions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text,
  `rating` int DEFAULT '0',
  `original_file` varchar(255) DEFAULT NULL,
  `save_file` varchar(255) DEFAULT NULL,
  `hit` int DEFAULT '0',
  `like_count` int DEFAULT '0',
  `regist_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `type` int DEFAULT '1' COMMENT '1:자유, 2:리뷰',
  `content_id` int DEFAULT NULL COMMENT '관광지 ID',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'althtjr1127','경복궁','여자친구랑 간 여행이라 너무 좋았어요',4,NULL,NULL,3,0,'2025-12-14 15:56:22',1,NULL),(2,'althtjr1127','ㅇㄹㅇㄹ','ㄹㅇㅇㄹ',0,NULL,NULL,15,0,'2025-12-14 15:56:31',1,NULL);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follows`
--

DROP TABLE IF EXISTS `follows`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follows` (
  `from_user_id` varchar(20) NOT NULL COMMENT '팔로우 하는 사람 (팬)',
  `to_user_id` varchar(20) NOT NULL COMMENT '팔로우 받는 사람 (스타)',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`from_user_id`,`to_user_id`),
  KEY `fk_follows_to` (`to_user_id`),
  CONSTRAINT `fk_follows_from` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_follows_to` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follows`
--

LOCK TABLES `follows` WRITE;
/*!40000 ALTER TABLE `follows` DISABLE KEYS */;
/*!40000 ALTER TABLE `follows` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `mno` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nickname` varchar(50) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `birth_date` varchar(20) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `profile_img` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mno`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','1234','admin@ssafy.com','관리자','010-0000-0000','1990-01-01','M','2025-12-05 06:04:28',NULL,NULL),(2,'althtjr1127','sod073530','althtjr1127@naver.com','자바 개발자',NULL,'2025-07-14',NULL,'2025-12-14 14:22:21','/upload/2b22451d-57c9-4b9b-ae0f-6e148c4a66ce_[크기변환]Adobe Express - file.jpg',NULL),(3,'abc','1234','althtjr1127@naver.com','디덕','010-2564-3149','2025-09-17','M','2025-12-17 08:32:45','/upload/15f6632c-0d23-4dd9-96ba-e43e8d8a311d_KakaoTalk_20240223_001034665.jpg','안녕!'),(5,'ddd','1234','dsf@naver.com','dd','010-5555-6545','2025-12-12','M','2025-12-17 09:12:16',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-18 16:09:39
