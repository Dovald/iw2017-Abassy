package com.abassy.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.Local;
import com.abassy.tables.Pedido;
import com.abassy.tables.PedidoRepository;

public class PedidoService implements PedidoServiceInt
{
	@Autowired
	private PedidoRepository repository;
	
	@Override
	public List<Pedido> findByLocal(Local local)
	{
		return repository.findByLocal(local);		
	}
	
	@Override
	public List<Pedido> findByLocalAndFechaAfter(Local local, Date fecha)
	{
		return repository.findByLocalAndFechaAfter(local, fecha);
	}
	
	@Override
	public void save(Pedido pedido)
	{
		repository.save(pedido);
	}
	
	@Override
	public void delete(Pedido pedido)
	{
		repository.delete(pedido);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() 
	{
		return  repository.findAll();
    }
	
	@Override
	public Pedido findOne(Long id)
	{
		return repository.findOne(id);
	}

}
