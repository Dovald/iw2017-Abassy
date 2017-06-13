package com.abassy.tables;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	public List<Cliente> findByTelefonoStartsWithIgnoreCase(String telefono);
}