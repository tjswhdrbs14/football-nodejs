--
-- Table structure for table `player`
--
 
 
CREATE TABLE player (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `matchNum` bigint(200) DEFAULT '0',
  `goal` int(8) DEFAULT '0',
  `assist` int(8) DEFAULT '0',
  `MOM` int(6) DEFAULT '0', 
  PRIMARY KEY (`id`)
);
 
--
-- Dumping data for table `player`
--
 
INSERT INTO `player` VALUES (17,'seon', 5, 1, 3, 1);
INSERT INTO `player` VALUES (16,'park', 3, 1, 0, 1);
INSERT INTO `player` VALUES (19,'kim', 4, 2, 1, 0);
INSERT INTO `player` VALUES (2,'lee', 5, 0, 1, 0);
INSERT INTO `player` VALUES (3,'A.Cole', 3, 0, 1, 0);
INSERT INTO `player` VALUES (77,'choi', 5, 3, 0, 2);
INSERT INTO `player` VALUES (4,'Fabregas', 2, 0, 1, 0);
INSERT INTO `player` VALUES (13,'park', 2, 0, 0, 0);
INSERT INTO `player` VALUES (10,'jang', 5, 1, 0, 0);
INSERT INTO `player` VALUES (9,'ahn', 5, 1, 0, 1);
INSERT INTO `player` VALUES (27,'song', 3, 0, 0, 0);
INSERT INTO `player` VALUES (416,'kim', 5, 0, 0, 0);
INSERT INTO `player` VALUES (5,'kang', 5, 0, 0, 0);
 
--
-- Table structure for table `match`
--
 
CREATE TABLE `match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `score` int(5) DEFAULT '0',
  `lose` int(5) DEFAULT '0',
  `place` varchar(30) DEFAULT NULL,
  `win` tinyint(1) DEFAULT '3',
  PRIMARY KEY (`id`)
);
 
--
-- Dumping data for table `match`
--
 
INSERT INTO `match` VALUES (1,'가온FC','2019-09-01 19:00:00', 2, 0, NULL, 0);
INSERT INTO `match` VALUES (2,'토마FC','2019-09-13 19:00:00', 2, 2, '오정구장', 1);
INSERT INTO `match` VALUES (3,'마무리FC','2019-09-21 21:00:00', 2, 3, '부천체육관', 2);
INSERT INTO `match` VALUES (4,'닛시FC','2019-10-06 07:00:00', 0, 0, '삼산체육관', 3);
INSERT INTO `match` VALUES (5,'T-LAD','2019-10-13 20:00:00', 0, 0, '신트리공원', 3);
INSERT INTO `match` VALUES (6,'JUST FC','2019-11-19 21:00:00', 0, 0, '백운공원', 3);
INSERT INTO `match` VALUES (7,'유니온 FC','2019-11-23 19:00:00', 2, 0, '옥길공원', 0);
INSERT INTO `match` VALUES (8,'JBSC','2019-11-30 20:00:00', 1, 4, '원미구장', 2);