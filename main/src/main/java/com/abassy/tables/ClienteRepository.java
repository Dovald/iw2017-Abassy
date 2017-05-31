package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Cliente findByTelefono(String telefono);
}