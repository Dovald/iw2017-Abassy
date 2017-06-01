package com.abassy.services;

import java.util.List;

import com.abassy.tables.Mesa;
import com.abassy.tables.Zona;

public interface MesaServiceInt 
{
	public void save(Mesa mesa);
	public void delete(Mesa mesa);
	public Mesa findByNumeroAndZona(Integer numero,Zona zona);
	public List<Mesa> findByNumero(Integer numero);
	public List<Mesa> findByZona(Zona zona);
	public List<Mesa> findAll();	
	public Mesa findOne(Long id);
}
