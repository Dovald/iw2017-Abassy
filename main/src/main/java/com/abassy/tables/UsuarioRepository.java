package com.abassy.tables;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	List<Cliente> findByApellidosStartsWithIgnoreCase(String apellidos);
}