-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: lms_platform
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lecture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lecture_order` int(11) DEFAULT NULL,
  `title` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `chapter_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk912p0rsmfkhgk3pyeuaxckvm` (`chapter_id`),
  CONSTRAINT `FKk912p0rsmfkhgk3pyeuaxckvm` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_type` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `folder_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdfgd9qovcgebjry9mynttnijc` (`folder_id`),
  CONSTRAINT `FKdfgd9qovcgebjry9mynttnijc` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_type` int(11) DEFAULT NULL,
  `transcode_url` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  `lecture_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9lyeevh4v812hf13y51axfk6s` (`file_id`),
  KEY `FKlcm4ohrbjmvf3y2p7tgjhurmf` (`lecture_id`),
  CONSTRAINT `FK9lyeevh4v812hf13y51axfk6s` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
  CONSTRAINT `FKlcm4ohrbjmvf3y2p7tgjhurmf` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alias` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_order` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `course_version_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7njm7khxfu7daert7tc5eo0p9` (`course_version_id`),
  CONSTRAINT `FK7njm7khxfu7daert7tc5eo0p9` FOREIGN KEY (`course_version_id`) REFERENCES `course_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `config_key` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alias_name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `lastest_version_updated_at` datetime DEFAULT NULL,
  `published_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `release_course_version_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj18ul5unaxh6eic0glg2wd1xr` (`release_course_version_id`),
  CONSTRAINT `FKj18ul5unaxh6eic0glg2wd1xr` FOREIGN KEY (`release_course_version_id`) REFERENCES `course_version` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course_version`
--

DROP TABLE IF EXISTS `course_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `background_img` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `benefit` text COLLATE utf8_bin,
  `created_at` datetime DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `requirement` text COLLATE utf8_bin,
  `short_des` text COLLATE utf8_bin,
  `status` int(11) DEFAULT NULL,
  `target` text COLLATE utf8_bin,
  `thumnail_img` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6yglm1887hjjbrm60ll1eg2fv` (`course_id`),
  CONSTRAINT `FK6yglm1887hjjbrm60ll1eg2fv` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `definition`
--

DROP TABLE IF EXISTS `definition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `definition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  `short_des` text COLLATE utf8_bin,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parent_folder_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK57g7veis1gp5wn3g0mp0x57pl` (`parent_folder_id`),
  CONSTRAINT `FK57g7veis1gp5wn3g0mp0x57pl` FOREIGN KEY (`parent_folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answers` text COLLATE utf8_bin,
  `correct_answer` text COLLATE utf8_bin,
  `question_title` text COLLATE utf8_bin,
  `question_type` int(11) DEFAULT NULL,
  `quiz_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yh0c1qaxfwlcnwo9dms2txf` (`quiz_id`),
  CONSTRAINT `FKb0yh0c1qaxfwlcnwo9dms2txf` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quiz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `long_des` text COLLATE utf8_bin,
  `pass_score` int(11) DEFAULT NULL,
  `quiz_duration` int(11) DEFAULT NULL,
  `quiz_order` int(11) DEFAULT NULL,
  `title` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `chapter_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKawdglurlxuavvtgtwg7h5y6v8` (`chapter_id`),
  CONSTRAINT `FKawdglurlxuavvtgtwg7h5y6v8` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `long_des` text COLLATE utf8_bin,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `benefit` text COLLATE utf8_bin,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `requirement` text COLLATE utf8_bin,
  `target` text COLLATE utf8_bin,
  `parent_category_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKldve3olfmdfjvs3gaxfyyvwj` (`parent_category_id`),
  CONSTRAINT `FKldve3olfmdfjvs3gaxfyyvwj` FOREIGN KEY (`parent_cateogry_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sub_category_course_version`
--

DROP TABLE IF EXISTS `sub_category_course_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_category_course_version` (
  `subcategory_id` int(11) NOT NULL,
  `course_version_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  KEY `FKkfvb8dvqec4ne18t3fn8wi7ld` (`course_version_id`),
  KEY `FK4l2wamhfrbqkmmoskd1x07hej` (`subcategory_id`),
  CONSTRAINT `FK4l2wamhfrbqkmmoskd1x07hej` FOREIGN KEY (`subcategory_id`) REFERENCES `sub_category` (`id`),
  CONSTRAINT `FKkfvb8dvqec4ne18t3fn8wi7ld` FOREIGN KEY (`course_version_id`) REFERENCES `sub_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `academic_rank` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `function` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  `major` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `short_des` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `work_unit` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpb6g6pahj1mr2ijg92r7m1xlh` (`user_id`),
  CONSTRAINT `FKpb6g6pahj1mr2ijg92r7m1xlh` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teacher_course`
--

DROP TABLE IF EXISTS `teacher_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher_course` (
  `teacher_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  KEY `FKp8bco6842vkqh13y4759ib7tk` (`course_id`),
  KEY `FKaleldsg7yww5as540ld8iwghe` (`teacher_id`),
  CONSTRAINT `FKaleldsg7yww5as540ld8iwghe` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`),
  CONSTRAINT `FKp8bco6842vkqh13y4759ib7tk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `mobile` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `username` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_activity_code`
--

DROP TABLE IF EXISTS `user_activity_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_activity_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `long_des` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_activity_log`
--

DROP TABLE IF EXISTS `user_activity_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_activity_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `activity` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `activity_code` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `browser` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `controller` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `ip_address` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `note` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `params` text COLLATE utf8_bin,
  `updated_at` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `activity_code_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5xky54s180gxknfipe3aprucf` (`activity_code_id`),
  CONSTRAINT `FK5xky54s180gxknfipe3aprucf` FOREIGN KEY (`activity_code_id`) REFERENCES `user_activity_code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  KEY `FKak8topdb5d9ms6ml76er9vd3l` (`role_id`),
  KEY `FK2c25owjk4tax6fvm6yptbakvj` (`user_id`,`role_id`),
  CONSTRAINT `TO_ROLE_KF_31231313` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `TO_USER_FK_31231242` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `root_folder_id` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK51ogjvg364lh10nwxrwu9uwgh` (`root_folder_id`),
  KEY `FKgwjttitp76ci22ay2ykuy4uvc` (`teacher_id`),
  CONSTRAINT `FK51ogjvg364lh10nwxrwu9uwgh` FOREIGN KEY (`root_folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FKgwjttitp76ci22ay2ykuy4uvc` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

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

-- Dump completed on 2019-08-14 15:51:55
DROP TABLE IF EXISTS `oauth_client_details`;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove tinyint
);

DROP TABLE IF EXISTS `oauth_client_token`;
create table oauth_client_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);


DROP TABLE IF EXISTS `oauth_access_token`;
create table oauth_access_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BLOB,
  refresh_token VARCHAR(255)
);


DROP TABLE IF EXISTS `oauth_refresh_token`;
create table oauth_refresh_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication BLOB
);


DROP TABLE IF EXISTS `oauth_code`;
create table oauth_code (
  code VARCHAR(255), authentication BLOB
);

