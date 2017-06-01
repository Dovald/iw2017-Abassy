package com.abassy.tables;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "local", catalog = "abassy_db")
public class Local implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String direccion;
	private String ciudad;
	private List<Pedido> pedidos;
	private List<Zona> zonas;
	private List<CierreCaja> cierreCajas;
	private List<Usuario> usuarios;

	public Local() {
	}
	
	public Local(String direccion, String ciudad) {
		this.direccion = direccion;
		this.ciudad = ciudad;
	}

	public Local(String direccion, String ciudad, List<Pedido> pedidos, List<Zona> zonas, List<CierreCaja> cierreCajas,
			List<Usuario> usuarios) {
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.pedidos = pedidos;
		this.zonas = zonas;
		this.cierreCajas = cierreCajas;
		this.usuarios = usuarios;
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

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public List<Zona> getZonas() {
		return this.zonas;
	}

	public void setZonas(List<Zona> zonas) {
		this.zonas = zonas;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<CierreCaja> getCierreCajas() {
		return this.cierreCajas;
	}

	public void setCierreCajas(List<CierreCaja> cierreCajas) {
		this.cierreCajas = cierreCajas;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", direccion, ciudad);
	}

}
