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

select l.* from lancamento l join planilha p on p.id = l.id_planilha where ano = 2023 and mes = 10 order by descricao ;
update lancamento set fixo = null where id_planilha = 7674;

alter table lancamento alter column fixo varchar(255);

33a4a8c2-faa3-4644-873a-a0d3b82e5f9e

02877d5b-7db2-43ed-8b90-eee299e672a8