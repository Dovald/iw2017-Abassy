package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MesaRepository extends CrudRepository<Mesa, Long> {
	List<Mesa> findByNumero(Integer numero);
}