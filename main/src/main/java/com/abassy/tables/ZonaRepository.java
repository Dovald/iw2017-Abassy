package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZonaRepository extends CrudRepository<Zona, Long> {
	List<Zona> findByLocal(Local local);
}