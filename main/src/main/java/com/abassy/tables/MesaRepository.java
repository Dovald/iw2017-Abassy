package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
	public Mesa findByNumeroAndZona(Integer numero, Zona zona);
	public List<Mesa> findByNumero(Integer numero);
	public List<Mesa> findByZona(Zona zona);
}