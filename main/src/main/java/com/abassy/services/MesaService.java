package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.Mesa;
import com.abassy.tables.MesaRepository;
import com.abassy.tables.Zona;

public class MesaService implements MesaServiceInt
{
	@Autowired
	private MesaRepository repository;
	
	@Override
	public void save(Mesa mesa)
	{
		repository.save(mesa);		
	}
	
	@Override
	public void delete(Mesa mesa)
	{
		repository.delete(mesa);
	}
	
	@Override
	public Mesa findByNumeroAndZona(Integer numero,Zona zona)
	{
		return repository.findByNumeroAndZona(numero, zona);		
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Mesa> findAll() 
	{
		return  repository.findAll();
    }
	
	@Override
	public Mesa findOne(Long id)
	{
		return repository.findOne(id);
	}

}
