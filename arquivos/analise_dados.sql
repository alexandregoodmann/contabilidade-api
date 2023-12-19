select * from planilha p ;

select * from lancamento l where l.id_planilha = 9455;

select * from lancamento l where l.id_planilha = 9455 and l.concluido = 1;

-- entradas previstas
select c.id, c.descricao, l.descricao, l.valor from lancamento l join conta c on c.id = l.id_conta where l.id_planilha = 9455 and c.id <> 7760 and l.valor > 0;
select sum(l.valor) from lancamento l join conta c on c.id = l.id_conta where l.id_planilha = 9455 and c.id <> 7760 and l.valor > 0;

-- entradas concluidas
select sum(l.valor) from lancamento l join conta c on c.id = l.id_conta where l.id_planilha = 9455 and c.id <> 7760 and l.valor > 0 and l.concluido = 1;
select c.id, c.descricao, l.descricao, l.valor from lancamento l join conta c on c.id = l.id_conta where l.id_planilha = 9455 and c.id <> 7760 and l.valor > 0 and l.concluido = 1;

-- saidas previstas
select c.id, c.descricao, c.tipo ,l.descricao, l.valor from lancamento l join conta c on c.id = l.id_conta where l.id_planilha = 9455 and c.id <> 7760 and c.tipo = 'CC' and l.valor < 0 order by c.id;
select sum(l.valor) from lancamento l join conta c on c.id = l.id_conta where l.id_planilha = 9455 and c.id <> 7760 and c.tipo = 'CC' and l.valor < 0 order by c.id;

-- saidas concluidas
select c.id, c.descricao, c.tipo ,l.descricao, l.valor from lancamento l join conta c on c.id = l.id_conta 
	where l.id_planilha = 9455 and c.id <> 7760 and c.tipo = 'CC' and l.valor < 0 and l.concluido = 1 order by c.id;
select sum(l.valor) from lancamento l join conta c on c.id = l.id_conta 
	where l.id_planilha = 9455 and c.id <> 7760 and c.tipo = 'CC' and l.valor < 0 and l.concluido = 1 order by c.id;
	
select 
	c.id,
	c.descricao,
	(select sum(valor) from lancamento l where l.id_planilha = 9455 and l.id_conta = c.id and l.valor > 0) as entradaprevista,
	(select sum(valor) from lancamento l where l.id_planilha = 9455 and l.id_conta = c.id and l.valor > 0 and concluido = 1) as entradaconcluida,
	(select sum(l.valor) from lancamento l where l.id_planilha = 9455 and l.id_conta = c.id and l.valor < 0) as saidaprevista,
	(select sum(l.valor) from lancamento l where l.id_planilha = 9455 and l.id_conta = c.id and l.valor < 0 and concluido = 1) as saidaconcluida
from conta c where c.id <> 7760 and c.tipo = 'CC';

select c.descricao, l.data, l.descricao , l.valor from lancamento l inner join conta c on c.id  = l.id_conta inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12;
select * from conta;
select * from planilha_2024;

insert into planilha_2024(conta, data, descricao, valor_1) select c.descricao, l.data, l.descricao , l.valor from lancamento l 
	inner join conta c on c.id  = l.id_conta 
	inner join planilha p on p.id = l.id_planilha where p.ano = 2023 and p.mes = 12;

