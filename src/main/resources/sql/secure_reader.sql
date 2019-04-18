/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1_mysql
Source Server Version : 50721
Source Host           : 127.0.0.1:3306
Source Database       : testdb

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-04-18 11:54:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for secure_reader
-- ----------------------------
DROP TABLE IF EXISTS `secure_reader`;
CREATE TABLE `secure_reader` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of secure_reader
-- ----------------------------
INSERT INTO `secure_reader` VALUES ('1', 'czy', 'claire', '123456');
INSERT INTO `secure_reader` VALUES ('2', 'hh', 'user', 'pass');
INSERT INTO `secure_reader` VALUES ('3', 'kk', 'kuser', '111111');
