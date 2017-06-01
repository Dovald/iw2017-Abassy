package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.Producto;
import com.abassy.tables.ProductoRepository;

@Service
public class ProductoService implements ProductoServiceInt
{
	@Autowired
	private ProductoRepository repository;
	
	@Override
	public List<Producto> findByNombreStartsWithIgnoreCase(String nombre)
	{
		return repository.findByNombreStartsWithIgnoreCase(nombre);		
	}
	
	@Override
	public void save(Producto producto)
	{
		repository.save(producto);
	}
	
	@Override
	public void delete(Producto producto)
	{
		repository.delete(producto);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() 
	{
		return  repository.findAll();
    }
	
	@Override
	public Producto findOne(Long id)
	{
		return repository.findOne(id);
	}

}
