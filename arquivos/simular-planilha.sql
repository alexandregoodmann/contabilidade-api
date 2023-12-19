drop  TABLE `contabilidade-dev`.planilha2024;

CREATE  TABLE `contabilidade-dev`.planilha2024 (
id int primary key not null,
	data DATETIME(6)  NULL,
	conta varchar(255),
	descricao varchar(255) null,
	fixo varchar(255),
	parcelas varchar(50),
	valor1 numeric(11,2),
	valor2 numeric(11,2),
	valor3 numeric(11,2),
	valor4 numeric(11,2),
	valor5 numeric(11,2),
	valor6 numeric(11,2),
	valor7 numeric(11,2),
	valor8 numeric(11,2),
	valor9 numeric(11,2),
	valor10 numeric(11,2),
	valor11 numeric(11,2),
	valor12 numeric(11,2) 
);

select l.id, c.descricao, l.data, l.descricao , l.valor, l.fixo, l.parcelas from lancamento l inner join conta c on c.id  = l.idconta inner join planilha p on p.id = l.idplanilha where p.ano = 2023 and p.mes = 12;

insert into planilha2024(id, conta, data, descricao, fixo, parcelas, valor1) 
	select l.id, c.descricao, l.data, l.descricao , l.fixo, l.parcelas, l.valor from lancamento l 
	inner join conta c on c.id  = l.id_conta 
	inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12;

select * from planilha2024;

