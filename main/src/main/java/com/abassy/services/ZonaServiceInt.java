package com.abassy.services;

import java.util.List;

import com.abassy.tables.Local;
import com.abassy.tables.Zona;

public interface ZonaServiceInt 
{
	public List<Zona> findByLocal(Local local);
	public void save(Zona zona);
	public void delete(Zona zona);
	public List<Zona> findAll();	
	public Zona findOne(Long id);

}
