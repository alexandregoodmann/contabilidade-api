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

select * from planilha2024 p order by conta;


