package com.abassy.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.security.SecurityUtils;
import com.abassy.tables.*;

@Service
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
	public void save(Local local, Pedido pedido, boolean cerrar)//, Cliente cliente, Pedido pedido)
	{
		Usuario usuario = SecurityUtils.getUserLogin();
		Date date = Calendar.getInstance().getTime();
		pedido.setFecha(date);
		pedido.setUsuario(usuario);
		pedido.setLocal(local);
		
		float total = 0.0f;
		List<LineaPedido> linPed = pedido.getLineaPedidos();
		if(linPed != null)
			if(!linPed.isEmpty())
				for(LineaPedido linea : linPed) {
					total += linea.getProducto().getPrecio() * linea.getCantidad();
				}
		pedido.setImporte(total);
		
		pedido.setCerrado(cerrar);
		repository.save(pedido);
	}
	
	@Override
	public void delete(Local local, Pedido pedido)
	{
		Usuario usuario = SecurityUtils.getUserLogin();
		Date date = Calendar.getInstance().getTime();
		pedido.setFecha(date);
		pedido.setUsuario(usuario);
		pedido.setLocal(local);
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
