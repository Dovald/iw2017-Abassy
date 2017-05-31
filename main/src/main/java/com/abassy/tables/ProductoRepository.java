package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	public List<Producto> findByNombreStartsWithIgnoreCase(String nombre);
}
