package com.abassy.tables;

import java.util.List;
import java.util.Collection;
import java.util.ArrayList;

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
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "usuario", catalog = "abassy_db")
public class Usuario implements java.io.Serializable, UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Local local;
	private Integer tipo;
	private String nombre;
	private String apellidos;
	private String username;
	private String password;
	private List<Pedido> pedidos;

	public Usuario() {
	}
	
	public Usuario(Local local, String nombre, String apellidos, int tipo) {
		this.local = local;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipo = tipo;
		this.username = nombre;	
	}

	public Usuario(String nombre, String apellidos, String username) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = username;	
	}
	
	public Usuario(String nombre, String apellidos) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = nombre;
	}
	
	public Usuario(String nombre, String apellidos, int tipo) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.username = nombre;
		this.tipo = tipo;
	}

	public Usuario(Local local, int tipo, String nombre, String apellidos, String password, List<Pedido> pedidos) {
		this.local = local;
		this.tipo = tipo;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.pedidos = pedidos;
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
	@JoinColumn(name = "id_local", nullable = true)
	public Local getLocal() {
		return this.local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Integer getTipo() {
		return this.tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@OneToMany(fetch = FetchType.LAZY)
	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Transient
	@Override
	public String toString() {
		return String.format("Usuario[id=%d, firstName='%s', lastName='%s', username='%s', password='%s']", id,
				nombre,apellidos,username,password);
	}
	
	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> list=new ArrayList<GrantedAuthority>();
		//list.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
		if (this.tipo == 0) 
			list.add(new SimpleGrantedAuthority("GERENTE"));
		else if (this.tipo == 1)
			list.add(new SimpleGrantedAuthority("ENCARGADO"));
		else if (this.tipo == 2)
			list.add(new SimpleGrantedAuthority("CAMARERO"));
		return list;
	}

	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}
