package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public List<Usuario> findByNombreStartsWithIgnoreCase(String nombre);
	
	public Usuario findByUsername(String username);
}