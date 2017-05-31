package com.abassy.services;

import java.util.List;

import com.abassy.tables.Producto;

public interface ProductoServiceInt 
{
	public List<Producto> findByNombreStartsWithIgnoreCase(String nombre);
	public void save(Producto producto);
	public void delete(Producto producto);
	public List<Producto> findAll();	
	public Producto findOne(Long id);

}
