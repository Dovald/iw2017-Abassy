package com.abassy.services;

import java.util.Date;
import java.util.List;

import com.abassy.tables.Local;
import com.abassy.tables.Pedido;

public interface PedidoServiceInt 
{
	public List<Pedido> findByLocal(Local local);
	public List<Pedido> findByLocalAndFechaAfter(Local local, Date fecha);
	public void save(Pedido pedido);
	public void delete(Pedido pedido);
	public List<Pedido> findAll();	
	public Pedido findOne(Long id);

}
