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
@Table(name = "familia_producto", catalog = "abassy_db")
public class FamiliaProducto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nombre;
	private List<Producto> productos;
	
	public FamiliaProducto() {
	}
	
	public FamiliaProducto(String nombre) {
		this.nombre = nombre;
	}

	public FamiliaProducto(String nombre, List<Producto> productos) {
		this.nombre = nombre;
		this.productos = productos;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
