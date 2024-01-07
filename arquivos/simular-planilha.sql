drop  TABLE `contabilidade-dev`.planilha2024;

CREATE  TABLE `contabilidade-dev`.planilha_anual (
	id int primary key not null,
	titulo varchar(255) not null,
	data DATETIME(6)  NULL,
	conta varchar(255),
	tipo_conta varchar(255),
	descricao varchar(255) null,
	tipo_lancamento varchar(255),
	fixo varchar(255),
	parcelas varchar(50),
	valor numeric(11,2) not null,
	valores varchar(255)
);

CREATE  TABLE `contabilidade-dev`.amortizacao (
	id int primary key not null AUTO_INCREMENT,
	data DATETIME(6)  NULL,
	valor numeric(11,2) not null,
	pago boolean not null
);
select * from amortizacao ;
select * from planilha_anual;
truncate table `contabilidade-dev`.planilha_anual;

select l.id, l.data, c.descricao,  l.descricao , l.valor, l.fixo, l.parcelas 
	from lancamento l inner join conta c on c.id  = l.id_conta 
	inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12;

insert into planilha_anual(id, titulo, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor) 
	            select l.id, 'titulo_1', l.data, c.descricao, c.tipo, l.descricao, l.tipo,  l.fixo, l.parcelas, l.valor
	from lancamento l inner join conta c on c.id  = l.id_conta 
	where l.id_planilha = 9455;

-- remover fixo de saldo anterior
select * from planilha p;-- where id = 9455;

-- labels
select l.* from lancamento l 
	join lancamento_label ll on ll.id_lancamento = l.id 
	join label l2 on l2.id = ll.id_label 
	where l.id_planilha = 9455 and l2.descricao = 'fatura';

select * from planilha; --9455



