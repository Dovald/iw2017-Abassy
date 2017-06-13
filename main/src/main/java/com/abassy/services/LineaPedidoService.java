package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.LineaPedido;
import com.abassy.tables.LineaPedidoRepository;
import com.abassy.tables.Pedido;
import com.abassy.tables.Producto;


@Service
public class LineaPedidoService implements LineaPedidoServiceInt
{
	@Autowired
	private LineaPedidoRepository repository;
	
	@Override
	public List<LineaPedido> findByPedido(Pedido pedido)
	{
		return repository.findByPedido(pedido);		
	}
	
	@Override
	public LineaPedido findByPedidoAndProducto(Pedido pedido,Producto producto)
	{
		return repository.findByPedidoAndProducto(pedido, producto);		
	}
	
	@Override
	public void save(LineaPedido lineapedido, Pedido pedido)
	{
		lineapedido.setPedido(pedido);
		repository.save(lineapedido);		
	}
	
	@Override
    public void delete(LineaPedido lineapedido, Pedido pedido)
    {
		repository.delete(lineapedido);		
    }
	
	@Override
    @Transactional(readOnly = true)
    public List<LineaPedido> findAll() 
	{
		return  repository.findAll();
    }
	
	@Override
	public LineaPedido findOne(Long id)
	{
		return repository.findOne(id);
	}
}
