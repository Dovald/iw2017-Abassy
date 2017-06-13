package com.abassy.services;

import java.util.List;

import com.abassy.tables.Local;

public interface LocalServiceInt 
{
	public List<Local> findByDireccionStartsWithIgnoreCase(String direccion);
	public Local findByDireccionIgnoreCaseAndCiudadIgnoreCase(String direccion, String ciudad);
	public void save(Local local);
	public void delete(Local local);
	public List<Local> findAll();	
	public Local findOne(Long id);

}
