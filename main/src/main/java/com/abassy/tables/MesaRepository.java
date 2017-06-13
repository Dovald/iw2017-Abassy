package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
	public Mesa findByNumeroAndZonaAndLocal(Integer numero, Zona zona,Local local);
	public List<Mesa> findByNumero(Integer numero);
	public List<Mesa> findByZona(Zona zona);
}