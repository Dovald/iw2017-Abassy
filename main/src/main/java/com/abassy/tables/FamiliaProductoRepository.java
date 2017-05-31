package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface FamiliaProductoRepository extends JpaRepository<FamiliaProducto, Long> {
	public FamiliaProducto findByNombreStartsWithIgnoreCase(String nombre);
}
