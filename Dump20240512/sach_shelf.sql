-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: sach
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `shelf`
--

DROP TABLE IF EXISTS `shelf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shelf` (
  `shelf_id` int NOT NULL AUTO_INCREMENT,
  `shelf_name` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `added_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`shelf_id`),
  KEY `user_id` (`user_id`),
  KEY `isbn` (`isbn`),
  CONSTRAINT `shelf_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `shelf_ibfk_2` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=447 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shelf`
--

LOCK TABLES `shelf` WRITE;
/*!40000 ALTER TABLE `shelf` DISABLE KEYS */;
INSERT INTO `shelf` VALUES (4,'rich',6,NULL,NULL),(6,'Private Shelf',5,'0804139298',NULL),(12,'study',6,NULL,NULL),(13,'Private Shelf',15,'0316398373',NULL),(21,'Want-to-read',15,'1599869772',NULL),(27,'rich',15,NULL,NULL),(31,'an\'s book',14,NULL,NULL),(32,'giang\'s book',14,NULL,NULL),(34,'Want-to-read',16,'9780439554930',NULL),(36,'rich',17,NULL,NULL),(37,'Want-to-read',17,'9780439554930',NULL),(41,'study',6,'9780807014271',NULL),(44,'study',6,'9780439554930',NULL),(52,'rich',6,'1476757801',NULL),(54,'rich',6,'1599869772',NULL),(77,'nhat\'s book',14,NULL,NULL),(78,'never read',14,NULL,NULL),(79,'rich',14,NULL,NULL),(80,'study',14,NULL,NULL),(81,'nam\'s book',14,NULL,NULL),(82,'anh\'s book',14,NULL,NULL),(92,'private',13,NULL,NULL),(94,'love',13,NULL,NULL),(112,'giang\'s book',6,NULL,NULL),(115,'an\'s book',6,NULL,NULL),(120,'rich',6,'0307951529',NULL),(121,'an\'s book',6,'9780451524935',NULL),(122,'an\'s book',6,'9780439554930',NULL),(134,'Want-to-read',19,'9780439554930',NULL),(135,'Want-to-read',19,'9780061120084',NULL),(143,'rich',13,NULL,NULL),(144,'giang\'s book',6,'9780451524935',NULL),(148,'Want-to-read',13,'9780451524935',NULL),(149,'private',13,'9780451524935',NULL),(150,'love',13,'9780451524935',NULL),(151,'Want-to-read',13,'1260463559',NULL),(156,'Want-to-read',15,'1647292972',NULL),(157,'Private Shelf',15,'1647292972',NULL),(158,'rich',15,'1647292972',NULL),(160,'Want-to-read',13,'0316398373',NULL),(161,'love',13,'0316398373',NULL),(162,'Currently Adding',6,NULL,NULL),(177,'anh\'s book',13,NULL,NULL),(178,'anh\'s book',13,'9780439554930',NULL),(179,'anh\'s book',13,'1421553627',NULL),(180,'anh\'s book',13,'9780451524935',NULL),(189,'test add shelf',13,NULL,NULL),(190,'test add shelf',13,'0316398373',NULL),(191,'test add shelf',13,'B0C3SJDLK8',NULL),(192,'test add shelf',13,'B0C3XX1C4V',NULL),(193,'test add shelf',13,'9780141441146',NULL),(194,'test add shelf',13,' 0593448235',NULL),(195,'test add shelf',13,'0141439661',NULL),(196,'test add shelf',13,'1599869772',NULL),(197,'test add shelf',14,NULL,NULL),(198,'test add shelf',14,'0316398373',NULL),(199,'test add shelf',14,'B0C3SJDLK8',NULL),(200,'test add shelf',14,'B0C3XX1C4V',NULL),(201,'test add shelf',14,'9780141441146',NULL),(202,'test add shelf',14,' 0593448235',NULL),(203,'test add shelf',14,'0141439661',NULL),(204,'test add shelf',14,'1599869772',NULL),(205,'test add shelf',22,NULL,NULL),(206,'test add shelf',22,'0316398373',NULL),(207,'test add shelf',22,'B0C3SJDLK8',NULL),(208,'test add shelf',22,'B0C3XX1C4V',NULL),(209,'test add shelf',22,'9780141441146',NULL),(210,'test add shelf',22,' 0593448235',NULL),(211,'test add shelf',22,'0141439661',NULL),(212,'test add shelf',22,'1599869772',NULL),(215,'Want-to-read',12,'1421553627',NULL),(223,'an\'s book',16,NULL,NULL),(224,'an\'s book',16,'9780451524935',NULL),(225,'an\'s book',16,'9780439554930',NULL),(226,'rich',16,NULL,NULL),(227,'rich',16,'9780439554930',NULL),(228,'rich',16,'1476757801',NULL),(229,'rich',16,'1599869772',NULL),(230,'rich',16,'0307951529',NULL),(231,'Want-to-read',23,'9780451524935',NULL),(234,'rich',35,NULL,NULL),(235,'rich',35,'9780439554930',NULL),(236,'rich',35,'1476757801',NULL),(237,'rich',35,'1599869772',NULL),(238,'rich',35,'0307951529',NULL),(255,'Want-to-read',27,'0316398373',NULL),(256,'Want-to-read',27,'9780061120084',NULL),(260,'Want-to-read',14,'0671035975',NULL),(261,'Want-to-read',14,'1476757801',NULL),(271,'Want-to-read',36,'9780451524935',NULL),(272,'Want-to-read',36,'9780439554930',NULL),(274,'dc',36,NULL,NULL),(275,'dc',36,'1421553627',NULL),(276,'Want-to-read',36,'0984782869',NULL),(277,'Currently Adding',36,'9780439554930',NULL),(278,'Currently Adding',36,'9780141441146',NULL),(279,'Currently Adding',36,NULL,NULL),(280,'Currently Adding',36,'1260463559',NULL),(281,'rich',36,NULL,NULL),(282,'rich',36,'9780439554930',NULL),(283,'rich',36,'1476757801',NULL),(284,'rich',36,'1599869772',NULL),(285,'rich',36,'0307951529',NULL),(286,'study',36,NULL,NULL),(287,'study',36,'9780807014271',NULL),(288,'study',36,'9780439554930',NULL),(289,'giang\'s book',36,NULL,NULL),(290,'giang\'s book',36,'9780439554930',NULL),(291,'giang\'s book',36,'9780451524935',NULL),(294,'an\'s book',6,'9780316769174',NULL),(296,'giang\'s book',6,'0553213695',NULL),(316,'Want-to-read',31,'0984782869','2024-05-08 13:50:59'),(317,'Want-to-read',31,'1647292972','2024-05-08 13:51:59'),(321,'Want-to-read',30,'9780099578512','2024-05-08 14:51:15'),(322,'Want-to-read',30,'0805139298','2024-05-08 14:51:38'),(323,'Want-to-read',38,'9780679720201','2024-05-08 15:02:36'),(324,'Want-to-read',38,'9780099578512','2024-05-08 15:04:17'),(325,'Currently Adding',38,'9780439554930','2024-05-08 15:04:27'),(326,'Currently Adding',38,'9780141441146','2024-05-08 15:04:27'),(327,'Currently Adding',38,NULL,'2024-05-08 15:04:27'),(329,'rich',38,NULL,'2024-05-08 15:04:29'),(330,'rich',38,'9780439554930','2024-05-08 15:04:29'),(331,'rich',38,'1476757801','2024-05-08 15:04:29'),(332,'rich',38,'1599869772','2024-05-08 15:04:29'),(333,'rich',38,'0307951529','2024-05-08 15:04:29'),(341,'Want-to-read',38,'1-4391-6734-6','2024-05-08 15:04:37'),(342,'Want-to-read',38,'B0C3XX1C4V','2024-05-08 15:05:00'),(343,'Want-to-read',38,'9780439554930','2024-05-08 15:05:17'),(364,'rich',39,NULL,'2024-05-08 15:07:41'),(365,'rich',39,'9780439554930','2024-05-08 15:07:41'),(366,'rich',39,'1476757801','2024-05-08 15:07:41'),(367,'rich',39,'1599869772','2024-05-08 15:07:41'),(368,'rich',39,'0307951529','2024-05-08 15:07:41'),(373,'study',39,NULL,'2024-05-08 15:07:47'),(374,'study',39,'9780807014271','2024-05-08 15:07:47'),(375,'study',39,'9780439554930','2024-05-08 15:07:47'),(378,'rich',39,NULL,'2024-05-08 15:08:37'),(379,'Want-to-read',39,'1421553627','2024-05-08 15:08:52'),(380,'Want-to-read',27,'1421553627','2024-05-08 15:18:32'),(381,'Currently Adding',27,'9780439554930','2024-05-08 15:18:41'),(382,'Currently Adding',27,'9780141441146','2024-05-08 15:18:41'),(383,'Currently Adding',27,NULL,'2024-05-08 15:18:41'),(384,'Currently Adding',27,'1260463559','2024-05-08 15:18:41'),(385,'Want-to-read',27,'1647292972','2024-05-08 15:19:14'),(386,'private',27,NULL,'2024-05-08 15:19:20'),(387,'private',27,'9780451524935','2024-05-08 15:19:20'),(388,'Want-to-read',27,'2147483647','2024-05-08 15:19:40'),(389,'Want-to-read',27,'0307951529','2024-05-08 15:19:43'),(390,'Want-to-read',27,'9780451524935','2024-05-08 15:19:51'),(391,'Want-to-read',27,'0804139298','2024-05-08 15:20:01'),(392,'Want-to-read',6,'2147483647','2024-05-08 16:04:46'),(413,'Want-to-read',40,'9781402726002','2024-05-08 16:17:19'),(414,'Want-to-read',40,'1421553627','2024-05-08 16:17:32'),(415,'Want-to-read',40,'9780718152437','2024-05-08 16:17:39'),(416,'Want-to-read',40,'9780679720201','2024-05-08 16:17:49'),(417,'Want-to-read',40,'9781591164418','2024-05-08 16:18:08'),(418,'Want-to-read',40,'0671035975','2024-05-08 16:19:13'),(419,'Currently Adding',6,'2147483647','2024-05-08 17:17:26'),(421,'Want-to-read',6,'978-0141439556','2024-05-09 01:05:42'),(424,'rich',41,NULL,'2024-05-09 01:08:15'),(425,'rich',41,'1421553627','2024-05-09 01:08:43'),(426,'Currently Adding',41,'9780439554930','2024-05-09 01:09:02'),(427,'Currently Adding',41,'9780141441146','2024-05-09 01:09:02'),(428,'Currently Adding',41,NULL,'2024-05-09 01:09:02'),(429,'Currently Adding',41,'1260463559','2024-05-09 01:09:02'),(431,'Want-to-read',42,'9780679783268','2024-05-09 01:56:16'),(432,'Want-to-read',42,'978-0141439556','2024-05-09 01:57:24'),(433,'Want-to-read',42,'1421553627','2024-05-09 01:57:51'),(436,'Private Shelf',42,'0316398373','2024-05-09 01:59:08'),(437,'Private Shelf',42,'1647292972','2024-05-09 01:59:08'),(438,'Want-to-read',42,'9781402726002','2024-05-09 01:59:56'),(439,'Want-to-read',42,'2147483647','2024-05-09 02:05:33'),(440,'Want-to-read',42,'0307951529','2024-05-09 02:05:35'),(441,'Want-to-read',6,'0671035975','2024-05-11 15:03:23'),(442,'Want-to-read',6,'9780593688137','2024-05-11 17:05:33'),(443,'Want-to-read',6,' 0593448235','2024-05-12 03:49:38'),(444,'Want-to-read',6,'9780316769174','2024-05-12 10:44:54'),(445,'giang\'s book',6,'9780316769174','2024-05-12 10:45:00'),(446,'Want-to-read',6,'9781416935865','2024-05-12 11:03:23');
/*!40000 ALTER TABLE `shelf` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-12 22:34:09
