package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.Local;
import com.abassy.tables.Zona;
import com.abassy.tables.ZonaRepository;

@Service
public class ZonaService implements ZonaServiceInt
{
	@Autowired
	private ZonaRepository repository;
	
	@Override
	public List<Zona> findByLocal(Local local)
	{
		return repository.findByLocal(local);		
	}
	
	@Override
	public void save(Zona zona)
	{
		repository.save(zona);
	}
	
	@Override
	public void delete(Zona zona)
	{
		repository.delete(zona);
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Zona> findAll() 
	{
		return  repository.findAll();
    }
	
	@Override
	public Zona findOne(Long id)
	{
		return repository.findOne(id);
	}

}
