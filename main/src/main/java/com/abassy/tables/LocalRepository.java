package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
	public Local findByDireccionStartsWithIgnoreCase(String direccion);
}