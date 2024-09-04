select sum(l.valor) from lancamento l
join lancamento_label ll on l.id = ll.id_lancamento 
join label l2 on ll.id_label = l2.id 
where l2.descricao = 'carro' and date(l.`data`) >= '2024-01-01' order by l.data;

select sum(l.valor) from lancamento l
join lancamento_label ll on l.id = ll.id_lancamento 
join label l2 on ll.id_label = l2.id 
where l2.descricao = 'carro' and date(l.`data`) >= '2023-01-01' and date(l.`data`) <= '2024-01-01' order by l.data;