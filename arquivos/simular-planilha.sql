drop  TABLE `contabilidade-dev`.planilha2024;

CREATE  TABLE `contabilidade-dev`.planilha2024 (
	id int primary key not null,
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

select * from planilha2024;

select l.id, l.data, c.descricao,  l.descricao , l.valor, l.fixo, l.parcelas 
	from lancamento l inner join conta c on c.id  = l.id_conta 
	inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12;

insert into planilha2024(id, data, conta, tipo_conta, descricao, tipo_lancamento, fixo, parcelas, valor) 
	            select l.id, l.data, c.descricao, c.tipo, l.descricao, l.tipo,  l.fixo, l.parcelas, l.valor
	from lancamento l inner join conta c on c.id  = l.id_conta 
	inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12;

select * from lancamento   where parcelas is not NULL and fixo is null;

update lancamento set fixo = null where parcelas  is not null;

select * from lancamento l ;

select * from planilha2024 p order by p.tipo_lancamento ;

-- remover fixo de saldo anterior
select * from planilha p;-- where id = 9455;

select l.* from lancamento l 
	join lancamento_label ll on ll.id_lancamento = l.id 
	join label l2 on l2.id = ll.id_label 
	where l.id_planilha = 9455 and l2.descricao = 'fatura';

UPDATE lancamento l set fixo = null where l.id_planilha = 9455 and tipo = 'saldo';
update lancamento set fixo = null, tipo = 'FATURA' where id in (9530, 9529, 9474);

select * from lancamento l where l.id_planilha  = 9455 and l.id_conta = 8710;
select * from conta;
select id from lancamento l where l.id_planilha  = 9455 and l.id_conta = 8710;

delete from lancamento l where l.id_planilha  = 9455 and l.id_conta = 8710;

delete from lancamento_label where id_lancamento in (select id from lancamento l where l.id_planilha  = 9455 and l.id_conta = 8710);



