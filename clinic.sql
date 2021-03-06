use snap_temp;
-- MySQL dump 10.13  Distrib 5.6.19, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: clinic
-- ------------------------------------------------------
-- Server version	5.6.19-1~exp1ubuntu2

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
-- Table structure for table `login_detail`
--

DROP TABLE IF EXISTS `login_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_detail`
--

LOCK TABLES `login_detail` WRITE;
/*!40000 ALTER TABLE `login_detail` DISABLE KEYS */;
INSERT INTO `login_detail` VALUES (1,'reception','1234','REC'),(2,'doctor','1234','DOC'),(3,'medi','1234','MED'),(4,'admin','admin','ADM');
/*!40000 ALTER TABLE `login_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `landline` varchar(45) DEFAULT NULL,
  `dependent` varchar(60) DEFAULT NULL,
  `birth_year` int(10) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `reffered_by` varchar(50) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `occupation` varchar(40) DEFAULT NULL,
  `marital_status` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'Nakul','kumar','9999999999','','',0,'m','','',NULL,NULL,NULL),(2,'aashish','man','888888888',NULL,'dad',28,'m','jahangir','arti',NULL,NULL,NULL),(3,'imam','hussain','777777777',NULL,'saumya',23,'m','noida','alex',NULL,NULL,NULL),(4,'akshit','gupta','6666666666',NULL,'vivek',24,'m','delhi','deepan',NULL,NULL,NULL),(15,'abcd','xyz','','','',23,NULL,NULL,NULL,NULL,NULL,NULL),(61,'lknlk','lkjnnkk','','','',NULL,'m','','',45,'',NULL),(62,'hi','bye','','','',NULL,'f','','',25,'',NULL),(63,'super','man','','','',NULL,'m','','',26,'',NULL);
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_history`
--

DROP TABLE IF EXISTS `patient_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `dateofvisit` datetime NOT NULL,
  `purposeofvisit` longtext,
  `chiefcomplaints` longtext,
  `mentalsymptoms` longtext,
  `physicalsymptoms` longtext,
  `investigation` longtext,
  `familyhistory` longtext,
  `pasthistory` longtext,
  `thermal` longtext,
  `desire` longtext,
  `aversion` longtext,
  PRIMARY KEY (`id`),
  KEY `fk_patient` (`patient_id`),
  CONSTRAINT `fk_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_history`
--

LOCK TABLES `patient_history` WRITE;
/*!40000 ALTER TABLE `patient_history` DISABLE KEYS */;
INSERT INTO `patient_history` VALUES (1,2,'2015-07-19 17:53:08','Regular checkup','crazy guy','mentally unstable','physically strong','cid investigation in progress','family is present, history is boring','past=history what is pasthistory?','thermally radioactive and inflammable','latin sensual desires','what is aversion?use easy words');
/*!40000 ALTER TABLE `patient_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_queue`
--

DROP TABLE IF EXISTS `patient_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_queue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) NOT NULL,
  `entry_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_patientid` (`patient_id`),
  CONSTRAINT `fk_patientid` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_queue`
--

LOCK TABLES `patient_queue` WRITE;
/*!40000 ALTER TABLE `patient_queue` DISABLE KEYS */;
INSERT INTO `patient_queue` VALUES (1,2,'2015-07-15'),(2,63,'2015-07-15');
/*!40000 ALTER TABLE `patient_queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `charges`;
CREATE TABLE `snap_temp`.`charges` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(15) NULL,
  `fixed_charges` INT NULL,
  `consultation_charges` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
  ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into charges(code,fixed_charges,consultation_charges) values('*',0,0);
insert into charges(code,fixed_charges,consultation_charges) values('A',50,10);
insert into charges(code,fixed_charges,consultation_charges) values('B',50,20);
insert into charges(code,fixed_charges,consultation_charges) values('C',50,30);
insert into charges(code,fixed_charges,consultation_charges) values('D',50,40);
insert into charges(code,fixed_charges,consultation_charges) values('E',50,50);
insert into charges(code,fixed_charges,consultation_charges) values('F',50,60);


DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
/*CREATE TABLE `prescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `medicines` longtext,
  `entry_time` date DEFAULT NULL,
  `charges` int(5) NOT NULL,
  `followup_remark` longtext,
  `revisit_date` date DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_patient_id` (`patient_id`),
  CONSTRAINT `fk_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;*/
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE snap_temp.`prescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `medicines` longtext,
  `entry_time` date DEFAULT NULL,
  `charges_code` varchar(5) NOT NULL,
  `followup_remark` longtext,
  `revisit_date` date DEFAULT NULL,
  `patient_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_patient_id` (`patient_id`),
  KEY `fk_charges_code` (`charges_code`),
  CONSTRAINT `fk_patient_id` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (1,'Combiflame','2015-06-26','A','try again','2015-06-26',1),(2,'Crocin','2015-06-26','B','try again','2015-06-26',2),(3,'Disprin','2015-06-25','B','try again','2015-06-26',2),(4,'DCold','2015-06-25','*','try again','2015-06-26',1),(6,'Durex','2015-07-25','C','drink milk','2015-07-25',2),(7,'Endura mass','2015-07-25','D','eat eggs','2015-07-25',2),(8,'Vicks','2015-07-25','D','vicks ki goli lo kichkich door karo,khao pio jam k','2015-07-25',2),(9,'Cheston 100','2015-07-25','A','Don\'t eat street food, only haldirams :P','2015-07-25',2);
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_credential`
--

DROP TABLE IF EXISTS `user_credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_credential` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `dependent` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `landline` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `reffered_by` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_credential`
--

LOCK TABLES `user_credential` WRITE;
/*!40000 ALTER TABLE `user_credential` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_credential` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-25 13:02:21
