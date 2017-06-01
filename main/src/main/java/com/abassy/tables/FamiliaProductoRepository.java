package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliaProductoRepository extends JpaRepository<FamiliaProducto, Long> {
	public FamiliaProducto findByNombreStartsWithIgnoreCase(String nombre);
}
