-- MySQL dump 10.13  Distrib 8.0.42, for macos15 (arm64)
--
-- Host: localhost    Database: enjoytrip
-- ------------------------------------------------------
-- Server version	9.5.0

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
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '3345dd9e-d67e-11f0-8936-846fa1da625a:1-158';

--
-- Table structure for table `attraction_bookmark`
--

DROP TABLE IF EXISTS `attraction_bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attraction_bookmark` (
  `bookmark_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(20) NOT NULL,
  `content_id` int NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `addr1` varchar(100) DEFAULT NULL,
  `first_image` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`bookmark_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `attraction_bookmark_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attraction_bookmark`
--

LOCK TABLES `attraction_bookmark` WRITE;
/*!40000 ALTER TABLE `attraction_bookmark` DISABLE KEYS */;
INSERT INTO `attraction_bookmark` VALUES (1,'althtjr1127',2763807,'간데메공원','서울특별시 동대문구 서울시립대로2길 59 (답십리동)','http://tong.visitkorea.or.kr/cms/resource/80/3505480_image2_1.jpg',NULL,NULL),(2,'althtjr1127',1118418,'감삼못공원','대구광역시 서구 서대구로3길 43 (내당동)','http://tong.visitkorea.or.kr/cms/resource/44/3576044_image2_1.JPG',NULL,NULL),(3,'althtjr1127',3378995,'고싸움놀이테마파크','광주광역시 남구 고싸움로 2 (칠석동)','http://tong.visitkorea.or.kr/cms/resource/79/3380379_image2_1.png',NULL,NULL),(4,'althtjr1127',2767573,'감중공원','인천광역시 서구 가정로98번길 18','http://tong.visitkorea.or.kr/cms/resource/10/3395610_image2_1.JPG',37.4932378921,126.6736203249),(5,'althtjr1127',3483083,'간송옛집','서울특별시 도봉구 시루봉로 149-18 (방학동)','http://tong.visitkorea.or.kr/cms/resource/81/3483081_image2_1.jpg',37.6649464754,127.0282025819),(6,'althtjr1127',2726843,'가덕도 연대봉','부산광역시 강서구 천성동',NULL,35.0317518247,128.8216713782);
/*!40000 ALTER TABLE `attraction_bookmark` ENABLE KEYS */;
UNLOCK TABLES;

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
  `attraction_title` varchar(100) DEFAULT NULL,
  `attraction_img` varchar(255) DEFAULT NULL,
  `attraction_addr` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'althtjr1127','경복궁','여자친구랑 간 여행이라 너무 좋았어요',4,NULL,NULL,3,0,'2025-12-14 15:56:22',1,NULL,NULL,NULL,NULL,NULL,NULL),(2,'althtjr1127','ㅇㄹㅇㄹ','ㄹㅇㅇㄹ',0,NULL,NULL,17,0,'2025-12-14 15:56:31',1,NULL,NULL,NULL,NULL,NULL,NULL),(5,'althtjr1127','ㅇㄹㄴㅇㄹ','ㄴㅇㄹㄴㅇ',0,NULL,NULL,6,0,'2025-12-18 11:53:04',1,NULL,NULL,NULL,NULL,NULL,NULL),(6,'althtjr1127','경복궁','ㅁㄴㅇㄹㅇㄴㄹ',3,NULL,NULL,6,0,'2025-12-18 12:04:11',2,NULL,NULL,NULL,NULL,NULL,NULL),(7,'althtjr1127','대구 계산동성당','ㄴㄹㅇ',4,NULL,NULL,0,0,'2025-12-18 13:38:20',2,NULL,NULL,NULL,NULL,NULL,NULL),(8,'althtjr1127','국립 대전 현충원','ㅇㄹㄹㅇㅇㄹㄹㄹㅇㅇ',4,NULL,NULL,2,0,'2025-12-18 13:45:39',2,NULL,'국립 대전 현충원','http://tong.visitkorea.or.kr/cms/resource/85/3051785_image2_1.JPG',NULL,36.3632959655,127.2991975797),(9,'althtjr1127','감중공원','ㅇㅇㅇ',4,NULL,NULL,0,0,'2025-12-18 13:53:38',2,NULL,'감중공원','http://tong.visitkorea.or.kr/cms/resource/10/3395610_image2_1.JPG',NULL,37.4932378921,126.6736203249),(10,'althtjr1127','경복궁','ㅇ',5,NULL,NULL,0,0,'2025-12-18 13:54:44',2,NULL,'경복궁','http://tong.visitkorea.or.kr/cms/resource/98/3487598_image2_1.jpg',NULL,37.5760836609,126.9767375783),(11,'althtjr1127','구미 금산리 떡버들나무','ㅇㅇㅇ',4,NULL,NULL,1,0,'2025-12-18 13:55:12',2,NULL,'구미 금산리 떡버들나무','http://tong.visitkorea.or.kr/cms/resource/58/3512858_image2_1.jpg',NULL,36.1600439799,128.5175253232),(12,'althtjr1127','광주 3·1 만세운동 기념비','ㄴㅇㄹㅇ',4,NULL,NULL,0,0,'2025-12-18 14:08:56',2,NULL,'광주 3·1 만세운동 기념비','http://tong.visitkorea.or.kr/cms/resource/57/3514657_image2_1.jpg',NULL,35.136761993,126.9096947007);
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
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-19 10:26:31
