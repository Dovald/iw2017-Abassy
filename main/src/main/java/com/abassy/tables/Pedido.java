package com.abassy.tables;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "pedido", catalog = "abassy_db")
public class Pedido implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Cliente cliente;
	private Local local;
	private Mesa mesa;
	private Usuario usuario;
	private Float importe;
	private Date fecha;
	private List<LineaPedido> lineaPedidos;

	public Pedido() {
	}
	
	public Pedido(Local local, Usuario usuario) {
		this.local = local;
		this.usuario = usuario;
		this.fecha = new Date();
	}

	public Pedido(Cliente cliente, Local local, Usuario usuario, Float importe, Date fecha) {
		this.cliente = cliente;
		this.local = local;
		this.usuario = usuario;
		this.importe = importe;
		this.fecha = fecha;
	}

	public Pedido(Cliente cliente, Local local, Mesa mesa, Usuario usuario, Float importe, Date fecha,
			List<LineaPedido> lineaPedidos) {
		this.cliente = cliente;
		this.local = local;
		this.mesa = mesa;
		this.usuario = usuario;
		this.importe = importe;
		this.fecha = fecha;
		this.lineaPedidos = lineaPedidos;
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
	@JoinColumn(name = "id_cliente", nullable = true)
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_local", nullable = false)
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mesa", nullable = false)
	public Mesa getMesa() {
		return this.mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Float getImporte() {
		return this.importe;
	}

	public void setImporte(Float importe) {
		this.importe = importe;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha", nullable = false, length = 19)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<LineaPedido> getLineaPedidos() {
		return this.lineaPedidos;
	}

	public void setLineaPedidos(List<LineaPedido> lineaPedidos) {
		this.lineaPedidos = lineaPedidos;
	}

}
