package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.Local;
import com.abassy.tables.Mesa;
import com.abassy.tables.MesaRepository;
import com.abassy.tables.Zona;

@Service
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
	public Mesa findByNumeroAndZonaAndLocal(Integer numero, Zona zona, Local local)
	{
		return repository.findByNumeroAndZonaAndLocal(numero, zona, local);		
	}
	
	@Override
	public List<Mesa> findByNumero(Integer numero)
	{
		return repository.findByNumero(numero);		
	}
	
	@Override
	public List<Mesa> findByZona(Zona zona)
	{
		return repository.findByZona(zona);		
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Mesa> findAll() 
	{
		return repository.findAll();
    }
	
	@Override
	public Mesa findOne(Long id)
	{
		return repository.findOne(id);
	}

}
