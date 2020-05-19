/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.18 : Database - trade
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`trade` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `trade`;

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论的id',
  `reviewer_id` int(11) unsigned NOT NULL COMMENT '评论人的id',
  `production_id` int(11) unsigned DEFAULT NULL COMMENT '被评论的商品的id',
  `review_time` timestamp NULL DEFAULT NULL COMMENT '评论时间',
  `content` text COMMENT '评论内容',
  PRIMARY KEY (`id`),
  KEY `reviewer_id` (`reviewer_id`),
  KEY `production_id` (`production_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`production_id`) REFERENCES `production` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL COMMENT '信息id',
  `text` text COMMENT '信息内容',
  `sender_id` int(11) DEFAULT NULL COMMENT '发送人的id',
  `receiver_id` int(11) DEFAULT NULL COMMENT '接收人的id',
  `type` int(11) DEFAULT NULL COMMENT '是否已被接收',
  `time` timestamp NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `message` */

/*Table structure for table `production` */

DROP TABLE IF EXISTS `production`;

CREATE TABLE `production` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `price` int(11) NOT NULL COMMENT '价格或者拍卖最初价格',
  `introduction` text COMMENT '商品介绍',
  `producer_id` int(10) unsigned NOT NULL COMMENT '发布者id',
  `produce_time` timestamp NOT NULL COMMENT '发布时间',
  `bought` tinyint(1) DEFAULT NULL COMMENT '是否已被购买',
  `auction` tinyint(1) DEFAULT NULL COMMENT '是否是拍卖商品',
  `auction_max_price` int(11) DEFAULT NULL COMMENT '拍卖最高价格',
  `max_price_user_id` int(11) DEFAULT NULL COMMENT '出价最高的人',
  `image1` text COMMENT '商品图片1',
  `image2` text COMMENT '商品图片2',
  PRIMARY KEY (`id`),
  KEY `producer_id` (`producer_id`),
  CONSTRAINT `production_ibfk_1` FOREIGN KEY (`producer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `production` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `school_number` varchar(12) DEFAULT NULL COMMENT '学号',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `register_time` timestamp NOT NULL COMMENT '注册时间',
  `image` text COMMENT '用户头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
