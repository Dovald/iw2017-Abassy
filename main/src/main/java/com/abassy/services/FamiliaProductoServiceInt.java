package com.abassy.services;

import java.util.List;

import com.abassy.tables.FamiliaProducto;



public interface FamiliaProductoServiceInt 
{
	public List<FamiliaProducto> findAll();
	
	public FamiliaProducto findOne(Long id);

    public void save(FamiliaProducto familiaproducto);

    public void delete(FamiliaProducto familiaproducto);
    
    public FamiliaProducto findByNombreStartsWithIgnoreCase(String nombre);

}
