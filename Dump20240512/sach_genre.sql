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
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genre` (
  `genre_id` int NOT NULL AUTO_INCREMENT,
  `genre_name` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (1,'Tình cảm – Romance','According to the Romance Writers of America, \"Two basic elements comprise every romance novel: a central love story and an emotionally-satisfying and optimistic ending.\" Both the conflict and the climax of the novel should be directly related to that core theme of developing a romantic relationship, although the novel can also contain subplots that do not specifically relate to the main characters\' romantic love. Other definitions of a romance novel may be broader, including other plots and endings or more than two people, or narrower, restricting the types of romances or conflicts.'),(2,'Bí ẩn – Mystery','The mystery genre is a genre of fiction that follows a crime (like a murder or a disappearance) from the moment it is committed to the moment it is solved. Mystery novels are often called “whodunnits” because they turn the reader into a detective trying to figure out the who, what, when, and how of a particular crime. Most mysteries feature a detective or private eye solving a case as the central character.'),(3,'Giả tưởng và khoa học viễn tưởng – Fantasy & Science fiction','Science fiction (abbreviated SF or sci-fi with varying punctuation and capitalization) is a broad genre of fiction that often involves speculations based on current or future science or technology. Science fiction is found in books, art, television, films, games, theatre, and other media. In organizational or marketing contexts, science fiction can be synonymous with the broader definition of speculative fiction, encompassing creative works incorporating imaginative elements not found in contemporary reality; this includes fantasy, horror and related genres. Science fantasy is largely based on writing entertainingly and rationally about alternate possibilities in settings that are contrary to known reality.'),(4,'Kinh dị, giật gân – Thrillers & Horror','Thriller is a genre of fiction with numerous, often overlapping, subgenres, including crime, horror, and detective fiction. Thrillers are characterized and defined by the moods they elicit, giving their audiences heightened feelings of suspense, excitement, surprise, anticipation and anxiety. This genre is well suited to film and television.'),(5,'Truyền cảm hứng – Self-help','Self-help, or self-improvement, is a self-guided improvement—economically, intellectually, or emotionally—often with a substantial psychological basis. Many different self-help groupings exist and each has its own focus, techniques, associated beliefs, proponents and in some cases, leaders. \"Self-help culture, particularly Twelve-Step culture, has provided some of our most robust new language: recovery, dysfunctional families, and codependency.\"'),(6,'Tiểu sử, tự truyện và hồi ký','As a literary genre, a memoir (from the French: mémoire from the Latin memoria, meaning \"memory\", or a reminiscence), forms a subclass of autobiography – although the terms \'memoir\' and \'autobiography\' are almost interchangeable in modern parlance. Memoir is autobiographical writing, but not all autobiographical writing follows the criteria for memoir.'),(7,'Truyện ngắn – Short Stories','A short story is a short work of prose fiction. It may be in any genre of fiction, and the exact definition of \"short\" will often depend on the genre.'),(8,'Dạy nấu ăn – Cookbooks','Non-fiction books that contain a collection of recipes, techniques, and tricks of the trade or else focus on the exploration of food, cooking, and culture of food. Many cookbooks are divided into sections such as baking, dinner, and breakfast. A specialty cookbook may focus only on a certain country\'s cuisine, such as Italian or Cajun. There are some cookbooks that are written to highlight one ingredient (i.e. honey), and some cookbooks focused on only one branch of cooking (such as bread.) Even further, some cookbooks focus on types of cooking (microwave, barbecue, baking.) '),(9,'Bài luận – Essays','An essay is a piece of writing which is often written from an author\'s personal point of view. Essays can consist of a number of elements, including: literary criticism, political manifestos, learned arguments, observations of daily life, recollections, and reflections of the author. The definition of an essay is vague, overlapping with those of an article and a short story. Almost all modern essays are written in prose, but works in verse have been dubbed essays.'),(10,'Lịch sử – History','History (from Greek historia, meaning \"inquiry, knowledge acquired by investigation\") is the discovery, collection, organization, and presentation of information about past events. History can also mean the period of time after writing was invented. Scholars who write about history are called historians. It is a field of research which uses a narrative to examine and analyse the sequence of events, and it sometimes attempts to investigate objectively the patterns of cause and effect that determine events. Historians debate the nature of history and its usefulness. This includes discussing the study of the discipline as an end in itself and as a way of providing \"perspective\" on the problems of the present.'),(11,'Phiêu lưu – Adventure','Adventure fiction is a genre of fiction in which an adventure, an exciting undertaking involving risk and physical danger, forms the main storyline. Adventure novels and short stories were popular subjects for American pulp magazines, which dominated American popular fiction between the Progressive Era and the 1950s. Adventure fiction often overlaps with other genres, notably war novels, crime novels, sea stories, spy stories, science fiction, fantasy. Not all books within these genres are adventures. Adventure fiction takes the setting and premise of these other genres, but the fast-paced plot of an adventure focuses on the actions of the hero within the setting.'),(12,'Fantasy','Fantasy is a genre that uses magic and other supernatural forms as a primary element of plot, theme, and/or setting. Fantasy is generally distinguished from science fiction and horror by the expectation that it steers clear of technological and macabre themes, respectively, though there is a great deal of overlap between the three (collectively known as speculative fiction or science fiction/fantasy)');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
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
