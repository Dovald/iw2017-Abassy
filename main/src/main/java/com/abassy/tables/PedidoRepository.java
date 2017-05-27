package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;
import java.util.Date;

import java.util.List;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {
	List<Pedido> findByLocal(Local local);
	List<Pedido> findByLocalAndFechaAfter(Local local, Date fecha);
}