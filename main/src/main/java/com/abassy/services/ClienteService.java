package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abassy.tables.Cliente;
import com.abassy.tables.ClienteRepository;

@Service
public class ClienteService implements ClienteServiceInt
{
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public List<Cliente> findAll()
	{
		return repository.findAll();
	}
	
	@Override
	public Cliente findOne(Long id)
	{
		return repository.findOne(id);
	}

	@Override
    public void save(Cliente cliente)
    {
		repository.save(cliente);
    }

	@Override
    public void delete(Cliente cliente)
    {
		repository.delete(cliente);
    }
    
	@Override
	public List<Cliente> findByTelefonoStartsWithIgnoreCase(String telefono)
    {
    	return repository.findByTelefonoStartsWithIgnoreCase(telefono);
    }
}
