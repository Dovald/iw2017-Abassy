package com.abassy.services;

import java.util.List;

import com.abassy.tables.Cliente;

public interface ClienteServiceInt 
{
	public List<Cliente> findAll();

    public void save(Cliente cliente);

    public void delete(Cliente cliente);
    
    public List<Cliente> findByTelefonoStartsWithIgnoreCase(String telefono);
    
	public Cliente findOne(Long id);

}
