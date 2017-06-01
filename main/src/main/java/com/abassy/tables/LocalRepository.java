package com.abassy.tables;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
	public Local findByDireccionStartsWithIgnoreCase(String direccion);
}