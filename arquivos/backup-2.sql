-- MySQL dump 10.13  Distrib 8.0.30, for Linux (x86_64)
--
-- Host: localhost    Database: contabilidade-dev
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` int NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `analisar` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (2,'Saúde',_binary ''),(66,'Saldo Anterior',NULL),(68,'Salário',_binary '\0'),(73,'Cartão',_binary '\0'),(136,'Rendimento',_binary '\0'),(139,'Casa',_binary ''),(142,'Carro',_binary ''),(174,'Diversão',_binary ''),(175,'Alimentação',_binary ''),(176,'Pessoal',_binary ''),(177,'Educação',_binary ''),(293,'Supermercado',_binary '');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conta`
--

DROP TABLE IF EXISTS `conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conta` (
  `id` int NOT NULL,
  `banco` varchar(50) NOT NULL,
  `carga` varchar(255) DEFAULT NULL,
  `descricao` varchar(50) NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conta`
--

LOCK TABLES `conta` WRITE;
/*!40000 ALTER TABLE `conta` DISABLE KEYS */;
INSERT INTO `conta` VALUES (3,'Bradesco','BRADESCO','Bradesco','CC'),(4,'C6',NULL,'C6','CC'),(5,'C6','C6','Cartão C6','CARTAO'),(65,'Itaú',NULL,'Itaú','CC'),(190,'Carteira',NULL,'Carteira','CARTEIRA'),(192,'Sodexo',NULL,'Refeição','REFEICAO'),(193,'Sodexo',NULL,'Alimentação','ALIMENTACAO');
/*!40000 ALTER TABLE `conta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (300),(300),(300),(300);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lancamento`
--

DROP TABLE IF EXISTS `lancamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lancamento` (
  `id` int NOT NULL,
  `concluido` bit(1) DEFAULT NULL,
  `data` datetime(6) NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  `id_categoria` int DEFAULT NULL,
  `id_conta` int NOT NULL,
  `id_planilha` int NOT NULL,
  `fixo` bit(1) DEFAULT NULL,
  `numero_bradesco` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr651pitjvnxinfpcgbd9g71sf` (`id_categoria`),
  KEY `FKtdlpcnlxjl048mkkrxu92engo` (`id_conta`),
  KEY `FKi8mx6xn6npvgpuhsef253x7eo` (`id_planilha`),
  CONSTRAINT `FKi8mx6xn6npvgpuhsef253x7eo` FOREIGN KEY (`id_planilha`) REFERENCES `planilha` (`id`),
  CONSTRAINT `FKr651pitjvnxinfpcgbd9g71sf` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FKtdlpcnlxjl048mkkrxu92engo` FOREIGN KEY (`id_conta`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamento`
--

LOCK TABLES `lancamento` WRITE;
/*!40000 ALTER TABLE `lancamento` DISABLE KEYS */;
INSERT INTO `lancamento` VALUES (6,_binary '','2022-08-02 00:00:00.000000','Renato Pozzebon',-300.00,142,3,1,_binary '',NULL),(7,_binary '','2022-08-02 00:00:00.000000','Dest: Veronica Torino Lermen',-265.00,139,3,1,_binary '',NULL),(8,_binary '','2022-08-03 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',172.88,136,3,1,NULL,NULL),(9,_binary '','2022-08-04 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',23.16,136,3,1,NULL,NULL),(10,_binary '','2022-08-05 00:00:00.000000','Remet.coop Soma',10245.22,68,3,1,NULL,NULL),(11,_binary '','2022-08-05 00:00:00.000000','Damask',-68.75,174,3,1,NULL,NULL),(12,_binary '','2022-08-08 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',26.53,136,3,1,NULL,NULL),(13,_binary '','2022-08-08 00:00:00.000000','Distribuidora de Gas',-125.00,139,3,1,NULL,NULL),(14,_binary '','2022-08-10 00:00:00.000000','Fatura C6 Julho',-4050.82,73,3,1,_binary '',NULL),(15,_binary '','2022-08-10 00:00:00.000000','Dest.vicente Coda',-850.00,2,3,1,_binary '',NULL),(16,_binary '','2022-08-10 00:00:00.000000','Dest: Stefan Cardon',-303.00,2,3,1,_binary '',NULL),(17,_binary '','2022-08-10 00:00:00.000000','Bradesco C-claro S.a.',-213.61,139,3,1,_binary '',NULL),(18,_binary '','2022-08-26 00:00:00.000000','ok Park',-14.00,142,3,1,NULL,NULL),(19,_binary '','2022-08-30 00:00:00.000000','Secr. r. f. -. l. 2021/16',4057.10,136,3,1,NULL,NULL),(20,_binary '','2022-05-07 00:00:00.000000','Máquina de Lavar  - Parcela 4/12 ',-169.08,136,5,1,_binary '',NULL),(21,_binary '','2022-07-29 00:00:00.000000','IFOOD IFOOD ',-33.88,175,5,1,NULL,NULL),(22,_binary '','2022-07-30 00:00:00.000000','BUTEQUIM DA ESQUINA ',-43.00,174,5,1,NULL,NULL),(23,_binary '','2022-08-02 00:00:00.000000','AMAZON AWS SERVICOS BR ',-2.66,176,5,1,_binary '',NULL),(24,_binary '','2022-08-03 00:00:00.000000','BALDHEAD CB ',-60.50,174,5,1,NULL,NULL),(25,_binary '','2022-08-05 00:00:00.000000','FIRMA BAR ',-49.50,174,5,1,NULL,NULL),(26,_binary '','2022-08-07 00:00:00.000000','RTA BAR RESTAURANTE LT ',-62.70,174,5,1,NULL,NULL),(27,_binary '','2022-08-07 00:00:00.000000','QUENTINS ',-46.20,174,5,1,NULL,NULL),(28,_binary '','2022-08-09 00:00:00.000000','EL TONEL ',-77.50,174,5,1,NULL,NULL),(30,_binary '','2022-08-10 00:00:00.000000','LIBERTY BRAZIL PARC 3 ',-1166.25,142,5,1,_binary '',NULL),(31,_binary '','2022-08-10 00:00:00.000000','LYON PARK SQUARE ',-10.00,142,5,1,NULL,NULL),(32,_binary '','2022-08-10 00:00:00.000000','JAPESCA CIDADE BAIXA ',-33.90,175,5,1,NULL,NULL),(33,_binary '','2022-08-10 00:00:00.000000','OLDSCHOOL ',-35.00,176,5,1,NULL,NULL),(34,_binary '','2022-08-10 00:00:00.000000','INSTITUTO CULTURAL BR ',-21.00,176,5,1,NULL,NULL),(35,_binary '','2022-08-10 00:00:00.000000','A VIRGEM BAR E TATTOO ',-94.00,174,5,1,NULL,NULL),(36,_binary '','2022-08-11 00:00:00.000000','PRI BAKERY ',-48.00,174,5,1,NULL,NULL),(37,_binary '','2022-08-12 00:00:00.000000','UBER TRIP HELP UBER C ',-29.93,174,5,1,NULL,NULL),(38,_binary '','2022-08-12 00:00:00.000000','EL AGUANTE ',-53.00,174,5,1,NULL,NULL),(39,_binary '','2022-08-12 00:00:00.000000','PAG MIGUEL836 ',-24.20,174,5,1,NULL,NULL),(40,_binary '','2022-08-13 00:00:00.000000','UBER UBER TRIP ',-24.91,174,5,1,NULL,NULL),(41,_binary '','2022-08-13 00:00:00.000000','BAR DO NITO ',-87.70,174,5,1,NULL,NULL),(42,_binary '','2022-08-14 00:00:00.000000','LOS TRES COMERCIO DE A ',-82.28,174,5,1,NULL,NULL),(43,_binary '','2022-08-16 00:00:00.000000','PAG PLASTICOSPORTO ',-85.00,176,5,1,NULL,NULL),(44,_binary '','2022-08-16 00:00:00.000000','PAG JOSERICARDOCANDID ',-30.90,174,5,1,NULL,NULL),(45,_binary '','2022-08-16 00:00:00.000000','PAG PANVELFILIAL324 ',-99.63,2,5,1,NULL,NULL),(46,_binary '','2022-08-16 00:00:00.000000','LEVEDURA CERVEJAS ESP ',-52.00,174,5,1,NULL,NULL),(47,_binary '','2022-08-18 00:00:00.000000','PASTEL COM BORDA ',-62.50,174,5,1,NULL,NULL),(48,_binary '','2022-08-19 00:00:00.000000','SANTO POSTO ',-237.94,142,5,1,NULL,NULL),(49,_binary '','2022-08-19 00:00:00.000000','ESPONTANEO ',-46.00,174,5,1,NULL,NULL),(50,_binary '','2022-08-19 00:00:00.000000','FENIX BAR ',-20.00,174,5,1,NULL,NULL),(51,_binary '','2022-08-21 00:00:00.000000','PAG ALEXANDREMACHADO ',-20.00,174,5,1,NULL,NULL),(52,_binary '','2022-08-21 00:00:00.000000','ADOMA ',-20.00,174,5,1,NULL,NULL),(53,_binary '','2022-08-21 00:00:00.000000','PAG ATELIERDEDOCESART ',-18.00,174,5,1,NULL,NULL),(54,_binary '','2022-08-21 00:00:00.000000','PAG MICHELLEPETINELI ',-60.00,174,5,1,NULL,NULL),(55,_binary '','2022-08-24 00:00:00.000000','IFOOD IFOOD ',-44.99,175,5,1,NULL,NULL),(56,_binary '','2022-08-24 00:00:00.000000','BALDHEAD CB ',-33.00,174,5,1,NULL,NULL),(57,_binary '','2022-08-25 00:00:00.000000','DTP DRIVE TERCEIRA PER ',-24.70,175,5,1,NULL,NULL),(58,_binary '','2022-08-26 00:00:00.000000','BARRANQUINHO ',-190.30,174,5,1,NULL,NULL),(59,_binary '','2022-08-28 00:00:00.000000','FAMIGLIA FACIN ',-119.35,174,5,1,NULL,NULL),(60,_binary '','2022-08-29 00:00:00.000000','LYON PARK SQUARE ',-10.00,142,5,1,NULL,NULL),(61,_binary '','2022-08-29 00:00:00.000000','ARTEMIO GRITTI COMERCI ',-20.00,174,5,1,NULL,NULL),(62,_binary '','2022-11-28 00:00:00.000000','PP ALURA - Parcela 10/12 ',-63.75,177,5,1,_binary '',NULL),(63,_binary '','2022-07-18 00:00:00.000000','PAG CENTURIAINDUSTRIA - Parcela 2/3 ',-113.54,176,5,1,_binary '',NULL),(64,_binary '','2022-08-02 00:00:00.000000','EBW SPOTIFY ',-19.90,176,5,1,_binary '',NULL),(67,_binary '','2022-08-01 03:00:00.000000','Saldo Anterior',15503.87,66,65,1,NULL,NULL),(69,_binary '','2022-08-08 03:00:00.000000','Salário 1',5200.00,68,65,1,NULL,NULL),(70,_binary '','2022-08-25 03:00:00.000000','Salário 2',4421.21,68,65,1,NULL,NULL),(71,_binary '','2022-08-01 03:00:00.000000','Saldo Anterior',32181.17,66,3,1,NULL,NULL),(72,_binary '','2022-08-01 03:00:00.000000','Saldo Anterior',124.11,66,4,1,NULL,NULL),(100,_binary '','2022-09-02 00:00:00.000000','Tomograf Radiolog',-205.00,2,3,74,NULL,NULL),(101,_binary '','2022-09-02 00:00:00.000000','Posto Paineira',-41.39,174,3,74,NULL,NULL),(102,_binary '','2022-09-02 00:00:00.000000','Bradesco C-detran Gade-e/rs',-267.79,176,3,74,NULL,NULL),(103,_binary '','2022-09-05 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',184.49,136,3,74,NULL,NULL),(104,_binary '','2022-09-05 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',22.68,136,3,74,NULL,NULL),(105,_binary '','2022-09-05 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',57.38,136,3,74,NULL,NULL),(106,_binary '','2022-09-05 00:00:00.000000','Pag*panvelfilial397',-7.56,2,3,74,NULL,NULL),(107,_binary '','2022-09-06 00:00:00.000000','Atelier de Massas',-104.34,174,3,74,NULL,NULL),(108,_binary '','2022-09-09 00:00:00.000000','Renato Pozzebon',-300.00,142,3,74,NULL,NULL),(109,_binary '','2022-09-09 00:00:00.000000','Dest.vicente Coda',-680.00,2,3,74,NULL,NULL),(110,_binary '','2022-09-09 00:00:00.000000','Des: Stefan Cardon 09/09',-303.00,2,3,74,_binary '',NULL),(111,_binary '','2022-09-09 00:00:00.000000','Des: Veronica Torino Lerme 09/09',-140.00,139,3,74,_binary '',NULL),(112,_binary '','2022-09-09 00:00:00.000000','Bradesco C-claro S.a.',-223.53,139,3,74,_binary '',NULL),(113,_binary '','2022-09-12 00:00:00.000000','Fatura C6 Agosto',-3646.69,73,3,74,_binary '',NULL),(114,_binary '','2022-09-12 00:00:00.000000','Pag*panvelfilial324',-21.63,2,3,74,NULL,NULL),(115,_binary '','2022-09-13 00:00:00.000000','Posto Pegasus',-265.72,142,3,74,NULL,NULL),(116,_binary '','2022-09-13 00:00:00.000000','Oldschool',-31.90,176,3,74,NULL,NULL),(117,_binary '','2022-09-13 00:00:00.000000','Mekaurio',-74.80,174,3,74,NULL,NULL),(118,_binary '','2022-09-14 00:00:00.000000','Pag*garagemalbertobi',-13.00,142,3,74,NULL,NULL),(119,_binary '','2022-09-15 00:00:00.000000','Cesta Exclusive',-68.80,174,3,74,NULL,NULL),(120,_binary '','2022-09-15 00:00:00.000000','el Tonel',-77.50,174,3,74,NULL,NULL),(121,_binary '','2022-09-19 00:00:00.000000','Parque Dos Peixes',-79.00,174,3,74,NULL,NULL),(122,_binary '','2022-09-19 00:00:00.000000','Saque 24H',-500.00,176,3,74,NULL,NULL),(123,_binary '','2022-09-22 00:00:00.000000','Pedro Churrasco Breno',-35.00,174,3,74,NULL,NULL),(124,_binary '','2022-09-29 00:00:00.000000','Areatec - Tecnolo',-4.60,142,3,74,NULL,NULL),(125,_binary '','2022-09-29 00:00:00.000000','Pag*panvelfilial324',-116.59,2,3,74,NULL,NULL),(126,_binary '','2022-09-29 00:00:00.000000','Eduardo Extração dente',-1500.00,2,3,74,NULL,NULL),(127,_binary '','2022-09-01 03:00:00.000000','Saldo Anterior',40515.88,66,3,74,NULL,NULL),(128,_binary '','2022-09-01 03:00:00.000000','Saldo Anterior',25125.08,66,65,74,NULL,NULL),(129,_binary '','2022-09-10 03:00:00.000000','Salário 1',5200.00,68,65,74,NULL,NULL),(130,_binary '','2022-09-25 03:00:00.000000','Salário 2',4421.21,68,65,74,NULL,NULL),(131,_binary '','2022-09-01 03:00:00.000000','Saldo Anterior',124.11,66,4,74,NULL,NULL),(133,_binary '','2022-10-01 03:00:00.000000','Saldo Anterior',34682.54,66,65,132,_binary '',NULL),(135,_binary '','2022-10-01 03:00:00.000000','Saldo Anterior',124.11,66,4,132,_binary '',NULL),(148,_binary '','2022-05-07 00:00:00.000000','Máquina Lavar Roupa 5/12 ',-169.08,139,5,74,_binary '',NULL),(149,_binary '','2022-08-30 00:00:00.000000','IFOOD IFOOD ',-32.88,175,5,74,NULL,NULL),(150,_binary '','2022-09-02 00:00:00.000000','AMAZON AWS SERVICOS BR ',-2.55,176,5,74,_binary '',NULL),(151,_binary '','2022-09-10 00:00:00.000000','LIBERTY BRAZIL PARC 4 ',-1166.27,142,5,74,_binary '',NULL),(152,_binary '','2022-09-10 00:00:00.000000','IFOOD IFOOD ',-32.88,175,5,74,NULL,NULL),(153,_binary '','2022-09-11 00:00:00.000000','BRECHO DO FUTEBOL CERV ',-52.80,174,5,74,NULL,NULL),(154,_binary '','2022-09-11 00:00:00.000000','RTA BAR RESTAURANTE LT ',-72.00,174,5,74,NULL,NULL),(156,_binary '','2022-09-17 00:00:00.000000','DOM CAMILO PRODUTOS CO ',-88.00,174,5,74,NULL,NULL),(157,_binary '','2022-09-17 00:00:00.000000','PAG MARIPOUSADA ',-32.00,174,5,74,NULL,NULL),(158,_binary '','2022-09-19 00:00:00.000000','PAG CELIOBRANCALIONE ',-18.50,174,5,74,NULL,NULL),(159,_binary '','2022-09-19 00:00:00.000000','POSTO TERRA NOSTRA ',-200.00,142,5,74,NULL,NULL),(160,_binary '','2022-09-19 00:00:00.000000','FLORES KITCHEN ',-12.00,174,5,74,NULL,NULL),(161,_binary '','2022-09-20 00:00:00.000000','SERGIO LUIZ CATANI ',-39.00,174,5,74,NULL,NULL),(162,_binary '','2022-09-21 00:00:00.000000','GARGULA ',-33.00,174,5,74,NULL,NULL),(163,_binary '','2022-09-22 00:00:00.000000','PUC CAMPUS FACULDADE ',-13.00,142,5,74,NULL,NULL),(164,_binary '','2022-09-22 00:00:00.000000','LANTICA PIETRA ',-122.20,174,5,74,NULL,NULL),(165,_binary '','2022-09-23 00:00:00.000000','ESPACO LED POA ',-14.98,176,5,74,NULL,NULL),(166,_binary '','2022-09-23 00:00:00.000000','MULTIPLAN ADMINISTRADO ',-15.00,174,5,74,NULL,NULL),(167,_binary '','2022-09-26 00:00:00.000000','KALUNGA IGUATEMI PORTO - Parcela 1/3 ',-97.78,176,5,74,_binary '',NULL),(168,_binary '','2022-09-26 00:00:00.000000','ESTACIONAMENTO IGUATEM ',-15.00,142,5,74,NULL,NULL),(169,_binary '','2022-09-28 00:00:00.000000','TANGA ',-83.60,174,5,74,NULL,NULL),(170,_binary '','2022-11-28 00:00:00.000000','PP ALURA - Parcela 11/12 ',-63.75,177,5,74,_binary '',NULL),(171,_binary '','2022-07-18 00:00:00.000000','PAG CENTURIAINDUSTRIA - Parcela 3/3 ',-113.54,176,5,74,_binary '',NULL),(172,_binary '','2022-09-02 00:00:00.000000','EBW SPOTIFY ',-19.90,176,5,74,_binary '',NULL),(173,_binary '','2022-09-25 00:00:00.000000','IPLACE COM - Parcela 1/10 ',-36.61,176,5,74,_binary '',NULL),(179,_binary '','2022-10-05 18:01:16.847000','Rendimento',66.40,136,65,132,NULL,NULL),(183,_binary '\0','2022-10-06 03:00:00.000000','Gasolina',-254.62,142,5,132,_binary '',NULL),(184,_binary '\0','2022-10-06 15:39:19.492000','Farmácia',-27.93,2,5,132,NULL,NULL),(185,_binary '\0','2022-10-02 03:00:00.000000','Amazon',-2.59,176,5,132,_binary '',NULL),(186,_binary '\0','2022-10-02 03:00:00.000000','Spotify',-19.90,176,5,132,_binary '',NULL),(187,_binary '\0','2022-10-01 03:00:00.000000','Kalunga Teclado 2/3',-97.76,176,5,132,_binary '',NULL),(188,_binary '\0','2022-10-01 03:00:00.000000','Máquina Lavar 6/12',-169.08,139,5,132,_binary '',NULL),(189,_binary '\0','2022-10-01 03:00:00.000000','Caixa JBL 2/10',-36.55,176,5,132,_binary '',NULL),(191,_binary '','2022-10-01 03:00:00.000000',' Saldo Anterior',500.00,66,190,132,NULL,NULL),(194,_binary '','2022-10-06 16:07:49.480000','Saldo Atual',70.77,68,192,132,NULL,NULL),(195,_binary '','2022-10-06 16:11:03.550000','Saldo Atual',1212.59,68,193,132,NULL,NULL),(196,_binary '','2022-10-11 03:00:00.000000','Rescisão contrato Eldorado',2295.97,68,65,132,NULL,NULL),(210,_binary '','2022-10-17 12:10:20.734000','Rendimento',26.97,136,65,132,NULL,NULL),(211,_binary '','2022-10-16 03:00:00.000000','Aeroporto chegada Vitória ',-40.00,174,190,132,NULL,NULL),(212,_binary '','2022-10-10 03:00:00.000000','Persianas',-150.00,139,190,132,NULL,NULL),(263,_binary '','2022-10-03 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',24.14,136,3,132,NULL,'0106814'),(264,_binary '','2022-10-03 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',153.84,136,3,132,NULL,'0306152'),(265,_binary '','2022-10-04 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',20.74,136,3,132,NULL,'0406430'),(266,_binary '','2022-10-04 00:00:00.000000','Renato Pozzebon',-300.00,142,3,132,_binary '','1194800'),(267,_binary '','2022-10-04 00:00:00.000000','Dest.vicente Coda',-680.00,2,3,132,_binary '','3409456'),(268,_binary '','2022-10-04 00:00:00.000000','Veronica Torino Lerme 04/10',-377.50,139,3,132,_binary '','1704348'),(269,_binary '','2022-10-05 00:00:00.000000','Poup Facil-depos a Partir 4/5/12',2.01,136,3,132,NULL,'0506809'),(270,_binary '','2022-10-05 00:00:00.000000','Des: Stefan Cardon 05/10',-303.00,2,3,132,_binary '','1507220'),(271,_binary '','2022-10-05 00:00:00.000000','Bradesco C-claro S.a.',-217.04,139,3,132,_binary '','6960028'),(272,_binary '','2022-10-06 00:00:00.000000','Puc Campus Faculd',-13.00,142,3,132,NULL,'0060094'),(273,_binary '','2022-10-06 00:00:00.000000','Pag*migra',-82.50,174,3,132,NULL,'0997885'),(274,_binary '','2022-10-07 00:00:00.000000','Areatec - Tecnolo',-2.30,142,3,132,NULL,'0004340'),(275,_binary '','2022-10-10 00:00:00.000000','c6',-2546.32,73,3,132,NULL,'0000057'),(276,_binary '','2022-10-10 00:00:00.000000','Vintage Craft Beer',-30.00,174,3,132,NULL,'0502089'),(277,_binary '','2022-10-10 00:00:00.000000','Persiana',-200.00,139,3,132,NULL,'1054105'),(278,_binary '','2022-10-11 00:00:00.000000','Backup',-49.00,176,3,132,NULL,'0003597'),(279,_binary '','2022-10-11 00:00:00.000000','rs Farma fl 02',-86.54,2,3,132,NULL,'0036363'),(280,_binary '','2022-10-11 00:00:00.000000','Pag*panvelfilial45',-31.84,2,3,132,NULL,'0137037'),(281,_binary '','2022-10-11 00:00:00.000000','Oldschool',-35.00,176,3,132,_binary '','0180875'),(282,_binary '','2022-10-11 00:00:00.000000','Estacionamento do',-13.00,142,3,132,NULL,'0428554'),(283,_binary '','2022-10-13 00:00:00.000000','Mangalo',-28.00,174,3,132,NULL,'0003096'),(284,_binary '','2022-10-13 00:00:00.000000','Quiosque Tropical',-24.00,174,3,132,NULL,'0224481'),(285,_binary '','2022-10-13 00:00:00.000000','Barzito',-201.97,174,3,132,NULL,'0470302'),(286,_binary '','2022-10-13 00:00:00.000000','Trindade Restaurante',-126.17,174,3,132,NULL,'0539608'),(287,_binary '','2022-10-14 00:00:00.000000','Mercadopago*emersond',-10.00,176,3,132,NULL,'0052280'),(288,_binary '','2022-10-17 00:00:00.000000','Ponta de Areia',-142.50,174,3,132,NULL,'0049973'),(289,_binary '','2022-10-17 00:00:00.000000','Ponta de Areia',-103.00,174,3,132,NULL,'0049988'),(290,_binary '','2022-10-18 00:00:00.000000','Cesta Exclusive',-68.80,174,3,132,NULL,'0031022'),(291,_binary '','2022-10-19 00:00:00.000000','Baldhead cb',-54.34,174,3,132,NULL,'0413612'),(292,_binary '','2022-10-01 03:00:00.000000','Saldo Anterior',32072.59,66,3,132,_binary '',NULL),(294,_binary '','2022-10-20 03:00:00.000000','Zaffari Lima',-478.78,293,193,132,_binary '',NULL),(295,_binary '','2022-10-22 03:00:00.000000','Churras Cris',-101.89,174,193,132,NULL,NULL),(296,_binary '','2022-10-21 03:00:00.000000','Gasolina Caxias',-195.70,142,3,132,_binary '',NULL),(297,_binary '','2022-10-21 03:00:00.000000','Almoço Caxias',-28.00,175,3,132,NULL,NULL),(298,_binary '','2022-10-21 03:00:00.000000','Zona Azul Estacionamento',-20.00,142,3,132,NULL,NULL),(299,_binary '','2022-10-21 03:00:00.000000','Caxias Mercopar',-36.00,176,190,132,NULL,NULL);
/*!40000 ALTER TABLE `lancamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planilha`
--

DROP TABLE IF EXISTS `planilha`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planilha` (
  `id` int NOT NULL,
  `ano` smallint NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `mes` smallint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planilha`
--

LOCK TABLES `planilha` WRITE;
/*!40000 ALTER TABLE `planilha` DISABLE KEYS */;
INSERT INTO `planilha` VALUES (1,2022,'Agosto',8),(74,2022,'Setembro',9),(132,2022,'Outubro',10),(262,2023,'Janeiro',1);
/*!40000 ALTER TABLE `planilha` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-24 18:23:45
