package com.abassy.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "linea_pedido", catalog = "abassy_db")
public class LineaPedido implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Pedido pedido;
	private Producto producto;
	private Integer cantidad;

	public LineaPedido() {
	}

	public LineaPedido(Pedido pedido, Producto producto, Integer cantidad) {
		this.pedido = pedido;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pedido")
	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_producto")
	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

}
