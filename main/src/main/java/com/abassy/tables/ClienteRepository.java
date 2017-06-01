package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	Cliente findByTelefono(String telefono);
}