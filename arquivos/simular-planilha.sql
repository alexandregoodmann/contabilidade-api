select * from planilha p ;

select l.id, 'fevereiro2024', l.data, c.descricao, c.tipo, l.descricao, l.tipo, l.fixo, l.parcelas, l.valor"
			+ "	from lancamento l inner join conta c on c.id  = l.id_conta where l.id_planilha=9923;
		
select * from planilha_anual pa where titulo = 'Fevereiro2024';
			
select * from planilha_anual order by valor;

select * from lancamento l where fixo is not null and parcelas is not null;
select * from lancamento l where parcelas is not null;