select * from planilha p ;

select * from lancamento l ;

update categoria set analisar = 1 where id in (2, 139,142,174,175,176,177);

alter table categoria add column analisar bit(1) null;

select * from categoria c ;

-- somente para um mes
select c.descricao, count(*) as qtd, sum(l.valor)*(-1) as valor 
	from lancamento l join categoria c on l.id_categoria = c.id 
	where l.id_planilha = 1 
	and c.analisar = 1 
	group by c.descricao order by valor;
	
-- para o ano inteiro
select p.mes, c.descricao, count(*) as qtd, sum(l.valor)*(-1) as valor from lancamento l 
	join categoria c on l.id_categoria = c.id
	inner join planilha p on p.id = l.id_planilha  
	and c.analisar = 1 
	group by p.mes, c.descricao 
	order by p.mes, c.descricao;
