package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
	public Mesa findByNumeroAndZona(Integer numero,Zona zona);
}