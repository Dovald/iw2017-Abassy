package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.Local;
import com.abassy.tables.LocalRepository;

@Service
public class LocalService implements LocalServiceInt
{
	@Autowired
	private LocalRepository repository;
	
	@Override
	public Local findByDireccionStartsWithIgnoreCase(String direccion)
	{
		return repository.findByDireccionStartsWithIgnoreCase(direccion);
	}
	
	@Override
	public void save(Local local)
	{
		repository.save(local);
	}
	
	@Override
	public void delete(Local local)
	{
		repository.delete(local);		
	}
	
	@Override
    @Transactional(readOnly = true)
    public List<Local> findAll() 
	{
		return  repository.findAll();
    }
	
	@Override
	public Local findOne(Long id)
	{
		return repository.findOne(id);
	}

}
