package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
	List<Cliente> findByNombreStartsWithIgnoreCase(String nombre);
}