-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: lms_platform
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `asset_type` int(11) DEFAULT NULL,
  `transcode_url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `lecture_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9lyeevh4v812hf13y51axfk6s` (`file_id`),
  KEY `FKlcm4ohrbjmvf3y2p7tgjhurmf` (`lecture_id`),
  CONSTRAINT `FK9lyeevh4v812hf13y51axfk6s` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
  CONSTRAINT `FKlcm4ohrbjmvf3y2p7tgjhurmf` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `alias` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `chapter_order` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `course_version_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7njm7khxfu7daert7tc5eo0p9` (`course_version_id`),
  CONSTRAINT `FK7njm7khxfu7daert7tc5eo0p9` FOREIGN KEY (`course_version_id`) REFERENCES `course_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chapter`
--

LOCK TABLES `chapter` WRITE;
/*!40000 ALTER TABLE `chapter` DISABLE KEYS */;
/*!40000 ALTER TABLE `chapter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `config_key` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `alias_name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `lastest_version_updated_at` datetime DEFAULT NULL,
  `published_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `release_course_version_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj18ul5unaxh6eic0glg2wd1xr` (`release_course_version_id`),
  CONSTRAINT `FKj18ul5unaxh6eic0glg2wd1xr` FOREIGN KEY (`release_course_version_id`) REFERENCES `course_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_version`
--

DROP TABLE IF EXISTS `course_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `background_img` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `benefit` text COLLATE utf8_bin,
  `long_des` text COLLATE utf8_bin,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `requirement` text COLLATE utf8_bin,
  `short_des` text COLLATE utf8_bin,
  `status` int(11) DEFAULT NULL,
  `target` text COLLATE utf8_bin,
  `thumnail_img` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6yglm1887hjjbrm60ll1eg2fv` (`course_id`),
  CONSTRAINT `FK6yglm1887hjjbrm60ll1eg2fv` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_version`
--

LOCK TABLES `course_version` WRITE;
/*!40000 ALTER TABLE `course_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `definition`
--

DROP TABLE IF EXISTS `definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `keyword` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  `short_des` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `definition`
--

LOCK TABLES `definition` WRITE;
/*!40000 ALTER TABLE `definition` DISABLE KEYS */;
/*!40000 ALTER TABLE `definition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `file_extension` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `file_type` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `object_key` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `folder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdfgd9qovcgebjry9mynttnijc` (`folder_id`),
  CONSTRAINT `FKdfgd9qovcgebjry9mynttnijc` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (1,NULL,NULL,NULL,NULL,NULL,NULL,'Đa',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parent_folder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK57g7veis1gp5wn3g0mp0x57pl` (`parent_folder_id`),
  CONSTRAINT `FK57g7veis1gp5wn3g0mp0x57pl` FOREIGN KEY (`parent_folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `lecture_order` int(11) DEFAULT NULL,
  `title` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `chapter_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk912p0rsmfkhgk3pyeuaxckvm` (`chapter_id`),
  CONSTRAINT `FKk912p0rsmfkhgk3pyeuaxckvm` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture`
--

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;
/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `answers` text COLLATE utf8_bin,
  `correct_answer` text COLLATE utf8_bin,
  `question_title` text COLLATE utf8_bin,
  `question_type` int(11) DEFAULT NULL,
  `quiz_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yh0c1qaxfwlcnwo9dms2txf` (`quiz_id`),
  CONSTRAINT `FKb0yh0c1qaxfwlcnwo9dms2txf` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  `pass_score` int(11) DEFAULT NULL,
  `quiz_duration` int(11) DEFAULT NULL,
  `quiz_order` int(11) DEFAULT NULL,
  `title` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `chapter_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKawdglurlxuavvtgtwg7h5y6v8` (`chapter_id`),
  CONSTRAINT `FKawdglurlxuavvtgtwg7h5y6v8` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `long_des` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `alias_name` text COLLATE utf8_bin,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parent_category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgbenhmddown3ra94x7a0u3w5y` (`parent_category_id`),
  CONSTRAINT `FKgbenhmddown3ra94x7a0u3w5y` FOREIGN KEY (`parent_category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_category_course_version`
--

DROP TABLE IF EXISTS `sub_category_course_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_category_course_version` (
  `course_version_id` bigint(20) NOT NULL,
  `subcategory_id` bigint(20) NOT NULL,
  KEY `FK4l2wamhfrbqkmmoskd1x07hej` (`subcategory_id`),
  KEY `FKet61623m8343d3lrar5xu1fub` (`course_version_id`),
  CONSTRAINT `FK4l2wamhfrbqkmmoskd1x07hej` FOREIGN KEY (`subcategory_id`) REFERENCES `sub_category` (`id`),
  CONSTRAINT `FKet61623m8343d3lrar5xu1fub` FOREIGN KEY (`course_version_id`) REFERENCES `course_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category_course_version`
--

LOCK TABLES `sub_category_course_version` WRITE;
/*!40000 ALTER TABLE `sub_category_course_version` DISABLE KEYS */;
/*!40000 ALTER TABLE `sub_category_course_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `academic_rank` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `function` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  `major` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `short_des` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `work_unit` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpb6g6pahj1mr2ijg92r7m1xlh` (`user_id`),
  CONSTRAINT `FKpb6g6pahj1mr2ijg92r7m1xlh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_course`
--

DROP TABLE IF EXISTS `teacher_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_course` (
  `teacher_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  KEY `FKp8bco6842vkqh13y4759ib7tk` (`course_id`),
  KEY `FKaleldsg7yww5as540ld8iwghe` (`teacher_id`),
  CONSTRAINT `FKaleldsg7yww5as540ld8iwghe` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FKp8bco6842vkqh13y4759ib7tk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_course`
--

LOCK TABLES `teacher_course` WRITE;
/*!40000 ALTER TABLE `teacher_course` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacher_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_activity_code`
--

DROP TABLE IF EXISTS `user_activity_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_activity_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_activity_code`
--

LOCK TABLES `user_activity_code` WRITE;
/*!40000 ALTER TABLE `user_activity_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_activity_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_activity_log`
--

DROP TABLE IF EXISTS `user_activity_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_activity_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `action` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `activity` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `activity_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `browser` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `controller` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ip_address` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `params` text COLLATE utf8_bin,
  `user_id` int(11) DEFAULT NULL,
  `activity_code_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5xky54s180gxknfipe3aprucf` (`activity_code_id`),
  CONSTRAINT `FK5xky54s180gxknfipe3aprucf` FOREIGN KEY (`activity_code_id`) REFERENCES `user_activity_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_activity_log`
--

LOCK TABLES `user_activity_log` WRITE;
/*!40000 ALTER TABLE `user_activity_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_activity_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `root_folder_id` bigint(20) DEFAULT NULL,
  `teacher_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK51ogjvg364lh10nwxrwu9uwgh` (`root_folder_id`),
  KEY `FKgwjttitp76ci22ay2ykuy4uvc` (`teacher_id`),
  CONSTRAINT `FK51ogjvg364lh10nwxrwu9uwgh` FOREIGN KEY (`root_folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FKgwjttitp76ci22ay2ykuy4uvc` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'lms_platform'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-28 11:34:43
