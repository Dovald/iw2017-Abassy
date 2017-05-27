package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FamiliaProductoRepository extends CrudRepository<FamiliaProducto, Long> {
	List<FamiliaProducto> findByNombreStartsWithIgnoreCase(String nombre);
}
