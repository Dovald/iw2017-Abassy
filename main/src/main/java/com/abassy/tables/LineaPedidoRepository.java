package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineaPedidoRepository extends JpaRepository<LineaPedido, Long> {
	public List<LineaPedido> findByPedido(Pedido pedido);
	public LineaPedido findByPedidoAndProducto(Pedido pedido, Producto producto);
}