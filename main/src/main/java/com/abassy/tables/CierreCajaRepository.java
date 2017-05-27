package com.abassy.tables;

import java.util.Date;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CierreCajaRepository extends CrudRepository<CierreCaja, Long> {
	List<CierreCaja> findAll();
	List<CierreCaja> findByFecha(Date fecha);
}