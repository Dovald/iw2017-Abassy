package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
	public List<Zona> findByLocal(Local local);
}