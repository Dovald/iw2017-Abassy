package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepository extends CrudRepository<Producto, Long> {
	List<Producto> findByNombreStartsWithIgnoreCase(String nombre);
}
