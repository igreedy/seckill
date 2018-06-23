/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50714
Source Host           : 192.168.192.132:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-06-22 18:24:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `seckill`
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '秒杀开启时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idex_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill` VALUES ('1000', '1000元秒杀iphone7', '100', '2017-05-01 00:00:00', '2017-05-02 00:00:00', '2018-06-22 13:58:19');
INSERT INTO `seckill` VALUES ('1001', '1000元秒杀小米6', '200', '2017-05-01 00:00:00', '2017-05-02 00:00:00', '2018-06-22 13:58:19');
INSERT INTO `seckill` VALUES ('1002', '1000元秒杀荣耀7', '300', '2017-05-01 00:00:00', '2017-05-02 00:00:00', '2018-06-22 13:58:19');
INSERT INTO `seckill` VALUES ('1003', '1000元秒杀ipad4', '400', '2017-05-01 00:00:00', '2017-05-02 00:00:00', '2018-06-22 13:58:19');
INSERT INTO `seckill` VALUES ('1004', '1元秒杀iphone9', '98', '2018-06-22 18:13:30', '2018-09-02 00:00:00', '2018-06-22 13:58:19');
