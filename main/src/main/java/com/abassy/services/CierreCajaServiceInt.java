package com.abassy.services;

import java.util.Date;
import java.util.List;

import com.abassy.tables.CierreCaja;
import com.abassy.tables.Local;

public interface CierreCajaServiceInt 
{
	public float cerrarcaja(Local local);
	public void cerrarcajafinal(Local local, float importe, float importe_real);
	public void save(CierreCaja cierrecaja);
	public CierreCaja findByFecha(Date fecha);
	public List<CierreCaja> findAll();
	public CierreCaja findOne(Long id);
	public List<CierreCaja> findByLocal(Local local);

}
