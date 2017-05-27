package com.abassy.tables;

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

import java.util.List;

@Entity
@Table(name = "producto", catalog = "abassy_db")
public class Producto implements java.io.Serializable {

	private static final long serialVersionUID = -500120082346868169L;
	
	private Long id;
	private FamiliaProducto familiaProducto;
	private String nombre;
	private Float precio;
	private Boolean tipo;
	private byte[] imagen;
	List<Producto> productos;

	public Producto() {
	}

	public Producto(FamiliaProducto familiaProducto, String nombre, Float precio, Boolean tipo, byte[] imagen,
			List<Producto> productos) {
		this.familiaProducto = familiaProducto;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
		this.imagen = imagen;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_familia")
	public FamiliaProducto getFamiliaProducto() {
		return this.familiaProducto;
	}

	public void setFamiliaProducto(FamiliaProducto familiaProducto) {
		this.familiaProducto = familiaProducto;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getPrecio() {
		return this.precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public Boolean getTipo() {
		return this.tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	@OneToMany(fetch = FetchType.LAZY)
	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
