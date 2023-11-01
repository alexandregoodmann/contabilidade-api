

delete from lancamento where id in (8708
,8709,8746,8751,8753
,8756,8757,8758,8770
,8811,8812,8813,8814
,8815,8816,8817,8818);

select l.id from lancamento l join planilha p on 	p.id = l.id_planilha join conta c on 	 c.id = l.id_conta where c.id = 8710

select * from lancamento_label ll ;
delete from lancamento_label where id_lancamento in (8708
,8709,8746,8751,8753
,8756,8757,8758,8770
,8811,8812,8813,8814
,8815,8816,8817,8818);