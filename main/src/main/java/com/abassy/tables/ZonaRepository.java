package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
	public List<Zona> findByLocal(Local local);
}