package com.abassy.tables;

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

@Entity
@Table(name = "zona", catalog = "abassy_db")
public class Zona implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Local local;
	private String nombre;
	private List<Mesa> mesas;

	public Zona() {
	}

	public Zona(Local local, String nombre, List<Mesa> mesas) {
		this.local = local;
		this.nombre = nombre;
		this.mesas = mesas;
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
	@JoinColumn(name = "id_local")
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<Mesa> getMesas() {
		return this.mesas;
	}

	public void setMesas(List<Mesa> mesas) {
		this.mesas = mesas;
	}
	
	@Override
	public String toString() {
		return String.format("%s", nombre);
	}

}
