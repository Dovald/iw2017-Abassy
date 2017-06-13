package com.abassy.tables;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "producto", catalog = "abassy_db")
public class Producto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private FamiliaProducto familiaProducto;
	private String nombre;
	private Float precio;
	private Boolean tipo;
	private byte[] imagen;
	private Set<Producto> productos;

	public Producto() {
	}
	
	// Constructor de ingredientes:
	public Producto(String nombre, Float precio) {
		this.familiaProducto = new FamiliaProducto("Ingredientes");
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = false;
		//this.imagen = imagen;
		//this.productos = productos;
	}

	// Producto sin imagen
	public Producto(FamiliaProducto familiaProducto, String nombre, Float precio, Boolean tipo,	Set<Producto> productos) {
		this.familiaProducto = familiaProducto;
		this.nombre = nombre;
		this.precio = precio;
		this.tipo = tipo;
		//this.imagen = imagen;
		this.productos = productos;
	}
	
	public Producto(FamiliaProducto familiaProducto, String nombre, Float precio, Boolean tipo, byte[] imagen,
			Set<Producto> productos) {
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

	@ManyToOne(fetch = FetchType.EAGER)
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	public Set<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(Set<Producto> productos) {
		this.productos = productos;
	}
	
	@Override
	public String toString() {
		return familiaProducto + " - " + nombre;
	}

}
