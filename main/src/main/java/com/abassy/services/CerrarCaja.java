package com.abassy.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abassy.tables.CierreCajaRepository;
import com.abassy.tables.PedidoRepository;
import com.abassy.tables.CierreCaja;
import com.abassy.tables.Local;
import com.abassy.tables.Pedido;

@Service
public class CerrarCaja {
	
	@Autowired
	private PedidoRepository repository;
	@Autowired
	private CierreCajaRepository repositoryCaja;

	public float cerrarcaja(Local local) {
		
		List<CierreCaja> cierres = repositoryCaja.findAll();
		Date lastdate = cierres.get(1).getFecha();
		
		for(CierreCaja cierre : cierres)
		{
			if(cierre.getFecha().after(lastdate)) lastdate = cierre.getFecha();
		}
		
		List<Pedido> pedidos = repository.findByLocalAndFechaAfter(local,lastdate);
		float importe = 0;
		
		for(Pedido pedido : pedidos)
		{
			importe += pedido.getImporte();
		}
		
		return importe;
	}
	
	public void cerrarcajafinal(Local local, float importe, float importe_real)
	{
		Date date = Calendar.getInstance().getTime();
		repositoryCaja.save(new CierreCaja(local, importe, importe_real, date));
	}
	
}
