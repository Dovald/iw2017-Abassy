package com.abassy.services;

import java.util.List;

import com.abassy.tables.Local;

public interface LocalServiceInt 
{
	public Local findByDireccionStartsWithIgnoreCase(String direccion);
	public void save(Local local);
	public void delete(Local local);
	public List<Local> findAll();	
	public Local findOne(Long id);

}
