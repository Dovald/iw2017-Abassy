package com.abassy.services;

import java.util.List;

import com.abassy.tables.LineaPedido;
import com.abassy.tables.Pedido;
import com.abassy.tables.Producto;

public interface LineaPedidoServiceInt 
{
	public List<LineaPedido> findByPedido(Pedido pedido);
	public LineaPedido findByPedidoAndProducto(Pedido pedido,Producto producto);
	public void save(LineaPedido lineapedido, Pedido pedido);
    public void delete(LineaPedido lineapedido, Pedido pedido);
    public List<LineaPedido> findAll();	
	public LineaPedido findOne(Long id);

}
