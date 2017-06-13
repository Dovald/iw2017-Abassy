package com.abassy.tables;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
	public List<Local> findByDireccionStartsWithIgnoreCase(String direccion);
	public Local findByDireccionIgnoreCaseAndCiudadIgnoreCase(String direccion, String ciudad);
}