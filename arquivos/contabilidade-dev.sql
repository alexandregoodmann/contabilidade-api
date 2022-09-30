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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `categoria` (`id`, `descricao`) VALUES
(2,	'Saúde'),
(18,	'Carro'),
(19,	'Casa'),
(20,	'Rendimento'),
(21,	'Salário'),
(22,	'Diversão'),
(23,	'Cartão'),
(71,	'Alimentação'),
(72,	'Pessoal'),
(73,	'Saldo Anterior');

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
(24,	'C6',	NULL,	'C6',	'CC'),
(25,	'C6',	'C6',	'C6 Cartão',	'CARTAO'),
(213,	'Itaú',	NULL,	'Itaú',	'CC'),
(248,	'Carteira',	NULL,	'Carteira',	'CARTEIRA');

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(250),
(250),
(250),
(250);

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
(4,	CONV('1', 2, 10) + 0,	'2022-08-02 00:00:00.000000',	'Renato Pozzebon',	-300.00,	18,	3,	1),
(5,	CONV('1', 2, 10) + 0,	'2022-08-02 00:00:00.000000',	'Dest: Veronica Torino Lermen',	-265.00,	19,	3,	1),
(6,	CONV('1', 2, 10) + 0,	'2022-08-03 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	172.88,	20,	3,	1),
(7,	CONV('1', 2, 10) + 0,	'2022-08-04 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	23.16,	20,	3,	1),
(8,	CONV('1', 2, 10) + 0,	'2022-08-05 00:00:00.000000',	'Remet.coop Soma',	10245.22,	21,	3,	1),
(9,	CONV('1', 2, 10) + 0,	'2022-08-05 00:00:00.000000',	'Damask',	-68.75,	22,	3,	1),
(10,	CONV('1', 2, 10) + 0,	'2022-08-08 00:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	26.53,	20,	3,	1),
(11,	CONV('1', 2, 10) + 0,	'2022-08-08 00:00:00.000000',	'Distribuidora de Gas',	-125.00,	19,	3,	1),
(12,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Fatura C6 Julho',	-4050.82,	23,	3,	1),
(13,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Dest.vicente Coda',	-850.00,	2,	3,	1),
(14,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Dest: Stefan Cardon',	-303.00,	2,	3,	1),
(15,	CONV('1', 2, 10) + 0,	'2022-08-10 00:00:00.000000',	'Bradesco C-claro S.a.',	-213.61,	19,	3,	1),
(16,	CONV('1', 2, 10) + 0,	'2022-08-26 00:00:00.000000',	'ok Park',	-14.00,	18,	3,	1),
(17,	CONV('1', 2, 10) + 0,	'2022-08-30 00:00:00.000000',	'Secr. r. f. -. l. 2021/16',	4057.10,	20,	3,	1),
(74,	CONV('1', 2, 10) + 0,	'2022-08-01 03:00:00.000000',	'Saldo Anterior',	32181.17,	73,	3,	1),
(168,	CONV('1', 2, 10) + 0,	'2022-05-07 03:00:00.000000',	'LOJAS COLOMBO 291 - Parcela 4/12 ',	-169.08,	NULL,	25,	1),
(169,	CONV('1', 2, 10) + 0,	'2022-07-29 03:00:00.000000',	'IFOOD IFOOD ',	-33.88,	NULL,	25,	1),
(170,	CONV('1', 2, 10) + 0,	'2022-07-30 03:00:00.000000',	'BUTEQUIM DA ESQUINA ',	-43.00,	NULL,	25,	1),
(171,	CONV('1', 2, 10) + 0,	'2022-08-02 03:00:00.000000',	'AMAZON AWS SERVICOS BR ',	-2.66,	NULL,	25,	1),
(172,	CONV('1', 2, 10) + 0,	'2022-08-03 03:00:00.000000',	'BALDHEAD CB ',	-60.50,	NULL,	25,	1),
(173,	CONV('1', 2, 10) + 0,	'2022-08-05 03:00:00.000000',	'FIRMA BAR ',	-49.50,	NULL,	25,	1),
(174,	CONV('1', 2, 10) + 0,	'2022-08-07 03:00:00.000000',	'RTA BAR RESTAURANTE LT ',	-62.70,	NULL,	25,	1),
(175,	CONV('1', 2, 10) + 0,	'2022-08-07 03:00:00.000000',	'QUENTINS ',	-46.20,	NULL,	25,	1),
(176,	CONV('1', 2, 10) + 0,	'2022-08-09 03:00:00.000000',	'EL TONEL ',	-77.50,	NULL,	25,	1),
(178,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'LIBERTY BRAZIL PARC 3 ',	-1166.25,	NULL,	25,	1),
(179,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'LYON PARK SQUARE ',	-10.00,	NULL,	25,	1),
(180,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'JAPESCA CIDADE BAIXA ',	-33.90,	NULL,	25,	1),
(181,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'OLDSCHOOL ',	-35.00,	NULL,	25,	1),
(182,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'INSTITUTO CULTURAL BR ',	-21.00,	NULL,	25,	1),
(183,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'A VIRGEM BAR E TATTOO ',	-94.00,	NULL,	25,	1),
(184,	CONV('1', 2, 10) + 0,	'2022-08-11 03:00:00.000000',	'PRI BAKERY ',	-48.00,	NULL,	25,	1),
(185,	CONV('1', 2, 10) + 0,	'2022-08-12 03:00:00.000000',	'UBER TRIP HELP UBER C ',	-29.93,	NULL,	25,	1),
(186,	CONV('1', 2, 10) + 0,	'2022-08-12 03:00:00.000000',	'EL AGUANTE ',	-53.00,	NULL,	25,	1),
(187,	CONV('1', 2, 10) + 0,	'2022-08-12 03:00:00.000000',	'PAG MIGUEL836 ',	-24.20,	NULL,	25,	1),
(188,	CONV('1', 2, 10) + 0,	'2022-08-13 03:00:00.000000',	'UBER UBER TRIP ',	-24.91,	NULL,	25,	1),
(189,	CONV('1', 2, 10) + 0,	'2022-08-13 03:00:00.000000',	'BAR DO NITO ',	-87.70,	NULL,	25,	1),
(190,	CONV('1', 2, 10) + 0,	'2022-08-14 03:00:00.000000',	'LOS TRES COMERCIO DE A ',	-82.28,	NULL,	25,	1),
(191,	CONV('1', 2, 10) + 0,	'2022-08-16 03:00:00.000000',	'PAG PLASTICOSPORTO ',	-85.00,	NULL,	25,	1),
(192,	CONV('1', 2, 10) + 0,	'2022-08-16 03:00:00.000000',	'PAG JOSERICARDOCANDID ',	-30.90,	NULL,	25,	1),
(193,	CONV('1', 2, 10) + 0,	'2022-08-16 03:00:00.000000',	'PAG PANVELFILIAL324 ',	-99.63,	NULL,	25,	1),
(194,	CONV('1', 2, 10) + 0,	'2022-08-16 03:00:00.000000',	'LEVEDURA CERVEJAS ESP ',	-52.00,	NULL,	25,	1),
(195,	CONV('1', 2, 10) + 0,	'2022-08-18 03:00:00.000000',	'PASTEL COM BORDA ',	-62.50,	NULL,	25,	1),
(196,	CONV('1', 2, 10) + 0,	'2022-08-19 03:00:00.000000',	'SANTO POSTO ',	-237.94,	NULL,	25,	1),
(197,	CONV('1', 2, 10) + 0,	'2022-08-19 03:00:00.000000',	'ESPONTANEO ',	-46.00,	NULL,	25,	1),
(198,	CONV('1', 2, 10) + 0,	'2022-08-19 03:00:00.000000',	'FENIX BAR ',	-20.00,	NULL,	25,	1),
(199,	CONV('1', 2, 10) + 0,	'2022-08-21 03:00:00.000000',	'PAG ALEXANDREMACHADO ',	-20.00,	NULL,	25,	1),
(200,	CONV('1', 2, 10) + 0,	'2022-08-21 03:00:00.000000',	'ADOMA ',	-20.00,	NULL,	25,	1),
(201,	CONV('1', 2, 10) + 0,	'2022-08-21 03:00:00.000000',	'PAG ATELIERDEDOCESART ',	-18.00,	NULL,	25,	1),
(202,	CONV('1', 2, 10) + 0,	'2022-08-21 03:00:00.000000',	'PAG MICHELLEPETINELI ',	-60.00,	NULL,	25,	1),
(203,	CONV('1', 2, 10) + 0,	'2022-08-24 03:00:00.000000',	'IFOOD IFOOD ',	-44.99,	NULL,	25,	1),
(204,	CONV('1', 2, 10) + 0,	'2022-08-24 03:00:00.000000',	'BALDHEAD CB ',	-33.00,	NULL,	25,	1),
(205,	CONV('1', 2, 10) + 0,	'2022-08-25 03:00:00.000000',	'DTP DRIVE TERCEIRA PER ',	-24.70,	NULL,	25,	1),
(206,	CONV('1', 2, 10) + 0,	'2022-08-26 03:00:00.000000',	'BARRANQUINHO ',	-190.30,	NULL,	25,	1),
(207,	CONV('1', 2, 10) + 0,	'2022-08-28 03:00:00.000000',	'FAMIGLIA FACIN ',	-119.35,	NULL,	25,	1),
(208,	CONV('1', 2, 10) + 0,	'2022-08-29 03:00:00.000000',	'LYON PARK SQUARE ',	-10.00,	NULL,	25,	1),
(209,	CONV('1', 2, 10) + 0,	'2022-08-29 03:00:00.000000',	'ARTEMIO GRITTI COMERCI ',	-20.00,	NULL,	25,	1),
(210,	CONV('1', 2, 10) + 0,	'2022-11-28 03:00:00.000000',	'PP ALURA - Parcela 10/12 ',	-63.75,	NULL,	25,	1),
(211,	CONV('1', 2, 10) + 0,	'2022-07-18 03:00:00.000000',	'PAG CENTURIAINDUSTRIA - Parcela 2/3 ',	-113.54,	NULL,	25,	1),
(212,	CONV('1', 2, 10) + 0,	'2022-08-02 03:00:00.000000',	'EBW SPOTIFY ',	-19.90,	NULL,	25,	1),
(214,	CONV('1', 2, 10) + 0,	'2022-08-10 03:00:00.000000',	'Salário 1',	5200.00,	21,	213,	1),
(215,	CONV('1', 2, 10) + 0,	'2022-08-25 03:00:00.000000',	'Salário 2',	4300.00,	21,	213,	1),
(216,	CONV('1', 2, 10) + 0,	'2022-08-01 03:00:00.000000',	'Saldo Anterior',	15446.43,	73,	213,	1),
(217,	CONV('1', 2, 10) + 0,	'2022-08-01 03:00:00.000000',	'Saldo Anterior',	124.11,	73,	24,	1),
(219,	CONV('1', 2, 10) + 0,	'2022-09-02 03:00:00.000000',	'Tomograf Radiolog',	-205.00,	2,	3,	218),
(220,	CONV('1', 2, 10) + 0,	'2022-09-02 03:00:00.000000',	'Posto Paineira Cerveja',	-41.39,	22,	3,	218),
(221,	CONV('1', 2, 10) + 0,	'2022-09-02 03:00:00.000000',	'Renovação Habilitação',	-267.79,	72,	3,	218),
(222,	CONV('1', 2, 10) + 0,	'2022-09-05 03:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	184.49,	20,	3,	218),
(223,	CONV('1', 2, 10) + 0,	'2022-09-05 03:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	22.68,	20,	3,	218),
(224,	CONV('1', 2, 10) + 0,	'2022-09-05 03:00:00.000000',	'Poup Facil-depos a Partir 4/5/12',	57.38,	20,	3,	218),
(225,	CONV('1', 2, 10) + 0,	'2022-09-05 03:00:00.000000',	'Farmácia Panvel',	-7.56,	2,	3,	218),
(226,	CONV('1', 2, 10) + 0,	'2022-09-06 03:00:00.000000',	'Atelier de Massas',	-104.34,	22,	3,	218),
(227,	CONV('1', 2, 10) + 0,	'2022-09-09 03:00:00.000000',	'Renato Pozzebon',	-300.00,	18,	3,	218),
(228,	CONV('1', 2, 10) + 0,	'2022-09-09 03:00:00.000000',	'Dest.vicente Coda',	-680.00,	2,	3,	218),
(229,	CONV('1', 2, 10) + 0,	'2022-09-09 03:00:00.000000',	'Des: Stefan Cardon 09/09',	-303.00,	2,	3,	218),
(230,	CONV('1', 2, 10) + 0,	'2022-09-09 03:00:00.000000',	'Des: Veronica Torino Lerme 09/09',	-140.00,	19,	3,	218),
(231,	CONV('1', 2, 10) + 0,	'2022-09-09 03:00:00.000000',	'Bradesco C-claro S.a.',	-223.53,	19,	3,	218),
(232,	CONV('1', 2, 10) + 0,	'2022-09-12 03:00:00.000000',	'Fatura C6 Agosto',	-3646.69,	23,	3,	218),
(233,	CONV('1', 2, 10) + 0,	'2022-09-12 03:00:00.000000',	'	Farmácia Panvel',	-21.63,	2,	3,	218),
(234,	CONV('1', 2, 10) + 0,	'2022-09-13 03:00:00.000000',	'Posto Pegasus',	-265.72,	18,	3,	218),
(235,	CONV('1', 2, 10) + 0,	'2022-09-13 03:00:00.000000',	'Oldschool',	-31.90,	72,	3,	218),
(236,	CONV('1', 2, 10) + 0,	'2022-09-13 03:00:00.000000',	'Mekaurio',	-74.80,	22,	3,	218),
(237,	CONV('1', 2, 10) + 0,	'2022-09-14 03:00:00.000000',	'Pag*garagemalbertobi',	-13.00,	18,	3,	218),
(238,	CONV('1', 2, 10) + 0,	'2022-09-15 03:00:00.000000',	'Cesta Exclusive',	-68.80,	22,	3,	218),
(239,	CONV('1', 2, 10) + 0,	'2022-09-15 03:00:00.000000',	'el Tonel',	-77.50,	22,	3,	218),
(240,	CONV('1', 2, 10) + 0,	'2022-09-19 03:00:00.000000',	'Parque Dos Peixes',	-79.00,	22,	3,	218),
(241,	CONV('1', 2, 10) + 0,	'2022-09-19 03:00:00.000000',	'Saque 24H',	-500.00,	72,	3,	218),
(242,	CONV('1', 2, 10) + 0,	'2022-09-22 03:00:00.000000',	'Des: Pedro Luis Guimaraes 22/09',	-35.00,	22,	3,	218),
(243,	CONV('1', 2, 10) + 0,	'2022-09-01 03:00:00.000000',	'Saldo Anterior',	40515.88,	73,	3,	218),
(244,	CONV('1', 2, 10) + 0,	'2022-09-01 03:00:00.000000',	'Saldo Anterior',	124.11,	73,	24,	218),
(245,	CONV('1', 2, 10) + 0,	'2022-09-09 03:00:00.000000',	'Salário 1',	5200.00,	21,	213,	218),
(246,	CONV('1', 2, 10) + 0,	'2022-09-23 03:00:00.000000',	'Salário 2',	4421.21,	21,	213,	218),
(247,	CONV('1', 2, 10) + 0,	'2022-09-01 03:00:00.000000',	'Saldo Anterior',	25121.23,	73,	213,	218),
(249,	CONV('1', 2, 10) + 0,	'2022-09-19 03:00:00.000000',	'Saque Bradesco',	500.00,	72,	248,	218);

CREATE TABLE `planilha` (
  `id` int NOT NULL,
  `ano` smallint NOT NULL,
  `descricao` varchar(50) NOT NULL,
  `mes` smallint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `planilha` (`id`, `ano`, `descricao`, `mes`) VALUES
(1,	2022,	'Agosto',	8),
(218,	2022,	'Setembro',	9);

-- 2022-09-30 12:22:26
