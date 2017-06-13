package com.abassy.tables;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FamiliaProductoRepository extends JpaRepository<FamiliaProducto, Long> {
	public List<FamiliaProducto> findByNombreStartsWithIgnoreCase(String nombre);
}
