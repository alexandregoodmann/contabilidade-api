-- Adminer 4.8.1 MySQL 8.0.30 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE DATABASE `contabilidade-dev` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `contabilidade-dev`;

CREATE TABLE `categoria` (
  `id` int NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `analisar` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `categoria` (`id`, `descricao`, `analisar`) VALUES
(2,	'Saúde',	CONV('1', 2, 10) + 0),
(66,	'Saldo Anterior',	NULL),
(68,	'Salário',	NULL),
(73,	'Cartão',	NULL),
(136,	'Rendimento',	NULL),
(139,	'Casa',	CONV('1', 2, 10) + 0),
(142,	'Carro',	CONV('1', 2, 10) + 0),
(174,	'Diversão',	CONV('1', 2, 10) + 0),
(175,	'Alimentação',	CONV('1', 2, 10) + 0),
(176,	'Pessoal',	CONV('1', 2, 10) + 0),
(177,	'Educação',	CONV('1', 2, 10) + 0);

CREATE TABLE `conta` (
  `id` int NOT NULL,
  `banco` varchar(50) NOT NULL,
  `carga` varchar(255) DEFAULT NULL,
  `descricao` varchar(50) NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `conta` (`id`, `banco`, `carga`, `descricao`, `tipo`) VALUES
(3,	'Bradesco',	'BRADESCO',	'Bradesco',	'CC'),
(4,	'C6',	NULL,	'C6',	'CC'),
(5,	'C6',	'C6',	'Cartão C6',	'CARTAO'),
(65,	'Itaú',	NULL,	'Itaú',	'CC');

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(178),
(178),
(178),
(178);

CREATE TABLE `lancamento` (
  `id` int NOT NULL,
  `concluido` bit(1) DEFAULT NULL,
  `data` datetime(6) NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `valor` decimal(19,2) NOT NULL,
  `id_categoria` int DEFAULT NULL,
  `id_conta` int NOT NULL,
  `id_planilha` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr651pitjvnxinfpcgbd9g71sf` (`id_categoria`),
  KEY `FKtdlpcnlxjl048mkkrxu92engo` (`id_conta`),
  KEY `FKi8mx6xn6npvgpuhsef253x7eo` (`id_planilha`),
  CONSTRAINT `FKi8mx6xn6npvgpuhsef253x7eo` FOREIGN KEY (`id_planilha`) REFERENCES `planilha` (`id`),
  CONSTRAINT `FKr651pitjvnxinfpcgbd9g71sf` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id`),
  CONSTRAINT `FKtdlpcnlxjl048mkkrxu92engo` FOREIGN KEY (`id_conta`) REFERENCES `conta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `lancamento` (`id`, `concluido`, `data`, `descricao`, `valor`, `id_categoria`, `id_conta`, `id_planilha`) VALUES
(6,	CONV('1', 2, 10) + 0,	'2022-08-02 00:00:00.000000',	'Renato Pozzebon',	-300.00,	142,	3,	1),
(7,	CONV('1', 2, 10) + 0,	'2022-08-02 00:00:00.000000',	'Dest: Veronica Torino Lermen',	-265.00,	139,	3,	1),
(8,	CONV('1', 2, 10) + 0,	'2022-08-03 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	172.88,	136,	3,	1),
(9,	CONV('1', 2, 10) + 0,	'2022-08-04 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	23.16,	136,	3,	1),
(10,	CONV('1', 2, 10) + 0,	'2022-08-05 00:00:00.000000',	'Remet.coop Soma',	10245.22,	68,	3,	1),
(11,	CONV('1', 2, 10) + 0,	'2022-08-05 00:00:00.000000',	'Damask',	-68.75,	174,	3,	1),
(12,	CONV('1', 2, 10) + 0,	'2022-08-08 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	26.53,	136,	3,	1),
(13,	CONV('1', 2, 10) + 0,	'2022-08-08 00:00:00.000000',	'Distribuidora de Gas',	-125.00,	139,	3,	1),
(14,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Fatura C6 Julho',	-4050.82,	73,	3,	1),
(15,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Dest.vicente Coda',	-850.00,	2,	3,	1),
(16,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Dest: Stefan Cardon',	-303.00,	2,	3,	1),
(17,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Bradesco C-claro S.a.',	-213.61,	139,	3,	1),
(18,	CONV('1', 2, 10) + 0,	'2022-08-26 00:00:00.000000',	'ok Park',	-14.00,	142,	3,	1),
(19,	CONV('1', 2, 10) + 0,	'2022-08-30 00:00:00.000000',	'Secr. r. f. -. l. 2021/16',	4057.10,	136,	3,	1),
(20,	CONV('1', 2, 10) + 0,	'2022-05-07 00:00:00.000000',	'Máquina de Lavar  - Parcela 4/12 ',	-169.08,	136,	5,	1),
(21,	CONV('1', 2, 10) + 0,	'2022-07-29 00:00:00.000000',	'IFOOD IFOOD ',	-33.88,	175,	5,	1),
(22,	CONV('1', 2, 10) + 0,	'2022-07-30 00:00:00.000000',	'BUTEQUIM DA ESQUINA ',	-43.00,	174,	5,	1),
(23,	CONV('1', 2, 10) + 0,	'2022-08-02 00:00:00.000000',	'AMAZON AWS SERVICOS BR ',	-2.66,	176,	5,	1),
(24,	CONV('1', 2, 10) + 0,	'2022-08-03 00:00:00.000000',	'BALDHEAD CB ',	-60.50,	174,	5,	1),
(25,	CONV('1', 2, 10) + 0,	'2022-08-05 00:00:00.000000',	'FIRMA BAR ',	-49.50,	174,	5,	1),
(26,	CONV('1', 2, 10) + 0,	'2022-08-07 00:00:00.000000',	'RTA BAR RESTAURANTE LT ',	-62.70,	174,	5,	1),
(27,	CONV('1', 2, 10) + 0,	'2022-08-07 00:00:00.000000',	'QUENTINS ',	-46.20,	174,	5,	1),
(28,	CONV('1', 2, 10) + 0,	'2022-08-09 00:00:00.000000',	'EL TONEL ',	-77.50,	174,	5,	1),
(30,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'LIBERTY BRAZIL PARC 3 ',	-1166.25,	142,	5,	1),
(31,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'LYON PARK SQUARE ',	-10.00,	142,	5,	1),
(32,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'JAPESCA CIDADE BAIXA ',	-33.90,	175,	5,	1),
(33,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'OLDSCHOOL ',	-35.00,	176,	5,	1),
(34,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'INSTITUTO CULTURAL BR ',	-21.00,	176,	5,	1),
(35,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'A VIRGEM BAR E TATTOO ',	-94.00,	174,	5,	1),
(36,	CONV('1', 2, 10) + 0,	'2022-08-11 00:00:00.000000',	'PRI BAKERY ',	-48.00,	174,	5,	1),
(37,	CONV('1', 2, 10) + 0,	'2022-08-12 00:00:00.000000',	'UBER TRIP HELP UBER C ',	-29.93,	174,	5,	1),
(38,	CONV('1', 2, 10) + 0,	'2022-08-12 00:00:00.000000',	'EL AGUANTE ',	-53.00,	174,	5,	1),
(39,	CONV('1', 2, 10) + 0,	'2022-08-12 00:00:00.000000',	'PAG MIGUEL836 ',	-24.20,	174,	5,	1),
(40,	CONV('1', 2, 10) + 0,	'2022-08-13 00:00:00.000000',	'UBER UBER TRIP ',	-24.91,	174,	5,	1),
(41,	CONV('1', 2, 10) + 0,	'2022-08-13 00:00:00.000000',	'BAR DO NITO ',	-87.70,	174,	5,	1),
(42,	CONV('1', 2, 10) + 0,	'2022-08-14 00:00:00.000000',	'LOS TRES COMERCIO DE A ',	-82.28,	174,	5,	1),
(43,	CONV('1', 2, 10) + 0,	'2022-08-16 00:00:00.000000',	'PAG PLASTICOSPORTO ',	-85.00,	176,	5,	1),
(44,	CONV('1', 2, 10) + 0,	'2022-08-16 00:00:00.000000',	'PAG JOSERICARDOCANDID ',	-30.90,	174,	5,	1),
(45,	CONV('1', 2, 10) + 0,	'2022-08-16 00:00:00.000000',	'PAG PANVELFILIAL324 ',	-99.63,	2,	5,	1),
(46,	CONV('1', 2, 10) + 0,	'2022-08-16 00:00:00.000000',	'LEVEDURA CERVEJAS ESP ',	-52.00,	174,	5,	1),
(47,	CONV('1', 2, 10) + 0,	'2022-08-18 00:00:00.000000',	'PASTEL COM BORDA ',	-62.50,	174,	5,	1),
(48,	CONV('1', 2, 10) + 0,	'2022-08-19 00:00:00.000000',	'SANTO POSTO ',	-237.94,	142,	5,	1),
(49,	CONV('1', 2, 10) + 0,	'2022-08-19 00:00:00.000000',	'ESPONTANEO ',	-46.00,	174,	5,	1),
(50,	CONV('1', 2, 10) + 0,	'2022-08-19 00:00:00.000000',	'FENIX BAR ',	-20.00,	174,	5,	1),
(51,	CONV('1', 2, 10) + 0,	'2022-08-21 00:00:00.000000',	'PAG ALEXANDREMACHADO ',	-20.00,	174,	5,	1),
(52,	CONV('1', 2, 10) + 0,	'2022-08-21 00:00:00.000000',	'ADOMA ',	-20.00,	174,	5,	1),
(53,	CONV('1', 2, 10) + 0,	'2022-08-21 00:00:00.000000',	'PAG ATELIERDEDOCESART ',	-18.00,	174,	5,	1),
(54,	CONV('1', 2, 10) + 0,	'2022-08-21 00:00:00.000000',	'PAG MICHELLEPETINELI ',	-60.00,	174,	5,	1),
(55,	CONV('1', 2, 10) + 0,	'2022-08-24 00:00:00.000000',	'IFOOD IFOOD ',	-44.99,	175,	5,	1),
(56,	CONV('1', 2, 10) + 0,	'2022-08-24 00:00:00.000000',	'BALDHEAD CB ',	-33.00,	174,	5,	1),
(57,	CONV('1', 2, 10) + 0,	'2022-08-25 00:00:00.000000',	'DTP DRIVE TERCEIRA PER ',	-24.70,	175,	5,	1),
(58,	CONV('1', 2, 10) + 0,	'2022-08-26 00:00:00.000000',	'BARRANQUINHO ',	-190.30,	174,	5,	1),
(59,	CONV('1', 2, 10) + 0,	'2022-08-28 00:00:00.000000',	'FAMIGLIA FACIN ',	-119.35,	174,	5,	1),
(60,	CONV('1', 2, 10) + 0,	'2022-08-29 00:00:00.000000',	'LYON PARK SQUARE ',	-10.00,	142,	5,	1),
(61,	CONV('1', 2, 10) + 0,	'2022-08-29 00:00:00.000000',	'ARTEMIO GRITTI COMERCI ',	-20.00,	174,	5,	1),
(62,	CONV('1', 2, 10) + 0,	'2022-11-28 00:00:00.000000',	'PP ALURA - Parcela 10/12 ',	-63.75,	177,	5,	1),
(63,	CONV('1', 2, 10) + 0,	'2022-07-18 00:00:00.000000',	'PAG CENTURIAINDUSTRIA - Parcela 2/3 ',	-113.54,	176,	5,	1),
(64,	CONV('1', 2, 10) + 0,	'2022-08-02 00:00:00.000000',	'EBW SPOTIFY ',	-19.90,	176,	5,	1),
(67,	CONV('1', 2, 10) + 0,	'2022-08-01 03:00:00.000000',	'Saldo Anterior',	15503.87,	66,	65,	1),
(69,	CONV('1', 2, 10) + 0,	'2022-08-08 03:00:00.000000',	'Salário 1',	5200.00,	68,	65,	1),
(70,	CONV('1', 2, 10) + 0,	'2022-08-25 03:00:00.000000',	'Salário 2',	4421.21,	68,	65,	1),
(71,	CONV('1', 2, 10) + 0,	'2022-08-01 03:00:00.000000',	'Saldo Anterior',	32181.17,	66,	3,	1),
(72,	CONV('1', 2, 10) + 0,	'2022-08-01 03:00:00.000000',	'Saldo Anterior',	124.11,	66,	4,	1),
(100,	CONV('1', 2, 10) + 0,	'2022-09-02 00:00:00.000000',	'Tomograf Radiolog',	-205.00,	2,	3,	74),
(101,	CONV('1', 2, 10) + 0,	'2022-09-02 00:00:00.000000',	'Posto Paineira',	-41.39,	174,	3,	74),
(102,	CONV('1', 2, 10) + 0,	'2022-09-02 00:00:00.000000',	'Bradesco C-detran Gade-e/rs',	-267.79,	176,	3,	74),
(103,	CONV('1', 2, 10) + 0,	'2022-09-05 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	184.49,	136,	3,	74),
(104,	CONV('1', 2, 10) + 0,	'2022-09-05 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	22.68,	136,	3,	74),
(105,	CONV('1', 2, 10) + 0,	'2022-09-05 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	57.38,	136,	3,	74),
(106,	CONV('1', 2, 10) + 0,	'2022-09-05 00:00:00.000000',	'Pag*panvelfilial397',	-7.56,	2,	3,	74),
(107,	CONV('1', 2, 10) + 0,	'2022-09-06 00:00:00.000000',	'Atelier de Massas',	-104.34,	174,	3,	74),
(108,	CONV('1', 2, 10) + 0,	'2022-09-09 00:00:00.000000',	'Renato Pozzebon',	-300.00,	142,	3,	74),
(109,	CONV('1', 2, 10) + 0,	'2022-09-09 00:00:00.000000',	'Dest.vicente Coda',	-680.00,	2,	3,	74),
(110,	CONV('1', 2, 10) + 0,	'2022-09-09 00:00:00.000000',	'Des: Stefan Cardon 09/09',	-303.00,	2,	3,	74),
(111,	CONV('1', 2, 10) + 0,	'2022-09-09 00:00:00.000000',	'Des: Veronica Torino Lerme 09/09',	-140.00,	139,	3,	74),
(112,	CONV('1', 2, 10) + 0,	'2022-09-09 00:00:00.000000',	'Bradesco C-claro S.a.',	-223.53,	139,	3,	74),
(113,	CONV('1', 2, 10) + 0,	'2022-09-12 00:00:00.000000',	'Fatura C6 Agosto',	-3646.69,	73,	3,	74),
(114,	CONV('1', 2, 10) + 0,	'2022-09-12 00:00:00.000000',	'Pag*panvelfilial324',	-21.63,	2,	3,	74),
(115,	CONV('1', 2, 10) + 0,	'2022-09-13 00:00:00.000000',	'Posto Pegasus',	-265.72,	142,	3,	74),
(116,	CONV('1', 2, 10) + 0,	'2022-09-13 00:00:00.000000',	'Oldschool',	-31.90,	176,	3,	74),
(117,	CONV('1', 2, 10) + 0,	'2022-09-13 00:00:00.000000',	'Mekaurio',	-74.80,	174,	3,	74),
(118,	CONV('1', 2, 10) + 0,	'2022-09-14 00:00:00.000000',	'Pag*garagemalbertobi',	-13.00,	142,	3,	74),
(119,	CONV('1', 2, 10) + 0,	'2022-09-15 00:00:00.000000',	'Cesta Exclusive',	-68.80,	174,	3,	74),
(120,	CONV('1', 2, 10) + 0,	'2022-09-15 00:00:00.000000',	'el Tonel',	-77.50,	174,	3,	74),
(121,	CONV('1', 2, 10) + 0,	'2022-09-19 00:00:00.000000',	'Parque Dos Peixes',	-79.00,	174,	3,	74),
(122,	CONV('1', 2, 10) + 0,	'2022-09-19 00:00:00.000000',	'Saque 24H',	-500.00,	176,	3,	74),
(123,	CONV('1', 2, 10) + 0,	'2022-09-22 00:00:00.000000',	'Des: Pedro Luis Guimaraes 22/09',	-35.00,	174,	3,	74),
(124,	CONV('1', 2, 10) + 0,	'2022-09-29 00:00:00.000000',	'Areatec - Tecnolo',	-4.60,	142,	3,	74),
(125,	CONV('1', 2, 10) + 0,	'2022-09-29 00:00:00.000000',	'Pag*panvelfilial324',	-116.59,	2,	3,	74),
(126,	CONV('1', 2, 10) + 0,	'2022-09-29 00:00:00.000000',	'Des: Eduardo Ferrary Rocha 29/09',	-1500.00,	2,	3,	74),
(127,	CONV('1', 2, 10) + 0,	'2022-09-01 03:00:00.000000',	'Saldo Anterior',	40515.88,	66,	3,	74),
(128,	NULL,	'2022-09-01 03:00:00.000000',	'Saldo Anterior',	25125.08,	66,	65,	74),
(129,	CONV('1', 2, 10) + 0,	'2022-09-10 03:00:00.000000',	'Salário 1',	5200.00,	68,	65,	74),
(130,	CONV('1', 2, 10) + 0,	'2022-09-25 03:00:00.000000',	'Salário 2',	4421.21,	68,	65,	74),
(131,	CONV('1', 2, 10) + 0,	'2022-09-01 03:00:00.000000',	'Saldo Anterior',	124.11,	66,	4,	74),
(133,	CONV('1', 2, 10) + 0,	'2022-10-01 03:00:00.000000',	'Saldo Anterior',	34746.29,	66,	65,	132),
(134,	CONV('1', 2, 10) + 0,	'2022-10-01 03:00:00.000000',	'Saldo Anterior',	32072.59,	66,	3,	132),
(135,	CONV('1', 2, 10) + 0,	'2022-10-01 03:00:00.000000',	'Saldo Anterior',	124.11,	66,	4,	132),
(137,	CONV('1', 2, 10) + 0,	'2022-10-03 14:03:32.836000',	'Rendimento',	24.14,	136,	3,	132),
(138,	CONV('1', 2, 10) + 0,	'2022-10-03 03:00:00.000000',	'Rendimento',	153.84,	136,	3,	132),
(140,	NULL,	'2022-10-10 03:00:00.000000',	'Vicente',	-680.00,	2,	3,	132),
(141,	NULL,	'2022-10-10 03:00:00.000000',	'Tratamento Dentário',	-303.00,	2,	3,	132),
(143,	NULL,	'2022-10-03 15:48:13.217000',	'Garagem',	-300.00,	142,	3,	132),
(144,	NULL,	'2022-10-03 03:00:00.000000',	'Verônica',	-140.00,	139,	3,	132),
(145,	NULL,	'2022-10-03 15:50:48.523000',	'Gasolina',	-250.00,	142,	3,	132),
(146,	NULL,	'2022-10-03 03:00:00.000000',	'Net',	-220.00,	139,	3,	132),
(147,	NULL,	'2022-10-10 03:00:00.000000',	'Fatura C6 Setembro',	-2546.32,	73,	3,	132),
(148,	CONV('1', 2, 10) + 0,	'2022-05-07 00:00:00.000000',	'Máquina Lavar Roupa 5/12 ',	-169.08,	139,	5,	74),
(149,	CONV('1', 2, 10) + 0,	'2022-08-30 00:00:00.000000',	'IFOOD IFOOD ',	-32.88,	175,	5,	74),
(150,	CONV('1', 2, 10) + 0,	'2022-09-02 00:00:00.000000',	'AMAZON AWS SERVICOS BR ',	-2.55,	176,	5,	74),
(151,	CONV('1', 2, 10) + 0,	'2022-09-10 00:00:00.000000',	'LIBERTY BRAZIL PARC 4 ',	-1166.27,	142,	5,	74),
(152,	CONV('1', 2, 10) + 0,	'2022-09-10 00:00:00.000000',	'IFOOD IFOOD ',	-32.88,	175,	5,	74),
(153,	CONV('1', 2, 10) + 0,	'2022-09-11 00:00:00.000000',	'BRECHO DO FUTEBOL CERV ',	-52.80,	174,	5,	74),
(154,	CONV('1', 2, 10) + 0,	'2022-09-11 00:00:00.000000',	'RTA BAR RESTAURANTE LT ',	-72.00,	174,	5,	74),
(156,	CONV('1', 2, 10) + 0,	'2022-09-17 00:00:00.000000',	'DOM CAMILO PRODUTOS CO ',	-88.00,	174,	5,	74),
(157,	CONV('1', 2, 10) + 0,	'2022-09-17 00:00:00.000000',	'PAG MARIPOUSADA ',	-32.00,	174,	5,	74),
(158,	CONV('1', 2, 10) + 0,	'2022-09-19 00:00:00.000000',	'PAG CELIOBRANCALIONE ',	-18.50,	174,	5,	74),
(159,	CONV('1', 2, 10) + 0,	'2022-09-19 00:00:00.000000',	'POSTO TERRA NOSTRA ',	-200.00,	142,	5,	74),
(160,	CONV('1', 2, 10) + 0,	'2022-09-19 00:00:00.000000',	'FLORES KITCHEN ',	-12.00,	174,	5,	74),
(161,	CONV('1', 2, 10) + 0,	'2022-09-20 00:00:00.000000',	'SERGIO LUIZ CATANI ',	-39.00,	174,	5,	74),
(162,	CONV('1', 2, 10) + 0,	'2022-09-21 00:00:00.000000',	'GARGULA ',	-33.00,	174,	5,	74),
(163,	CONV('1', 2, 10) + 0,	'2022-09-22 00:00:00.000000',	'PUC CAMPUS FACULDADE ',	-13.00,	142,	5,	74),
(164,	CONV('1', 2, 10) + 0,	'2022-09-22 00:00:00.000000',	'LANTICA PIETRA ',	-122.20,	174,	5,	74),
(165,	CONV('1', 2, 10) + 0,	'2022-09-23 00:00:00.000000',	'ESPACO LED POA ',	-14.98,	176,	5,	74),
(166,	CONV('1', 2, 10) + 0,	'2022-09-23 00:00:00.000000',	'MULTIPLAN ADMINISTRADO ',	-15.00,	174,	5,	74),
(167,	CONV('1', 2, 10) + 0,	'2022-09-26 00:00:00.000000',	'KALUNGA IGUATEMI PORTO - Parcela 1/3 ',	-97.78,	176,	5,	74),
(168,	CONV('1', 2, 10) + 0,	'2022-09-26 00:00:00.000000',	'ESTACIONAMENTO IGUATEM ',	-15.00,	142,	5,	74),
(169,	CONV('1', 2, 10) + 0,	'2022-09-28 00:00:00.000000',	'TANGA ',	-83.60,	174,	5,	74),
(170,	CONV('1', 2, 10) + 0,	'2022-11-28 00:00:00.000000',	'PP ALURA - Parcela 11/12 ',	-63.75,	177,	5,	74),
(171,	CONV('1', 2, 10) + 0,	'2022-07-18 00:00:00.000000',	'PAG CENTURIAINDUSTRIA - Parcela 3/3 ',	-113.54,	176,	5,	74),
(172,	CONV('1', 2, 10) + 0,	'2022-09-02 00:00:00.000000',	'EBW SPOTIFY ',	-19.90,	176,	5,	74),
(173,	CONV('1', 2, 10) + 0,	'2022-09-25 00:00:00.000000',	'IPLACE COM - Parcela 1/10 ',	-36.61,	176,	5,	74);

CREATE TABLE `planilha` (
  `id` int NOT NULL,
  `ano` smallint NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `mes` smallint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `planilha` (`id`, `ano`, `descricao`, `mes`) VALUES
(1,	2022,	'Agosto',	8),
(74,	2022,	'Setembro',	9),
(132,	2022,	'Outubro',	10);

-- 2022-10-03 19:56:55
