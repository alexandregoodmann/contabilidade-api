select
	l.id as idlancamento,
	l.data,
	l.descricao,
	l.valor,
	l.fixo,
	l.concluido,
	l.numero_bradesco ,
	l.tipo ,
	l.parcelas,
	c.id as idconta,
	c.descricao as conta,
	c.tipo as tipoconta
from
	lancamento l
join planilha p on
	p.id = l.id_planilha
join conta c on
	 c.id = l.id_conta
where
	p.id = 8171;