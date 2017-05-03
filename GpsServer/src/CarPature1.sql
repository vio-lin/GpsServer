/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.5.40 : Database - carfencing
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`carfencing` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `carfencing`;

/*Table structure for table `device_location_info` */

DROP TABLE IF EXISTS `device_location_info`;

CREATE TABLE `device_location_info` (
  `ID` int(32) NOT NULL AUTO_INCREMENT COMMENT '空间信息的头',
  `GPSSSTAT` varbinary(20) DEFAULT NULL COMMENT 'gps的状态',
  `LONGTITUDE` double DEFAULT NULL COMMENT '经度',
  `LATITUDE` double DEFAULT NULL COMMENT '纬度',
  `SPEED` float DEFAULT NULL COMMENT '速度',
  `ORIEN_STAT` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '航向状态',
  `MCC` int(11) DEFAULT NULL COMMENT '国家代号',
  `MNC` int(11) DEFAULT NULL COMMENT '移动网号码',
  `LAC` int(11) DEFAULT NULL COMMENT '位置区域码',
  `CELL_ID` int(11) DEFAULT NULL COMMENT '基站ID',
  `ACC` tinyint(1) DEFAULT NULL COMMENT 'ACC状态',
  `OPLOAD_MODE` varchar(4) COLLATE utf8_bin DEFAULT NULL COMMENT '数据上报模式',
  `REAL_NOT` tinyint(1) DEFAULT NULL COMMENT '是否实时数据',
  `DATE` datetime DEFAULT NULL COMMENT '插入的日期',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `device_location_info` */

insert  into `device_location_info`(`ID`,`GPSSSTAT`,`LONGTITUDE`,`LATITUDE`,`SPEED`,`ORIEN_STAT`,`MCC`,`MNC`,`LAC`,`CELL_ID`,`ACC`,`OPLOAD_MODE`,`REAL_NOT`,`DATE`) values (1,'asdasd',121.123546132,31.21645648,45141.5,'orie',4545,4878,418,558,1,'asda',0,'2017-05-03 19:02:53'),(2,'asdasd',121.123546132,31.21645648,45141.5,'orie',4545,4878,418,558,1,'asda',0,'2017-05-03 19:04:08');

/*Table structure for table `device_status` */

DROP TABLE IF EXISTS `device_status`;

CREATE TABLE `device_status` (
  `ID` int(8) NOT NULL AUTO_INCREMENT,
  `IMEI` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '设备的IMEI码',
  `BATTERY` float DEFAULT NULL COMMENT '设备电量',
  `GSM` int(4) DEFAULT NULL COMMENT 'GSM信号强度',
  `OILELECTIRC` tinyint(1) DEFAULT NULL COMMENT '汽车油电控制',
  `GPSSTATE` tinyint(1) DEFAULT NULL COMMENT 'GPS状态',
  `Charging` tinyint(1) DEFAULT NULL COMMENT '充电状态',
  `ACC` tinyint(1) DEFAULT NULL COMMENT 'ACC状态',
  `GUARD` tinyint(1) DEFAULT NULL COMMENT '设置防御0 1',
  `LANGUAGE` tinyint(1) DEFAULT NULL COMMENT '语言类型 中文0 英文1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `device_status` */

insert  into `device_status`(`ID`,`IMEI`,`BATTERY`,`GSM`,`OILELECTIRC`,`GPSSTATE`,`Charging`,`ACC`,`GUARD`,`LANGUAGE`) values (1,'481215842551',2.9,5,1,0,1,1,1,1),(2,'481215842551',2.9,5,1,0,1,1,1,1),(3,'481215842551',2.9,5,1,0,1,1,1,1),(4,'481215842551',2.9,5,1,0,1,1,1,1),(5,'481215842551',2.9,5,1,0,1,1,1,1);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `ID` int(32) NOT NULL AUTO_INCREMENT COMMENT '用户的Id',
  `NAME` varchar(20) DEFAULT NULL COMMENT '用户的名 20字符应付大多情况',
  `PLATENUMBER` varchar(10) DEFAULT NULL COMMENT '车牌号 字母加数字',
  `IMEI` varchar(15) DEFAULT NULL COMMENT '设备的IMIE码',
  PRIMARY KEY (`ID`),
  KEY `IMEI` (`IMEI`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`ID`,`NAME`,`PLATENUMBER`,`IMEI`) values (1,'sjhdjd','浙J888','lux');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
