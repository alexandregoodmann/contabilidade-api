drop table label;

ALTER TABLE categoria RENAME TO label;

CREATE TABLE `lancamento_label` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_lancamento` int NOT NULL,
  `id_label` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lancamento_label_label` (`id_label`),
  KEY `fk_lancamento_label_lancamento` (`id_lancamento`),
  CONSTRAINT `fk_lancamento_label_label` FOREIGN KEY (`id_label`) REFERENCES `label` (`id`),
  CONSTRAINT `fk_lancamento_label_lancamento` FOREIGN KEY (`id_lancamento`) REFERENCES `lancamento` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8212 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO lancamento_label (id_lancamento, id_label) select id, id_categoria from lancamento where id_categoria is not null;

alter table lancamento  drop foreign key FKr651pitjvnxinfpcgbd9g71sf;

alter table lancamento drop id_categoria;

alter table lancamento  drop foreign key FK76mkkwgp6pceuxc8c7p5e3ve0;

alter table lancamento drop id_conta_cartao;

drop table limite_gastos ;