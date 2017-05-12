package com.abassy.tables;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FamiliaProducto generated by hbm2java
 */
@Entity
@Table(name = "familia_producto", catalog = "abassy_db")
public class FamiliaProducto implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Set<Producto> productos = new HashSet<Producto>(0);

	public FamiliaProducto() {
	}

	public FamiliaProducto(String nombre, Set<Producto> productos) {
		this.nombre = nombre;
		this.productos = productos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "nombre", length = 65535)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "familiaProducto")
	public Set<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}

}
