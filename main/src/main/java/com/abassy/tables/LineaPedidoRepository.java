package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineaPedidoRepository extends CrudRepository<LineaPedido, Long> {
	List<LineaPedido> findByPedido(Pedido pedido);
}