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
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `isbn` varchar(20) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `review_text` text,
  `rating` float DEFAULT NULL,
  `review_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `isbn` (`isbn`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book` (`isbn`) ON DELETE CASCADE,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'2147483647',1,'Cuốn sách rất sâu sắc và đầy triết lý.',5,'2024-04-17 03:04:26'),(2,'2147483647',2,'Tôi đã thích cuốn sách này từ trang đầu tiên.',4,'2024-04-17 03:04:26'),(3,'9780061120084',3,'Tôi cảm thấy sách này có sức ảnh hưởng sâu sắc đối với tôi.',5,'2024-04-17 03:04:26'),(4,'9780061120084',4,'Sách có diễn biến rõ ràng và nhân vật đầy sức sống.',4,'2024-04-17 03:04:26'),(5,'9780439554930',1,'Tôi đã mê cuốn sách này từ lúc đầu đến cuối.',5,'2024-04-17 03:04:27'),(6,'9780439554930',2,'Câu chuyện kỳ diệu và phù hợp cho mọi lứa tuổi.',4,'2024-04-17 03:04:27'),(7,'9780451524935',3,'Cuốn sách này khiến tôi suy nghĩ nhiều về tương lai của thế giới.',5,'2024-04-17 03:04:27'),(8,'9780451524935',4,'Đọc sách này khiến tôi có những suy tư sâu xa về tự do và quyền lực.',4,'2024-04-17 03:04:27'),(9,'2147483647',1,'Sách này thực sự là một kiệt tác trong thế giới văn học.',5,'2024-04-17 03:04:27'),(10,'2147483647',2,'Tôi đã đọc sách này nhiều lần và vẫn cảm thấy thú vị.',4,'2024-04-17 03:04:27'),(11,'1421553627',3,'Too much Ecchi.',4,'2024-04-20 10:05:45'),(13,'1421553627',6,'interesting',5,'2024-04-20 13:32:31'),(14,'1421553627',15,'Best of sci-fi',5,'2024-04-20 13:34:29'),(15,'1421553627',17,'Depressed.',5,'2024-04-20 13:37:37'),(16,'1421553627',14,'Komm Süsser Tod',5,'2024-04-20 13:43:04'),(17,'1647292972',6,'perfect',5,'2024-04-23 06:21:49'),(18,'0984782869',6,'Siêu bổ ích.',5,'2024-04-28 04:29:24'),(19,'1476757801',6,'GREAT!!',4,'2024-04-28 04:39:37'),(20,'1647292972',6,'GOAT.',5,'2024-04-28 06:18:43'),(21,'1260463559',6,'helpful.',4,'2024-04-28 06:26:14'),(22,'0316398373',6,'Masterpiece.',5,'2024-04-28 06:28:04'),(23,'B0C3SJDLK8',6,'I feel so catered to.',4,'2024-04-28 06:29:19'),(24,'B0C3XX1C4V',6,'This is a slow ride and an enjoyable one as it builds its world and relationships at a relaxing pace.',4,'2024-04-28 06:30:16'),(25,'0307951529',6,'it is wonderful.',5,'2024-04-28 06:31:43'),(26,'1421553627',6,'AT Field.',4,'2024-04-30 06:38:17'),(27,'1260463559',13,'code code code...',5,'2024-05-04 04:56:07'),(28,'0804139298',6,'must-read for beginners',5,'2024-05-04 05:02:04'),(29,'1421553627',6,'Reiiii',4,'2024-05-04 05:02:24'),(30,'1599869772',6,'great strategy!',5,'2024-05-04 16:29:48'),(31,'9780451524935',15,'so helpful in my career',5,'2024-05-04 16:30:33'),(32,'0307951529',15,'fundamental',4,'2024-05-04 16:32:16'),(33,'B0C3SJDLK8',13,'great',4,'2024-05-04 16:32:37'),(34,'0316398373',13,'hmm...hmm',4,'2024-05-04 16:32:55'),(35,' 0593448235',13,'great expectations as the name',5,'2024-05-04 16:33:17'),(36,'1421553627',6,'test rate',4,'2024-05-06 13:15:46'),(37,'1260463559',6,'rate',5,'2024-05-06 13:16:19'),(38,'1-4391-6734-6',6,'greatest',5,'2024-05-06 16:16:03'),(39,'0804139298',13,'good one',4,'2024-05-07 11:14:37'),(40,'0307951529',6,'good',3,'2024-05-07 11:31:16'),(41,'0984782869',6,'test rate',4,'2024-05-07 11:34:24'),(42,'1647292972',13,'i like it',3,'2024-05-07 11:39:39'),(43,'0805139298',6,'greatest',5,'2024-05-07 11:53:57'),(44,'0671035975',6,'relax',4,'2024-05-07 15:20:36'),(45,'0141439661',23,'masterpiece',5,'2024-05-07 16:57:41'),(46,'1421553627',30,'test ava not save',4,'2024-05-07 17:55:26'),(47,' 0593448235',6,'test rate',4,'2024-05-08 04:21:04'),(48,'9780141441146',6,'good',5,'2024-05-08 04:21:27'),(49,'9781974732173',36,'cmt',4,'2024-05-08 08:45:27'),(50,'978-0547978840',6,'huu anh cmt',4,'2024-05-08 09:04:22'),(51,'9781569319000',30,'goat',5,'2024-05-08 14:52:30'),(52,'0553213695',6,'make you think',4,'2024-05-08 15:24:40'),(53,'9781591164418',40,'Legend Shounen',5,'2024-05-08 16:18:38'),(54,'9780099578512',6,'remain countless emotions for me',5,'2024-05-08 16:27:03'),(55,'2147483647',6,'i luv it',5,'2024-05-08 17:17:38'),(56,'B0C3SJDLK8',42,'truyen rat hay.',4,'2024-05-09 01:59:53');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-12 22:34:08
