SELECT l.descricao, l2.descricao, l.valor  FROM lancamento l inner join planilha p on l.id_planilha = p.id
inner join lancamento_label ll on ll.id_lancamento = l.id 
inner join label l2 on l2.id = ll.id_label 
where p.ano = 2023 
and p.mes = 9 
and l2.analisar = true 
and l.valor < 0 
order by l2.descricao;

SELECT l2.descricao, sum(cast(l.valor * (-1) as signed)) as soma FROM lancamento l inner join planilha p on l.id_planilha = p.id
inner join lancamento_label ll on ll.id_lancamento = l.id 
inner join label l2 on l2.id = ll.id_label 
where p.ano = 2023 
and p.mes = 9 
and l2.analisar = true 
and l.valor < 0 
GROUP by l2.descricao order by soma ;