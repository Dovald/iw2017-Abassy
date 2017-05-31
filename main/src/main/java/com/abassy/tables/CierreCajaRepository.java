package com.abassy.tables;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface CierreCajaRepository extends JpaRepository<CierreCaja, Long> {
	public List<CierreCaja> findAll();
	public CierreCaja findByFecha(Date fecha);
	public List<CierreCaja> findByLocal(Local local);
}