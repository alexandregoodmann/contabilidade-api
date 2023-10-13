select l.descricao, l.valor, c.descricao, c.tipo from lancamento l 
join planilha p on p.id = l.id_planilha 
join conta c on c.id = l.id_conta 
where p.ano = 2023 and p.mes = 10 
and valor < 0 and c.tipo = 'CC';