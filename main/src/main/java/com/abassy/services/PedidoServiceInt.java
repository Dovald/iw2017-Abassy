package com.abassy.services;

import java.util.Date;
import java.util.List;

import com.abassy.tables.*;

public interface PedidoServiceInt 
{
	public List<Pedido> findByLocal(Local local);
	public List<Pedido> findByLocalAndFechaAfter(Local local, Date fecha);
	public void save(Local local, Pedido pedido, boolean cerrar);
	public void delete(Local local, Pedido pedido);
	public List<Pedido> findAll();	
	public Pedido findOne(Long id);

}
