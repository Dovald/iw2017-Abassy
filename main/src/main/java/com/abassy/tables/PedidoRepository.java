package com.abassy.tables;

//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	public List<Pedido> findByLocal(Local local);
	public List<Pedido> findByLocalAndFechaAfter(Local local, Date fecha);
}