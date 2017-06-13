package com.abassy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abassy.tables.FamiliaProducto;
import com.abassy.tables.FamiliaProductoRepository;

@Service
public class FamiliaProductoService implements FamiliaProductoServiceInt
{
	@Autowired
	private FamiliaProductoRepository repository;
	
	@Override
    @Transactional(readOnly = true)
    public List<FamiliaProducto> findAll() 
	{
		List<FamiliaProducto> familiasproducto = repository.findAll();
		return familiasproducto;
    }
	
	@Override
	public FamiliaProducto findOne(Long id)
	{
		return repository.findOne(id);
	}
	
	@Override
	public void save(FamiliaProducto familiaproducto)
	{
		repository.save(familiaproducto);
	}
	
	@Override
	public List<FamiliaProducto> findByNombreStartsWithIgnoreCase(String nombre)
	{
		return repository.findByNombreStartsWithIgnoreCase(nombre);
	}
	
	@Override
	public void delete(FamiliaProducto familiaproducto)
	{
		repository.delete(familiaproducto);
	}

}
